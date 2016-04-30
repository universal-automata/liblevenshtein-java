[Index](index.md) > [Testing](testing.md)

# Testing

Run `gradle test`:

```
% gradle test
:extractIncludeProto UP-TO-DATE
:extractProto UP-TO-DATE
:generateProto UP-TO-DATE
:compileJava UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:extractIncludeTestProto
:extractTestProto
:generateTestProto UP-TO-DATE
:compileTestJava/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
warning: No processor claimed any of these annotations: lombok.extern.slf4j.Slf4j,org.testng.annotations.BeforeTest,lombok.Getter,org.testng.annotations.DataProvider,org.testng.annotations.BeforeClass,lombok.RequiredArgsConstructor,org.testng.annotations.BeforeMethod,org.testng.annotations.Test
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:12: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    extends AbstractAssert<CandidateCollectionAssertions<Type>, CandidateCollection<Type>> {
                                                                ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:18: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
  public CandidateCollectionAssertions(final CandidateCollection<Type> actual) {
                                             ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:29: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
      final CandidateCollection<Type> actual) {
            ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/CandidateCollectionTest.java:14: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    val candidates = new CandidateCollection.WithoutDistance(3);
    ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/CandidateCollectionTest.java:14: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    val candidates = new CandidateCollection.WithoutDistance(3);
                         ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/CandidateCollectionTest.java:26: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    val candidates = new CandidateCollection.WithDistance(3);
    ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/CandidateCollectionTest.java:26: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    val candidates = new CandidateCollection.WithDistance(3);
                         ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/CandidateCollectionTest.java:36: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
  private void enqueueCandidates(final CandidateCollection<?> candidates) {
                                       ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:17: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
  private final ThreadLocal<CandidateCollection<String>> candidates = new ThreadLocal<>();
                            ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:22: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    candidates.set(mock(CandidateCollection.class));
                        ^
19 warnings

:processTestResources
:testClasses
:test

Gradle suite > Gradle test STANDARD_OUT
    15:07:59.890 [Test worker] [34mINFO [0;39m [36mc.g.d.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.dylon.liblevenshtein.collection.dawg.SortedDawg] from url [file:/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/build/resources/test/programming-languages.protobuf.bytes]
    15:07:59.896 [Test worker] [34mINFO [0;39m [36mc.g.d.l.s.ProtobufSerializer[0;39m - Deserializing an instance of [class com.github.dylon.liblevenshtein.collection.dawg.SortedDawg] from a stream
    15:07:59.939 [Test worker] [34mINFO [0;39m [36mc.g.d.l.l.factory.TransducerBuilder[0;39m - Building transducer out of [673] terms with algorithm [MERGE_AND_SPLIT], defaultMaxDistance [3], includeDistance [true], and maxCandidates [2147483647]
    15:07:59.946 [Test worker] [34mINFO [0;39m [36mc.g.d.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.dylon.liblevenshtein.collection.dawg.SortedDawg] from url [file:/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/build/resources/test/programming-languages.protobuf.bytes]
...

Gradle suite > Gradle test STANDARD_OUT
    15:08:01.063 [pool-1-thread-28] [34mINFO [0;39m [36mc.g.d.l.collection.dawg.AbstractDawg[0;39m - Added [100000] of [109582] terms

BUILD SUCCESSFUL

Total time: 36.261 secs

This build could be faster, please consider using the Gradle Daemon: https://docs.gradle.org/2.12/userguide/gradle_daemon.html


```

Note that if you want to ensure you are not testing against build artifacts,
please run `gradle clean test`, instead.  It will be a little slower on
successive runs, but will ensure no old, Java classes are left in the `build`
directory.

# Preparing for release

Before releasing your code, or checking it back into the shared repositories,
you should run all the checks against it and clean up any errors:

