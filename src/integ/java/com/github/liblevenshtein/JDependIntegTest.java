package com.github.liblevenshtein;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;

import lombok.extern.slf4j.Slf4j;

import static com.github.liblevenshtein.assertion.IteratorAssertions.assertThat;

@Slf4j
public class JDependIntegTest {

  private static final String BASE_PACKAGE = "com.github.liblevenshtein";

  @SuppressWarnings("unchecked")
  private static final Set<String> EMPTY_DEPS = (Set<String>) Collections.EMPTY_SET;

  /** Valid, cyclic dependencies. */
  private static final Map<String, Map<String, Set<String>>> VALID_DEPS = buildValidDeps();

  @SuppressWarnings("checkstyle:multiplestringliterals")
  private static Map<String, Map<String, Set<String>>> buildValidDeps() {
    final Map<String, Set<String>> mainDeps = new ImmutableMap.Builder<String, Set<String>>()
      .put("com.github.liblevenshtein.transducer", new ImmutableSet.Builder<String>()
        .add("com.github.liblevenshtein.transducer.factory")
        .add("com.github.liblevenshtein.transducer")
        .build())
      .put("com.github.liblevenshtein.transducer.factory", new ImmutableSet.Builder<String>()
        .add("com.github.liblevenshtein.transducer")
        .add("com.github.liblevenshtein.transducer.factory")
        .build())
      .build();

    // In the tests, pretty much every package is cyclic to every other through
    // their assertions, according to JDepend.  In the future, it may be good to
    // refactor the tests such that their packages are not so coupled.
    final Set<String> assertionDeps = new ImmutableSet.Builder<String>()
      .add("com.github.liblevenshtein.assertion")
      .add("com.github.liblevenshtein.collection.dictionary")
      .add("com.github.liblevenshtein.collection.dictionary.factory")
      .add("com.github.liblevenshtein.distance")
      .add("com.github.liblevenshtein.distance.factory")
      .add("com.github.liblevenshtein.serialization")
      .add("com.github.liblevenshtein.transducer")
      .add("com.github.liblevenshtein.transducer.factory")
      .build();

    final Map<String, Set<String>> testDeps = assertionDeps.stream()
      .reduce(new ImmutableMap.Builder<String, Set<String>>(),
        (builder, dep) -> builder.put(dep, assertionDeps),
        (lhs, rhs) -> lhs).build();

    final Map<String, Set<String>> regrDeps = mainDeps;
    final Map<String, Set<String>> integDeps = mainDeps;

    return new ImmutableMap.Builder<String, Map<String, Set<String>>>()
      .put("main", mainDeps)
      .put("test", testDeps)
      .put("regr", regrDeps)
      .put("integ", integDeps)
      .build();
  }

  @DataProvider(name = "jdependProvider")
  @SuppressWarnings("checkstyle:multiplestringliterals")
  public Object[][] jdependProvider() throws IOException {
    final String[] configs = {"main", "test", "regr", "integ", "task"};
    final Object[][] jdepends = new Object[configs.length][2];
    for (int i = 0; i < configs.length; i += 1) {
      final String config = configs[i];
      try {
        final JDepend jdepend = new JDepend();
        addConfigDir(jdepend, config);
        if (!"main".equals(config) && !"task".equals(config)) {
          addConfigDir(jdepend, "main");
        }
        jdepend.analyze();
        jdepends[i][0] = jdepend;
        jdepends[i][1] = config;
      }
      catch (final IOException exception) {
        log.error("Failed to initialize config [{}]", config, exception);
        throw exception;
      }
    }
    return jdepends;
  }

  private void addConfigDir(final JDepend jdepend, final String config) throws IOException {
    final String projectDir = System.getProperty("user.dir");
    final String classesDir =
      String.format("%s/build/classes/%s", projectDir, config);
    jdepend.addDirectory(classesDir);
  }

  private Set<String> depsFor(final JavaPackage pkg, final String config) {
    if (!VALID_DEPS.containsKey(config)) {
      return EMPTY_DEPS;
    }
    final Map<String, Set<String>> validDeps = VALID_DEPS.get(config);
    if (!validDeps.containsKey(pkg.getName())) {
      return EMPTY_DEPS;
    }
    return validDeps.get(pkg.getName());
  }

  @SuppressWarnings("unchecked")
  @Test(dataProvider = "jdependProvider")
  public void testNoUnexpectedCycles(final JDepend jdepend, final String config) {
    final List<JavaPackage> cycles = new ArrayList<>();
    for (final JavaPackage pkg : (Collection<JavaPackage>) jdepend.getPackages()) {
      if (pkg.getName().startsWith(BASE_PACKAGE)
          && pkg.collectAllCycles(cycles)
          && isDirectCycle(cycles)) {
        final Iterator<JavaPackage> iter = cycles.iterator();
        assertThat(iter).hasNext();
        assertThat(iter.next().getName()).isEqualTo(pkg.getName());

        Set<String> expectedDeps = depsFor(pkg, config);
        JavaPackage prev = pkg;

        while (iter.hasNext()) {
          try {
            final JavaPackage dep = iter.next();
            assertThat(expectedDeps).contains(dep.getName());
            expectedDeps = depsFor(dep, config);
            prev = dep;
          }
          catch (final AssertionError error) {
            final String message =
              String.format("Failed to analyze dependencies for package [%s], config [%s]",
                prev.getName(), config);
            throw new AssertionError(message, error);
          }
        }

        cycles.clear();
      }
    }
  }

  private boolean isDirectCycle(final Collection<JavaPackage> cycles) {
    final Iterator<JavaPackage> iter = cycles.iterator();
    if (iter.hasNext()) {
      final JavaPackage src = iter.next();
      while (iter.hasNext()) {
        final JavaPackage tgt = iter.next();
        if (src.getName().equals(tgt.getName())) {
          return true;
        }
      }
    }
    return false;
  }
}
