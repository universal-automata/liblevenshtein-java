package com.github.dylon.liblevenshtein.collection.dawg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicReference;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.google.common.base.Joiner;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.dylon.liblevenshtein.collection.dawg.factory.DawgFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.DawgNodeFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.PrefixFactory;
import com.github.dylon.liblevenshtein.collection.dawg.factory.TransitionFactory;
import com.github.dylon.liblevenshtein.serialization.BytecodeSerializer;
import com.github.dylon.liblevenshtein.serialization.ProtobufSerializer;
import com.github.dylon.liblevenshtein.serialization.Serializer;
import static com.github.dylon.liblevenshtein.assertion.SetAssertions.assertThat;

@Slf4j
public class DawgTest {
  private List<String> terms;
  private DawgNodeFactory dawgNodeFactory;
  private PrefixFactory<DawgNode> prefixFactory;
  private TransitionFactory<DawgNode> transitionFactory;
  private DawgFactory dawgFactory;
  private AbstractDawg emptyDawg;
  private AbstractDawg fullDawg;

  @BeforeClass
  public void setUp() throws IOException {
    try (final BufferedReader reader = new BufferedReader(
          new InputStreamReader(
            getClass().getResourceAsStream("/wordsEn.txt"),
            StandardCharsets.UTF_8))) {

      final List<String> termsList = new ArrayList<>();

      String term;
      while ((term = reader.readLine()) != null) {
        termsList.add(term);
      }

      Collections.sort(termsList);

      this.terms = termsList;
      this.dawgNodeFactory = new DawgNodeFactory();
      this.prefixFactory = new PrefixFactory<>();
      this.transitionFactory = new TransitionFactory<>();
      this.dawgFactory = new DawgFactory(dawgNodeFactory, prefixFactory, transitionFactory);
      this.emptyDawg = dawgFactory.build(new ArrayList<>(0));
      this.fullDawg = dawgFactory.build(termsList);
    }
  }

  @DataProvider(name="terms")
  public Iterator<Object[]> terms() {
    return new TermIterator(terms.iterator());
  }

  @Test(dataProvider="terms")
  public void emptyDawgAcceptsNothing(final String term) {
    assertThat(emptyDawg).doesNotContain(term);
  }

  @Test(dataProvider="terms")
  public void dawgAcceptsAllItsTerms(final String term) {
    assertThat(fullDawg).contains(term);
  }

  @DataProvider(name="serializers")
  public Iterator<Object[]> serializers() {
    final List<Object[]> serializers = new LinkedList<>();
    serializers.add(new Object[] {new BytecodeSerializer()});
    serializers.add(new Object[] {new ProtobufSerializer()});
    return serializers.iterator();
  }

  @Test(dataProvider="serializers")
  public void testSerialization(final Serializer serializer) throws Exception {
    byte[] bytes;

    bytes = serializer.serialize(fullDawg);
    final AbstractDawg actualDawg =
      serializer.deserialize(SortedDawg.class, bytes);
    assertThat(actualDawg).isEqualTo(fullDawg);

		final RuntimeAverager average = new RuntimeAverager(serializer);
    final ForkJoinPool pool = new ForkJoinPool();
    log.info("Benchmarking the average runtimes to build and deserialize a DAWG");
    pool.invoke(average);

    final double buildTime = average.buildTime();
    final double deserializationTime = average.deserializationTime();

    log.info(
      "Deserialized DAWG in [{}] % the average time required to build it, "+
      "after [{}] iterations, using deserializer [{}]%n",
      String.format("%.5f", (100.0 * deserializationTime / buildTime)),
      RuntimeAverager.NUM_ITERS, serializer.getClass());

    assertThat(deserializationTime).isLessThan(buildTime);
  }

  @Test
  public void dawgAcceptsNoTermsItDoesNotContain() {
    assertThat(fullDawg).doesNotContain("", "foobar", "C+");
  }

  @Test
  public void dawgSizeIsSameAsTerms() {
    assertThat(emptyDawg)
      .isEmpty()
      .hasSize(0);
    assertThat(fullDawg)
      .isNotEmpty()
      .hasSize(terms.size());
  }

  @Test
  public void dawgAcceptsEmptyStringIfInTerms() {
    final List<String> termsList = new ArrayList<>(1);
    termsList.add("");
    final AbstractDawg dawg = dawgFactory.build(termsList);
    assertThat(dawg).contains("");
  }