```
% gradle clean check
:clean
:extractIncludeProto
:extractProto UP-TO-DATE
:generateProto
:compileJavawarning: No processor claimed any of these annotations: lombok.Setter,lombok.experimental.ExtensionMethod,lombok.Getter,lombok.NonNull,lombok.RequiredArgsConstructor,lombok.EqualsAndHashCode,lombok.Value,lombok.extern.slf4j.Slf4j,lombok.ToString,lombok.Data,lombok.AllArgsConstructor,lombok.NoArgsConstructor
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/CandidateCollectionBuilder.java:50: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
      return new CandidateCollection.WithDistance(maxCandidates);
                 ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/CandidateCollectionBuilder.java:70: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
      return new CandidateCollection.WithoutDistance(maxCandidates);
                 ^
3 warnings

:processResources
:classes
:checkstyleMain[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/main/java/com/github/dylon/liblevenshtein/collection/dawg/factory/DawgFactory.java:82: Comment matches to-do format '\b(TODO|FIXME)\b'. [TodoComment]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/main/java/com/github/dylon/liblevenshtein/levenshtein/CandidateCollection.java:56: Comment matches to-do format '\b(TODO|FIXME)\b'. [TodoComment]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/main/java/com/github/dylon/liblevenshtein/levenshtein/LazyTransducerCollection.java:235:20: 'term' hides a field. [HiddenField]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/main/java/com/github/dylon/liblevenshtein/levenshtein/LazyTransducerCollection.java:236:17: 'k' hides a field. [HiddenField]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/main/java/com/github/dylon/liblevenshtein/levenshtein/LazyTransducerCollection.java:237:17: 'i' hides a field. [HiddenField]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/main/java/com/github/dylon/liblevenshtein/levenshtein/State.java:335:54: 'head' hides a field. [HiddenField]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.java:138:74: 'dictionary' hides a field. [HiddenField]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.java:147:41: 'dictionary' hides a field. [HiddenField]

:checkstyleMainHtml
:extractIncludeTaskProto
:extractTaskProto
:generateTaskProto UP-TO-DATE
:compileTaskJavawarning: No processor claimed any of these annotations: lombok.extern.slf4j.Slf4j,lombok.Getter,lombok.SneakyThrows,edu.umd.cs.findbugs.annotations.SuppressFBWarnings
1 warning

:processTaskResources
:taskClasses
:checkstyleTask[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/task/java/com/github/dylon/liblevenshtein/task/Action.java:387:25: 'cli' hides a field. [HiddenField]

:checkstyleTaskHtml
:extractIncludeTestProto
:extractTestProto UP-TO-DATE
:generateTestProto UP-TO-DATE
:compileTestJava/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
warning: No processor claimed any of these annotations: lombok.extern.slf4j.Slf4j,org.testng.annotations.BeforeTest,lombok.Getter,org.testng.annotations.DataProvider,org.testng.annotations.BeforeClass,lombok.RequiredArgsConstructor,org.testng.annotations.BeforeMethod,org.testng.annotations.Test
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
import com.github.dylon.liblevenshtein.levenshtein.CandidateCollection;
                                                  ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:12: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    extends AbstractAssert<CandidateCollectionAssertions<Type>, CandidateCollection<Type>> {
                                                                ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:18: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
  public CandidateCollectionAssertions(final CandidateCollection<Type> actual) {
                                             ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertions.java:29: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
      final CandidateCollection<Type> actual) {
            ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/CandidateCollectionTest.java:14: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    val candidates = new CandidateCollection.WithoutDistance(3);
    ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/CandidateCollectionTest.java:14: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    val candidates = new CandidateCollection.WithoutDistance(3);
                         ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/CandidateCollectionTest.java:26: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    val candidates = new CandidateCollection.WithDistance(3);
    ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/CandidateCollectionTest.java:26: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    val candidates = new CandidateCollection.WithDistance(3);
                         ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/CandidateCollectionTest.java:36: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
  private void enqueueCandidates(final CandidateCollection<?> candidates) {
                                       ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:17: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
  private final ThreadLocal<CandidateCollection<String>> candidates = new ThreadLocal<>();
                            ^
/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:22: warning: [deprecation] CandidateCollection in com.github.dylon.liblevenshtein.levenshtein has been deprecated
    candidates.set(mock(CandidateCollection.class));
                        ^
19 warnings

:processTestResources
:testClasses
:checkstyleTest[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/StateTransitionFunctionTest.java:206: Comment matches to-do format '\b(TODO|FIXME)\b'. [TodoComment]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/src/test/java/com/github/dylon/liblevenshtein/levenshtein/StateTransitionFunctionTest.java:243: Comment matches to-do format '\b(TODO|FIXME)\b'. [TodoComment]

:checkstyleTestHtml
:findbugsMain
:findbugsTask
:findbugsTest
:test

Gradle suite > Gradle test STANDARD_OUT
    15:08:55.324 [Test worker] [34mINFO [0;39m [36mc.g.d.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.dylon.liblevenshtein.collection.dawg.SortedDawg] from url [file:/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/build/resources/test/programming-languages.protobuf.bytes]
    15:08:55.329 [Test worker] [34mINFO [0;39m [36mc.g.d.l.s.ProtobufSerializer[0;39m - Deserializing an instance of [class com.github.dylon.liblevenshtein.collection.dawg.SortedDawg] from a stream
    15:08:55.373 [Test worker] [34mINFO [0;39m [36mc.g.d.l.l.factory.TransducerBuilder[0;39m - Building transducer out of [673] terms with algorithm [MERGE_AND_SPLIT], defaultMaxDistance [3], includeDistance [true], and maxCandidates [2147483647]
    15:08:55.381 [Test worker] [34mINFO [0;39m [36mc.g.d.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.dylon.liblevenshtein.collection.dawg.SortedDawg] from url [file:/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/build/resources/test/programming-languages.protobuf.bytes]
    15:08:55.381 [Test worker] [34mINFO [0;39m [36mc.g.d.l.s.ProtobufSerializer[0;39m - Deserializing an instance of [class com.github.dylon.liblevenshtein.collection.dawg.SortedDawg] from a stream
    15:08:55.385 [Test worker] [34mINFO [0;39m [36mc.g.d.l.l.factory.TransducerBuilder[0;39m - Building transducer out of [673] terms with algorithm [STANDARD], defaultMaxDistance [3], includeDistance [true], and maxCandidates [2147483647]
    15:08:55.386 [Test worker] [34mINFO [0;39m [36mc.g.d.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.dylon.liblevenshtein.collection.dawg.SortedDawg] from url [file:/tmp/GenerateWikidoc-9159085470689403334/liblevenshtein-java/build/resources/test/programming-languages.protobuf.bytes]
    15:08:55.386 [Test worker] [34mINFO [0;39m [36mc.g.d.l.s.ProtobufSerializer[0;39m - Deserializing an instance of [class com.github.dylon.liblevenshtein.collection.dawg.SortedDawg] from a stream
    15:08:55.389 [Test worker] [34mINFO [0;39m [36mc.g.d.l.l.factory.TransducerBuilder[0;39m - Building transducer out of [673] terms with algorithm [TRANSPOSITION], defaultMaxDistance [3], includeDistance [true], and maxCandidates [2147483647]
...

Gradle suite > Gradle test STANDARD_OUT
    15:08:56.536 [pool-1-thread-28] [34mINFO [0;39m [36mc.g.d.l.collection.dawg.AbstractDawg[0;39m - Added [90000] of [109582] terms
    15:08:56.555 [pool-1-thread-28] [34mINFO [0;39m [36mc.g.d.l.collection.dawg.AbstractDawg[0;39m - Added [100000] of [109582] terms
:check

BUILD SUCCESSFUL

Total time: 53.276 secs

This build could be faster, please consider using the Gradle Daemon: https://docs.gradle.org/2.12/userguide/gradle_daemon.html
```

