[Index](index.md) > [Testing](testing.md)

# Testing

Run `gradle test`:

```
% gradle test
[buildinfo] Not using buildInfo properties file for this build.
:extractIncludeProto UP-TO-DATE
:extractProto UP-TO-DATE
:generateProto UP-TO-DATE
:compileJava UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:extractIncludeTestProto
:extractTestProto
:generateTestProto UP-TO-DATE
:compileTestJavawarning: No processor claimed any of these annotations: org.testng.annotations.BeforeTest,lombok.extern.slf4j.Slf4j,org.testng.annotations.DataProvider,lombok.Getter,org.testng.annotations.BeforeClass,lombok.RequiredArgsConstructor,org.testng.annotations.BeforeMethod,org.testng.annotations.Test
1 warning

:processTestResources
:testClasses
:test

Gradle suite > Gradle test STANDARD_OUT
    01:14:42.264 [Test worker] [34mINFO [0;39m [36mc.g.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from url [file:/tmp/GenerateWikidoc-6908592161293780084/liblevenshtein-java/build/resources/test/programming-languages.protobuf.bytes]
    01:14:42.270 [Test worker] [34mINFO [0;39m [36mc.g.l.s.ProtobufSerializer[0;39m - Deserializing an instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from a stream
    01:14:42.313 [Test worker] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [673] terms with isSorted [false], algorithm [MERGE_AND_SPLIT], defaultMaxDistance [3], and includeDistance [true]
    01:14:42.319 [Test worker] [34mINFO [0;39m [36mc.g.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from url [file:/tmp/GenerateWikidoc-6908592161293780084/liblevenshtein-java/build/resources/test/programming-languages.protobuf.bytes]
    01:14:42.319 [Test worker] [34mINFO [0;39m [36mc.g.l.s.ProtobufSerializer[0;39m - Deserializing an instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from a stream
    01:14:42.325 [Test worker] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [673] terms with isSorted [true], algorithm [STANDARD], defaultMaxDistance [3], and includeDistance [true]
    01:14:42.326 [Test worker] [34mINFO [0;39m [36mc.g.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from url [file:/tmp/GenerateWikidoc-6908592161293780084/liblevenshtein-java/build/resources/test/programming-languages.protobuf.bytes]
    01:14:42.326 [Test worker] [34mINFO [0;39m [36mc.g.l.s.ProtobufSerializer[0;39m - Deserializing an instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from a stream
    01:14:42.331 [Test worker] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [673] terms with isSorted [false], algorithm [TRANSPOSITION], defaultMaxDistance [3], and includeDistance [true]

Gradle suite > Gradle test > com.github.liblevenshtein.assertion.SetAssertionsTest.testIsEmpty STANDARD_OUT
    01:14:42.361 [pool-1-thread-54] [34mINFO [0;39m [36mc.g.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from url [file:/tmp/GenerateWikidoc-6908592161293780084/liblevenshtein-java/build/resources/test/top-20-most-common-english-words.protobuf.bytes]

Gradle suite > Gradle test > com.github.liblevenshtein.assertion.SetAssertionsTest.testIsEqualTo STANDARD_OUT
    01:14:42.362 [pool-1-thread-54] [34mINFO [0;39m [36mc.g.l.s.ProtobufSerializer[0;39m - Deserializing an instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from a stream
    01:14:42.362 [pool-1-thread-54] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [20] terms with isSorted [false], algorithm [STANDARD], defaultMaxDistance [0], and includeDistance [true]
    01:14:42.363 [pool-1-thread-54] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [20] terms with isSorted [false], algorithm [TRANSPOSITION], defaultMaxDistance [0], and includeDistance [true]
    01:14:42.363 [pool-1-thread-54] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [20] terms with isSorted [false], algorithm [MERGE_AND_SPLIT], defaultMaxDistance [0], and includeDistance [true]
    01:14:42.364 [pool-1-thread-54] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [20] terms with isSorted [false], algorithm [STANDARD], defaultMaxDistance [0], and includeDistance [false]

Gradle suite > Gradle test > com.github.liblevenshtein.assertion.SetAssertionsTest.testSize STANDARD_OUT
    01:14:42.364 [pool-1-thread-54] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [20] terms with isSorted [false], algorithm [TRANSPOSITION], defaultMaxDistance [0], and includeDistance [false]

Gradle suite > Gradle test > com.github.liblevenshtein.assertion.DistanceFunctionAssertionsTest.testHasDistance STANDARD_OUT
    01:14:42.364 [pool-1-thread-54] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [20] terms with isSorted [false], algorithm [MERGE_AND_SPLIT], defaultMaxDistance [0], and includeDistance [false]
    01:14:42.365 [pool-1-thread-54] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [20] terms with isSorted [false], algorithm [STANDARD], defaultMaxDistance [2], and includeDistance [true]

Gradle suite > Gradle test > com.github.liblevenshtein.assertion.CandidateAssertionsTest.testHasDistance STANDARD_OUT
    01:14:42.365 [pool-1-thread-54] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [20] terms with isSorted [false], algorithm [TRANSPOSITION], defaultMaxDistance [2], and includeDistance [true]

Gradle suite > Gradle test > com.github.liblevenshtein.assertion.DistanceAssertionsTest.testEqualSelfSimilarityAgainstDifferingDistances STANDARD_OUT
    01:14:42.365 [pool-1-thread-54] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [20] terms with isSorted [false], algorithm [MERGE_AND_SPLIT], defaultMaxDistance [2], and includeDistance [true]
# ... TRUNCATED ...


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
[buildinfo] Not using buildInfo properties file for this build.
:clean
:extractIncludeProto
:extractProto UP-TO-DATE
:generateProto
:compileJavawarning: No processor claimed any of these annotations: lombok.Setter,lombok.Getter,lombok.experimental.ExtensionMethod,lombok.NonNull,lombok.RequiredArgsConstructor,lombok.EqualsAndHashCode,lombok.Value,lombok.extern.slf4j.Slf4j,lombok.Builder,lombok.ToString,lombok.Data,lombok.AllArgsConstructor,lombok.NoArgsConstructor
/tmp/GenerateWikidoc-6908592161293780084/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:21: warning: [rawtypes] found raw type: SymmetricImmutablePair
    final SymmetricImmutablePair key = new SymmetricImmutablePair<String>(v, w);
          ^
  missing type arguments for generic class SymmetricImmutablePair<Type>
  where Type is a type-variable:
    Type extends Comparable<Type> declared in class SymmetricImmutablePair
/tmp/GenerateWikidoc-6908592161293780084/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:30: warning: [unchecked] unchecked method invocation: method put in interface Object2IntFunction is applied to given types
      memo.put(key, distance);
              ^
  required: K,int
  found: SymmetricImmutablePair,int
  where K is a type-variable:
    K extends Object declared in interface Object2IntFunction
/tmp/GenerateWikidoc-6908592161293780084/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:30: warning: [unchecked] unchecked conversion
      memo.put(key, distance);
               ^
  required: K
  found:    SymmetricImmutablePair
  where K is a type-variable:
    K extends Object declared in interface Object2IntFunction
/tmp/GenerateWikidoc-6908592161293780084/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:36: warning: [unchecked] unchecked method invocation: method put in interface Object2IntFunction is applied to given types
      memo.put(key, distance);
              ^
  required: K,int
  found: SymmetricImmutablePair,int
  where K is a type-variable:
    K extends Object declared in interface Object2IntFunction
/tmp/GenerateWikidoc-6908592161293780084/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:36: warning: [unchecked] unchecked conversion
      memo.put(key, distance);
               ^
  required: K
  found:    SymmetricImmutablePair
  where K is a type-variable:
    K extends Object declared in interface Object2IntFunction
/tmp/GenerateWikidoc-6908592161293780084/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:52: warning: [unchecked] unchecked method invocation: method put in interface Object2IntFunction is applied to given types
      memo.put(key, distance);
              ^
  required: K,int
  found: SymmetricImmutablePair,int
  where K is a type-variable:
    K extends Object declared in interface Object2IntFunction
/tmp/GenerateWikidoc-6908592161293780084/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:52: warning: [unchecked] unchecked conversion
      memo.put(key, distance);
               ^
# ... TRUNCATED ...


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
[klaus-schulz]: http://www.cis.uni-muenchen.de/people/schulz.html "Klaus Schulz"
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

[master-branch]: https://github.com/universal-automata/liblevenshtein-java/tree/master "universal-automata/liblevenshtein-java/master"
[release-branch]: https://github.com/universal-automata/liblevenshtein-java/tree/release "universal-automata/liblevenshtein-java/release"
[release-branch-3.x]: https://github.com/universal-automata/liblevenshtein-java/tree/release-3.x "universal-automata/liblevenshtein-java/release-3.x"
[release-branch-2.x]: https://github.com/universal-automata/liblevenshtein-java/tree/release-2.x "universal-automata/liblevenshtein-java/release-2.x"

[wiki]: https://github.com/universal-automata/liblevenshtein-java/blob/gh-pages/docs/wiki/3.0.0-rc.1/index.md "liblevenshtein 3.0.0-rc.1 Wiki"
[javadoc]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/index.html "liblevenshtein 3.0.0-rc.1 API"
[tagged-source]: https://github.com/universal-automata/liblevenshtein-java/tree/3.0.0-rc.1/src "liblevenshtein 3.0.0-rc.1"

[java-lib]: https://github.com/universal-automata/liblevenshtein-java "liblevenshtein-java"
[java-cli]: https://github.com/universal-automata/liblevenshtein-java-cli "liblevenshtein-java-cli"
[java-cli-readme]: https://github.com/universal-automata/liblevenshtein-java-cli/blob/master/README.md "liblevenshtein-java-cli, README.md"

[javadoc/Iterable]: https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html?is-external=true "java.lang.Iterable"
[javadoc/Iterator.next()]: https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html#next-- "java.util.Iterator.next()"
[javadoc/Iterator]: https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html "java.util.Iterator"
[javadoc/String]: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html "java.lang.String"

[javadoc/Algorithm.MERGE_AND_SPLIT]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/transducer/Algorithm.html#MERGE_AND_SPLIT "Algorithm.MERGE_AND_SPLIT"
[javadoc/Algorithm.STANDARD]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/transducer/Algorithm.html#STANDARD "Algorithm.STANDARD"
[javadoc/Algorithm.TRANSPOSITION]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/transducer/Algorithm.html#TRANSPOSITION "Algorithm.TRANSPOSITION"
[javadoc/ITransducer.transduce(String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/transducer/ITransducer.html#transduce-java.lang.String- "ITransducer.transduce(String):Iterable"
[javadoc/ITransducer.transduce(String,int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/transducer/ITransducer.html#transduce-java.lang.String-int- "ITransducer.transduce(String,int):Iterable"
[javadoc/MemoizedMergeAndSplit.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.html "MemoizedMergeAndSplit.between(String,String):int"
[javadoc/MemoizedStandard.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/distance/MemoizedStandard.html "MemoizedStandard.between(String,String):int"
[javadoc/MemoizedTransposition.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/distance/MemoizedTransposition.html "MemoizedTransposition.between(String,String):int"
[javadoc/TransducerBuilder.algorithm(Algorithm)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#algorithm-com.github.liblevenshtein.Algorithm- "TransducerBuilder.algorithm(Algorithm):TransducerBuilder"
[javadoc/TransducerBuilder.build()]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#build-- "TransducerBuilder.build():ITransducer"
[javadoc/TransducerBuilder.defaultMaxDistance(int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#defaultMaxDistance-int- "TransducerBuilder.defaultMaxDistance(int):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#dictionary-java.util.Collection- "TransducerBuilder.dictionary(Collection):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection,boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#dictionary-java.util.Collection-boolean- "TransducerBuilder.dictionary(Collection,boolean):TransducerBuilder"
[javadoc/TransducerBuilder.includeDistance(boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-rc.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#includeDistance-boolean- "TransducerBuilder.includeDistance(boolean):TransducerBuilder"

[src/Candidate]: https://github.com/universal-automata/liblevenshtein-java/blob/master/src/main/java/com/github/liblevenshtein/transducer/Candidate.java "Candidate.java"
[src/ITransducer]: https://github.com/universal-automata/liblevenshtein-java/blob/3.0.0-rc.1/src/main/java/com/github/liblevenshtein/transducer/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/TransducerBuilder.java]: https://github.com/universal-automata/liblevenshtein-java/blob/3.0.0-rc.1/src/main/java/com/github/liblevenshtein/transducer/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/build.gradle]: https://github.com/universal-automata/liblevenshtein-java/blob/3.0.0-rc.1/build.gradle "build.gradle"

[top-20-most-common-english-words.txt]: https://raw.githubusercontent.com/universal-automata/liblevenshtein-java/3.0.0-rc.1/src/test/resources/top-20-most-common-english-words.txt "top-20-most-common-english-words.txt"

[maven-repo]: https://repo1.maven.org/maven2 "Maven Central repository"
[jcenter-repo]: https://jcenter.bintray.com "JCenter repository"
[bintray-repo]: https://dl.bintray.com/universal-automata/liblevenshtein "Bintray repository"
[artifactory-repo]: https://oss.jfrog.org/artifactory/oss-release-local "Artifactory repository"

[gradle-home]: http://gradle.org/ "Gradle homepage"