  @Test
  public void dawgShouldIterateOverAllTerms() {
    final Set<String> termsList = new HashSet<>(this.terms);
    for (final String term : fullDawg) {
      try {
        assertThat(termsList).contains(term);
      }
      catch (final AssertionError exception) {
        throw new AssertionError("Expected terms to contain: \"" + term + "\"", exception);
      }
      termsList.remove(term);
    }
    if (!termsList.isEmpty()) {
      final String message =
        String.format("Expected all terms to be iterated over, but missed [%s]",
          Joiner.on(", ").join(termsList));
      throw new AssertionError(message);
    }
  }

  @Test(expectedExceptions=IllegalArgumentException.class)
  public void insertingTermsOutOfOrderShouldThrowAnException() {
    final List<String> termsList = new ArrayList<>(3);
    termsList.add("a");
    termsList.add("c");
    termsList.add("b");
    dawgFactory.build(termsList, true);
  }

  @Test
  public void equivalentDawgsShouldBeEqual() {
    final AbstractDawg other = dawgFactory.build(terms);
    assertThat(fullDawg).isEqualTo(other);
  }

  @RequiredArgsConstructor
  private static class TermIterator implements Iterator<Object[]> {
    private final Iterator<String> terms;
    private Object[] params = null;
    private Object[] buffer = new Object[1];

    @Override
    public boolean hasNext() {
      advance();
      return null != params;
    }

    @Override
    public Object[] next() {
      advance();
      final Object[] paramsLocal = this.params;
      this.params = null;
      return paramsLocal;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    public void advance() {
      if (null == params && terms.hasNext()) {
        buffer[0] = terms.next();
        params = buffer;
      }
    }
  }

	@RequiredArgsConstructor
  private class RuntimeAverager extends RecursiveAction {
  	private static final long serialVersionUID = 1L;
  	private static final int NUM_ITERS = 50;

		private final Serializer serializer;
  	private final int numIters;
		private final AtomicReference<Double> buildTimeSum;
		private final AtomicReference<Double> deserializationTimeSum;

  	public RuntimeAverager(final Serializer serializer) {
  		this(serializer, NUM_ITERS,
  				 new AtomicReference<Double>(0.0),
  				 new AtomicReference<Double>(0.0));
  	}

  	public double buildTime() {
  		return buildTimeSum.get() / NUM_ITERS;
  	}

  	public double deserializationTime() {
  		return deserializationTimeSum.get() / NUM_ITERS;
  	}

  	@Override
  	protected void compute() {
  		try {
  			if (1 == numIters) {
      		long startTime;
      		long stopTime;
      		AbstractDawg dawg;

					log.info(
						"Copying terms to avoid ConcurrentModificationException in "+
						"Collections.sort(List)");

					final long seed = 58073L;
					final Random random = new Random(seed);
					final List<String> unsortedTerms = new ArrayList<>(terms);

					log.info("Shuffling terms with random seed [{}]", seed);
					Collections.shuffle(unsortedTerms, random);

					log.info("Benchmarking the time to build a DAWG from an unsorted list");
      		startTime = System.nanoTime();
      		dawg = dawgFactory.build(unsortedTerms);
      		stopTime = System.nanoTime();
      		final double buildTime = (stopTime - startTime);

					log.info("Serializing the DAWG to benchmark its deserialization time");
      		final byte[] bytes = serializer.serialize(dawg);

      		log.info("Benchmarking the time to deserialize a DAWG");
      		startTime = System.nanoTime();
      		dawg = serializer.deserialize(SortedDawg.class, bytes);
      		stopTime = System.nanoTime();
      		final double deserializationTime = (stopTime - startTime);

					log.info("Recording the benchmarked, build time: [{}] nanoseconds", buildTime);
      		synchronized (buildTimeSum) {
      			buildTimeSum.set(buildTime + buildTimeSum.get());
      		}

					log.info("Recording the benchmarked, deserialization time: [{}] nanoseconds", deserializationTime);
      		synchronized (deserializationTimeSum) {
      			deserializationTimeSum.set(deserializationTime + deserializationTimeSum.get());
      		}
  			}
  			else if (numIters > 0) {
  				final int lhsIters = numIters >> 1;
  				final int rhsIters = numIters - lhsIters;
  				log.info("Splitting benchmark into two, subtasks of sizes [{}] and [{}]",
  					lhsIters, rhsIters);
  				invokeAll(new RuntimeAverager(serializer,
  						                        	lhsIters,
  						                        	buildTimeSum,
  						                        	deserializationTimeSum),
  					      	new RuntimeAverager(serializer,
  					      	                  	rhsIters,
  					      	                  	buildTimeSum,
  					      	                  	deserializationTimeSum));
  			}
  		}
  		catch (final Throwable thrown) {
  			final String message = "Failed to benchmark the average runtimes";
  			log.error(message, thrown);
  			throw new RuntimeException(message, thrown);
  		}
  	}
  }
}