[liblevenshtein-java][github-repo] is maintained by[@dylon][github-author] ([dylon.devo+liblevenshtein-java@gmail.com][github-email])

[coursera-automata]: https://class.coursera.org/automata "Jeffrey Ullman (Coursera)"
[coursera-compilers]: https://class.coursera.org/compilers "Alex Aiken (Coursera)"
[coursera-nlp]: https://class.coursera.org/nlp "Dan Jurafsky and Chris Manning (Coursera)"
[damn-cool-algos-levenshtein-automata-2010]: http://blog.notdot.net/2010/07/Damn-Cool-Algorithms-Levenshtein-Automata "Nick Johnson (2010)"
[dict-compress-dawg-2011]: http://stevehanov.ca/blog/index.php?id=115 "Steve Hanov (2011)"
[fast-easy-correct-trie-2011]: http://stevehanov.ca/blog/index.php?id=114 "Steve Hanov (2011)"
[fast-string-correction-2002]: http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.16.652 "Klaus Schulz and Stoyan Mihov (2002)"
[incremental-construction-dawg-2000]: http://dl.acm.org/citation.cfm?id=971842 "Jan Daciuk, Bruce W. Watson, Stoyan Mihov, and Richard E. Watson (2000)"
[klaus-schulz]: http://www.klaus-schulze.com/ "Klaus Schulz"
[lucene-fuzzy-2011]: http://blog.mikemccandless.com/2011/03/lucenes-fuzzyquery-is-100-times-faster.html "Michael McCandless (2011)"
[moman]: https://sites.google.com/site/rrettesite/moman "Moman"
[rao-li]: http://www.usca.edu/math/~mathdept/rli/ "Dr. Rao Li"
[stoyan-mihov]: http://www.lml.bas.bg/~stoyan/ "Stoyan Mihov"
[universal-automata-2005]: http://www.fmi.uni-sofia.bg/fmi/logic/theses/mitankin-en.pdf "Petar Nikolaev Mitankin (2005)"
[usca]: http://web.usca.edu/ "University of South Carolina Aiken"

[live-demo]: http://universal-automata.github.io/liblevenshtein/

[github-author]: https://github.com/dylon "Dylon Edwards <dylon.devo+liblevenshtein-java@gmail.com>"
[github-demo]: http://universal-automata.github.io/liblevenshtein/ "liblevenshtein demo"
[github-email]: mailto:dylon.devo+liblevenshtein-java@gmail.com "Dylon Edwards <dylon.devo+liblevenshtein-java@gmail.com>"
[github-repo]: https://github.com/universal-automata/liblevenshtein-java/ "universal-automata/liblevenshtein-java"

