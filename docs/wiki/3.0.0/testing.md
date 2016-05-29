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
:compileTestJavawarning: No processor claimed any of these annotations: lombok.extern.slf4j.Slf4j,org.testng.annotations.BeforeTest,org.testng.annotations.DataProvider,lombok.Getter,org.testng.annotations.BeforeClass,org.testng.annotations.BeforeMethod,lombok.RequiredArgsConstructor,org.testng.annotations.Test
1 warning

:processTestResources
:testClasses
:testobjc[67138]: Class JavaLaunchHelper is implemented in both /Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/bin/java and /Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib/libinstrument.dylib. One of the two will be used. Which one is undefined.


Gradle suite > Gradle test STANDARD_OUT
    20:51:11.705 [Test worker] [34mINFO [0;39m [36mc.g.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from url [file:/private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/build/resources/test/programming-languages.protobuf.bytes]
    20:51:11.710 [Test worker] [34mINFO [0;39m [36mc.g.l.s.ProtobufSerializer[0;39m - Deserializing an instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from a stream
    20:51:11.756 [Test worker] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [673] terms with isSorted [false], algorithm [MERGE_AND_SPLIT], defaultMaxDistance [3], and includeDistance [true]
    20:51:11.762 [Test worker] [34mINFO [0;39m [36mc.g.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from url [file:/private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/build/resources/test/programming-languages.protobuf.bytes]
    20:51:11.762 [Test worker] [34mINFO [0;39m [36mc.g.l.s.ProtobufSerializer[0;39m - Deserializing an instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from a stream
    20:51:11.767 [Test worker] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [673] terms with isSorted [true], algorithm [STANDARD], defaultMaxDistance [3], and includeDistance [true]
    20:51:11.769 [Test worker] [34mINFO [0;39m [36mc.g.l.s.AbstractSerializer[0;39m - Deserilizing instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from url [file:/private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/build/resources/test/programming-languages.protobuf.bytes]
    20:51:11.769 [Test worker] [34mINFO [0;39m [36mc.g.l.s.ProtobufSerializer[0;39m - Deserializing an instance of [class com.github.liblevenshtein.collection.dictionary.SortedDawg] from a stream
    20:51:11.775 [Test worker] [34mINFO [0;39m [36mc.g.l.t.factory.TransducerBuilder[0;39m - Building transducer out of [673] terms with isSorted [false], algorithm [TRANSPOSITION], defaultMaxDistance [3], and includeDistance [true]
    20:51:11.971 [pool-1-thread-2] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [10000] of [109582] terms
    20:51:11.995 [pool-1-thread-2] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [20000] of [109582] terms
    20:51:12.021 [pool-1-thread-2] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [30000] of [109582] terms
    20:51:12.046 [pool-1-thread-2] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [40000] of [109582] terms
    20:51:12.066 [pool-1-thread-2] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [50000] of [109582] terms
    20:51:12.085 [pool-1-thread-2] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [60000] of [109582] terms
    20:51:12.105 [pool-1-thread-2] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [70000] of [109582] terms
    20:51:12.128 [pool-1-thread-2] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [80000] of [109582] terms
    20:51:12.150 [pool-1-thread-2] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [90000] of [109582] terms
    20:51:12.176 [pool-1-thread-2] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [100000] of [109582] terms
    20:51:12.264 [pool-1-thread-3] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [10000] of [109582] terms
    20:51:12.336 [pool-1-thread-3] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [20000] of [109582] terms

Gradle suite > Gradle test > com.github.liblevenshtein.distance.factory.MemoizedDistanceFactoryTest.testEqualSelfSimilarity[105](STANDARD, com.github.liblevenshtein.distance.MemoizedStandard@60e4c589, a, a) STANDARD_OUT
    20:51:12.401 [pool-1-thread-3] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [30000] of [109582] terms

Gradle suite > Gradle test STANDARD_OUT
    20:51:12.476 [pool-1-thread-3] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [40000] of [109582] terms

Gradle suite > Gradle test > com.github.liblevenshtein.distance.factory.MemoizedDistanceFactoryTest.testSymmetry[435](MERGE_AND_SPLIT, com.github.liblevenshtein.distance.MemoizedMergeAndSplit@229ce99f, be, he) STANDARD_OUT
    20:51:12.583 [pool-1-thread-3] [34mINFO [0;39m [36mc.g.l.collection.dictionary.Dawg[0;39m - Added [50000] of [109582] terms

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
:compileJavawarning: No processor claimed any of these annotations: lombok.Setter,lombok.Getter,lombok.experimental.ExtensionMethod,lombok.NonNull,lombok.RequiredArgsConstructor,lombok.EqualsAndHashCode,lombok.Value,lombok.extern.slf4j.Slf4j,lombok.Builder,lombok.Data,lombok.ToString,lombok.AllArgsConstructor,lombok.NoArgsConstructor
1 warning

:processResources
:classes
:extractIncludeTestProto
:extractTestProto UP-TO-DATE
:generateTestProto UP-TO-DATE
:compileTestJavawarning: No processor claimed any of these annotations: lombok.extern.slf4j.Slf4j,org.testng.annotations.BeforeTest,org.testng.annotations.DataProvider,lombok.Getter,org.testng.annotations.BeforeClass,org.testng.annotations.BeforeMethod,lombok.RequiredArgsConstructor,org.testng.annotations.Test
1 warning

:processTestResources
:testClasses
:extractIncludeIntegProto
:extractIntegProto
:generateIntegProto UP-TO-DATE
:compileIntegJavawarning: No processor claimed any of these annotations: lombok.extern.slf4j.Slf4j,org.testng.annotations.Test,org.testng.annotations.DataProvider
1 warning

:processIntegResources
:integClasses
:checkstyleInteg
:checkstyleMain[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:20:3: Cyclomatic Complexity is 20 (max allowed is 10). [CyclomaticComplexity]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:20:3: Executable statement count is 53 (max allowed is 30). [ExecutableStatementCount]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:20:3: NCSS for this method is 62 (max allowed is 50). [JavaNCSS]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:20:3: NPath Complexity is 38,400 (max allowed is 200). [NPathComplexity]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:23:9: Variable 'key' should be declared final. [FinalLocalVariable]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:42:5: Only one variable definition per line allowed. [MultipleVariableDeclarations]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:42:52: Only one statement per line allowed. [OneStatementPerLine]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:43:5: Only one variable definition per line allowed. [MultipleVariableDeclarations]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:43:52: Only one statement per line allowed. [OneStatementPerLine]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:47:26: Assignment of parameter 'v' is not allowed. [ParameterAssignment]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:47:29: Only one statement per line allowed. [OneStatementPerLine]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:47:49: Only one statement per line allowed. [OneStatementPerLine]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:48:26: Assignment of parameter 'w' is not allowed. [ParameterAssignment]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:48:29: Only one statement per line allowed. [OneStatementPerLine]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.java:48:49: Only one statement per line allowed. [OneStatementPerLine]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedStandard.java:21:3: Cyclomatic Complexity is 14 (max allowed is 10). [CyclomaticComplexity]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedStandard.java:21:3: Executable statement count is 42 (max allowed is 30). [ExecutableStatementCount]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedStandard.java:21:3: NPath Complexity is 1,536 (max allowed is 200). [NPathComplexity]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedStandard.java:24:9: Variable 'key' should be declared final. [FinalLocalVariable]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedStandard.java:43:5: Only one variable definition per line allowed. [MultipleVariableDeclarations]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedStandard.java:43:52: Only one statement per line allowed. [OneStatementPerLine]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedStandard.java:44:5: Only one variable definition per line allowed. [MultipleVariableDeclarations]
[ant:checkstyle] [WARN] /private/var/folders/x6/m_093hm90hx0stv1jl83643w0000gn/T/GenerateWikidoc-7688351961692878878/liblevenshtein-java/src/main/java/com/github/liblevenshtein/distance/MemoizedStandard.java:44:52: Only one statement per line allowed. [OneStatementPerLine]
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

[wiki]: https://github.com/universal-automata/liblevenshtein-java/blob/gh-pages/docs/wiki/3.0.0/index.md "liblevenshtein 3.0.0 Wiki"
[javadoc]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/index.html "liblevenshtein 3.0.0 API"
[tagged-source]: https://github.com/universal-automata/liblevenshtein-java/tree/3.0.0/src "liblevenshtein 3.0.0"

[java-lib]: https://github.com/universal-automata/liblevenshtein-java "liblevenshtein-java"
[java-cli]: https://github.com/universal-automata/liblevenshtein-java-cli "liblevenshtein-java-cli"
[java-cli-readme]: https://github.com/universal-automata/liblevenshtein-java-cli/blob/master/README.md "liblevenshtein-java-cli, README.md"

[javadoc/Iterable]: https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html?is-external=true "java.lang.Iterable"
[javadoc/Iterator.next()]: https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html#next-- "java.util.Iterator.next()"
[javadoc/Iterator]: https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html "java.util.Iterator"
[javadoc/String]: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html "java.lang.String"

[javadoc/Algorithm.MERGE_AND_SPLIT]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/transducer/Algorithm.html#MERGE_AND_SPLIT "Algorithm.MERGE_AND_SPLIT"
[javadoc/Algorithm.STANDARD]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/transducer/Algorithm.html#STANDARD "Algorithm.STANDARD"
[javadoc/Algorithm.TRANSPOSITION]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/transducer/Algorithm.html#TRANSPOSITION "Algorithm.TRANSPOSITION"
[javadoc/ITransducer.transduce(String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/transducer/ITransducer.html#transduce-java.lang.String- "ITransducer.transduce(String):Iterable"
[javadoc/ITransducer.transduce(String,int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/transducer/ITransducer.html#transduce-java.lang.String-int- "ITransducer.transduce(String,int):Iterable"
[javadoc/MemoizedMergeAndSplit.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.html "MemoizedMergeAndSplit.between(String,String):int"
[javadoc/MemoizedStandard.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/distance/MemoizedStandard.html "MemoizedStandard.between(String,String):int"
[javadoc/MemoizedTransposition.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/distance/MemoizedTransposition.html "MemoizedTransposition.between(String,String):int"
[javadoc/TransducerBuilder.algorithm(Algorithm)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#algorithm-com.github.liblevenshtein.Algorithm- "TransducerBuilder.algorithm(Algorithm):TransducerBuilder"
[javadoc/TransducerBuilder.build()]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#build-- "TransducerBuilder.build():ITransducer"
[javadoc/TransducerBuilder.defaultMaxDistance(int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#defaultMaxDistance-int- "TransducerBuilder.defaultMaxDistance(int):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#dictionary-java.util.Collection- "TransducerBuilder.dictionary(Collection):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection,boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#dictionary-java.util.Collection-boolean- "TransducerBuilder.dictionary(Collection,boolean):TransducerBuilder"
[javadoc/TransducerBuilder.includeDistance(boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#includeDistance-boolean- "TransducerBuilder.includeDistance(boolean):TransducerBuilder"

[src/Candidate]: https://github.com/universal-automata/liblevenshtein-java/blob/master/src/main/java/com/github/liblevenshtein/transducer/Candidate.java "Candidate.java"
[src/ITransducer]: https://github.com/universal-automata/liblevenshtein-java/blob/3.0.0/src/main/java/com/github/liblevenshtein/transducer/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/TransducerBuilder.java]: https://github.com/universal-automata/liblevenshtein-java/blob/3.0.0/src/main/java/com/github/liblevenshtein/transducer/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/build.gradle]: https://github.com/universal-automata/liblevenshtein-java/blob/3.0.0/build.gradle "build.gradle"

[top-20-most-common-english-words.txt]: https://raw.githubusercontent.com/universal-automata/liblevenshtein-java/3.0.0/src/test/resources/top-20-most-common-english-words.txt "top-20-most-common-english-words.txt"

[maven-repo]: https://repo1.maven.org/maven2 "Maven Central repository"
[jcenter-repo]: https://jcenter.bintray.com "JCenter repository"
[bintray-repo]: https://dl.bintray.com/universal-automata/liblevenshtein "Bintray repository"
[artifactory-repo]: https://oss.jfrog.org/artifactory/oss-release-local "Artifactory repository"

[gradle-home]: http://gradle.org/ "Gradle homepage"