[wikipedia-damerau-levenshtein-distance]: https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance "Damerau–Levenshtein distance"
[wikipedia-levenshtein-distance]: https://en.wikipedia.org/wiki/Levenshtein_distance "Levenshtein distance"

[master-branch]: https://github.com/universal-automata/liblevenshtein-java/tree/master
[release-branch]: https://github.com/universal-automata/liblevenshtein-java/tree/release

[wiki]: http://universal-automata.github.io/liblevenshtein-java/docs/wiki/2.2.2/index.md "liblevenshtein 2.2.2 Wiki"
[javadoc]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/index.html "liblevenshtein 2.2.2 API"
[tagged-source]: https://github.com/universal-automata/liblevenshtein-java/tree/2.2.2/src "liblevenshtein 2.2.2"

[java-lib]: https://github.com/universal-automata/liblevenshtein-java "liblevenshtein-java"
[java-cli]: https://github.com/universal-automata/liblevenshtein-java-cli "liblevenshtein-java-cli"
[java-cli-readme]: https://github.com/universal-automata/liblevenshtein-java-cli/blob/master/README.md "liblevenshtein-java-cli, README.md"

[javadoc/Iterable]: https://docs.oracle.com/javase/1.8/docs/api/java/lang/Iterable.html?is-external=true "java.lang.Iterable"
[javadoc/Iterator.next()]: https://docs.oracle.com/javase/1.8/docs/api/java/util/Iterator.html#next-- "java.util.Iterator.next()"
[javadoc/Iterator]: https://docs.oracle.com/javase/1.8/docs/api/java/util/Iterator.html "java.util.Iterator"
[javadoc/String]: https://docs.oracle.com/javase/1.8/docs/api/java/lang/String.html "java.lang.String"

[javadoc/Algorithm.MERGE_AND_SPLIT]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#MERGE_AND_SPLIT "Algorithm.MERGE_AND_SPLIT"
[javadoc/Algorithm.STANDARD]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#STANDARD "Algorithm.STANDARD"
[javadoc/Algorithm.TRANSPOSITION]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#TRANSPOSITION "Algorithm.TRANSPOSITION"
[javadoc/ICandidateCollection]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/ICandidateCollection.html "ICandidateCollection"
[javadoc/ITransducer.transduce(String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/ITransducer.html#transduce-java.lang.String- "ITransducer.transduce(String):ICandidateCollection"
[javadoc/ITransducer.transduce(String,int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/ITransducer.html#transduce-java.lang.String-int- "ITransducer.transduce(String,int):ICandidateCollection"
[javadoc/MemoizedMergeAndSplit.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedMergeAndSplit.html "MemoizedMergeAndSplit.between(String,String):int"
[javadoc/MemoizedStandard.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedStandard.html "MemoizedStandard.between(String,String):int"
[javadoc/MemoizedTransposition.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedTransposition.html "MemoizedTransposition.between(String,String):int"
[javadoc/TransducerBuilder.algorithm(Algorithm)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#algorithm-com.github.dylon.liblevenshtein.levenshtein.Algorithm- "TransducerBuilder.algorithm(Algorithm):TransducerBuilder"
[javadoc/TransducerBuilder.build()]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#build-- "TransducerBuilder.build():ITransducer"
[javadoc/TransducerBuilder.defaultMaxDistance(int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#defaultMaxDistance-int- "TransducerBuilder.defaultMaxDistance(int):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#dictionary-java.util.Collection- "TransducerBuilder.dictionary(Collection):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection,boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#dictionary-java.util.Collection-boolean- "TransducerBuilder.dictionary(Collection,boolean):TransducerBuilder"
[javadoc/TransducerBuilder.includeDistance(boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#includeDistance-boolean- "TransducerBuilder.includeDistance(boolean):TransducerBuilder"
[javadoc/TransducerBuilder.maxCandidates(int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.2/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#maxCandidates-int- "TransducerBuilder.maxCandidates(int):TransducerBuilder"

[src/Candidate]: https://github.com/universal-automata/liblevenshtein-java/blob/master/src/main/java/com/github/dylon/liblevenshtein/levenshtein/Candidate.java "Candidate.java"
[src/ITransducer]: https://github.com/universal-automata/liblevenshtein-java/blob/2.2.2/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/TransducerBuilder.java]: https://github.com/universal-automata/liblevenshtein-java/blob/2.2.2/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/build.gradle]: https://github.com/universal-automata/liblevenshtein-java/blob/2.2.2/build.gradle "build.gradle"
