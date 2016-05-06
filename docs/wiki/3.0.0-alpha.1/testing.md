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
:compileTestJava/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
                                           ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
                                           ^
warning: No processor claimed any of these annotations: org.testng.annotations.BeforeTest,lombok.extern.slf4j.Slf4j,org.testng.annotations.DataProvider,lombok.Getter,org.testng.annotations.BeforeClass,lombok.RequiredArgsConstructor,org.testng.annotations.BeforeMethod,org.testng.annotations.Test
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
                                           ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
                                           ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
                                           ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
                                           ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
                                           ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
                                           ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertions.java:12: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
    extends AbstractAssert<CandidateCollectionAssertions<Type>, CandidateCollection<Type>> {
                                                                ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertions.java:18: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
  public CandidateCollectionAssertions(final CandidateCollection<Type> actual) {
                                             ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertions.java:29: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
      final CandidateCollection<Type> actual) {
            ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/transducer/CandidateCollectionTest.java:14: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
    val candidates = new CandidateCollection.WithoutDistance(3);
    ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/transducer/CandidateCollectionTest.java:14: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
    val candidates = new CandidateCollection.WithoutDistance(3);
                         ^
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
:compileJavawarning: No processor claimed any of these annotations: lombok.Setter,lombok.Getter,lombok.experimental.ExtensionMethod,lombok.NonNull,lombok.RequiredArgsConstructor,lombok.EqualsAndHashCode,lombok.Value,lombok.extern.slf4j.Slf4j,lombok.Data,lombok.ToString,lombok.AllArgsConstructor,lombok.NoArgsConstructor
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/main/java/com/github/liblevenshtein/transducer/factory/CandidateCollectionBuilder.java:50: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
      return new CandidateCollection.WithDistance(maxCandidates);
                 ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/main/java/com/github/liblevenshtein/transducer/factory/CandidateCollectionBuilder.java:70: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
      return new CandidateCollection.WithoutDistance(maxCandidates);
                 ^
3 warnings

:processResources
:classes
:checkstyleMain[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/main/java/com/github/liblevenshtein/collection/dawg/factory/DawgFactory.java:82: Comment matches to-do format '\b(TODO|FIXME)\b'. [TodoComment]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/main/java/com/github/liblevenshtein/transducer/CandidateCollection.java:56: Comment matches to-do format '\b(TODO|FIXME)\b'. [TodoComment]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/main/java/com/github/liblevenshtein/transducer/LazyTransducerCollection.java:235:20: 'term' hides a field. [HiddenField]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/main/java/com/github/liblevenshtein/transducer/LazyTransducerCollection.java:236:17: 'k' hides a field. [HiddenField]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/main/java/com/github/liblevenshtein/transducer/LazyTransducerCollection.java:237:17: 'i' hides a field. [HiddenField]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/main/java/com/github/liblevenshtein/transducer/State.java:335:54: 'head' hides a field. [HiddenField]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/main/java/com/github/liblevenshtein/transducer/factory/TransducerBuilder.java:138:74: 'dictionary' hides a field. [HiddenField]
[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/main/java/com/github/liblevenshtein/transducer/factory/TransducerBuilder.java:147:41: 'dictionary' hides a field. [HiddenField]

:extractIncludeTaskProto
:extractTaskProto
:generateTaskProto UP-TO-DATE
:compileTaskJavawarning: No processor claimed any of these annotations: lombok.extern.slf4j.Slf4j,lombok.Getter,lombok.SneakyThrows,edu.umd.cs.findbugs.annotations.SuppressFBWarnings
1 warning

:processTaskResources
:taskClasses
:checkstyleTask[ant:checkstyle] [WARN] /tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/task/java/com/github/liblevenshtein/task/Action.java:448:25: 'cli' hides a field. [HiddenField]

:extractIncludeTestProto
:extractTestProto UP-TO-DATE
:generateTestProto UP-TO-DATE
:compileTestJava/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
                                           ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
                                           ^
warning: No processor claimed any of these annotations: org.testng.annotations.BeforeTest,lombok.extern.slf4j.Slf4j,org.testng.annotations.DataProvider,lombok.Getter,org.testng.annotations.BeforeClass,lombok.RequiredArgsConstructor,org.testng.annotations.BeforeMethod,org.testng.annotations.Test
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertions.java:5: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
                                           ^
/tmp/GenerateWikidoc-344426555866628663/liblevenshtein-java/src/test/java/com/github/liblevenshtein/assertion/CandidateCollectionAssertionsTest.java:12: warning: [deprecation] CandidateCollection in com.github.liblevenshtein.transducer has been deprecated
import com.github.liblevenshtein.transducer.CandidateCollection;
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

[master-branch]: https://github.com/universal-automata/liblevenshtein-java/tree/master
[release-branch]: https://github.com/universal-automata/liblevenshtein-java/tree/release

[wiki]: https://github.com/universal-automata/liblevenshtein-java/blob/gh-pages/docs/wiki/3.0.0-alpha.1/index.md "liblevenshtein 3.0.0-alpha.1 Wiki"
[javadoc]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/index.html "liblevenshtein 3.0.0-alpha.1 API"
[tagged-source]: https://github.com/universal-automata/liblevenshtein-java/tree/3.0.0-alpha.1/src "liblevenshtein 3.0.0-alpha.1"

[java-lib]: https://github.com/universal-automata/liblevenshtein-java "liblevenshtein-java"
[java-cli]: https://github.com/universal-automata/liblevenshtein-java-cli "liblevenshtein-java-cli"
[java-cli-readme]: https://github.com/universal-automata/liblevenshtein-java-cli/blob/master/README.md "liblevenshtein-java-cli, README.md"

[javadoc/Iterable]: https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html?is-external=true "java.lang.Iterable"
[javadoc/Iterator.next()]: https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html#next-- "java.util.Iterator.next()"
[javadoc/Iterator]: https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html "java.util.Iterator"
[javadoc/String]: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html "java.lang.String"

[javadoc/Algorithm.MERGE_AND_SPLIT]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/Algorithm.html#MERGE_AND_SPLIT "Algorithm.MERGE_AND_SPLIT"
[javadoc/Algorithm.STANDARD]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/Algorithm.html#STANDARD "Algorithm.STANDARD"
[javadoc/Algorithm.TRANSPOSITION]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/Algorithm.html#TRANSPOSITION "Algorithm.TRANSPOSITION"
[javadoc/ICandidateCollection]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/ICandidateCollection.html "ICandidateCollection"
[javadoc/ITransducer.transduce(String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/ITransducer.html#transduce-java.lang.String- "ITransducer.transduce(String):ICandidateCollection"
[javadoc/ITransducer.transduce(String,int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/ITransducer.html#transduce-java.lang.String-int- "ITransducer.transduce(String,int):ICandidateCollection"
[javadoc/MemoizedMergeAndSplit.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/distance/MemoizedMergeAndSplit.html "MemoizedMergeAndSplit.between(String,String):int"
[javadoc/MemoizedStandard.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/distance/MemoizedStandard.html "MemoizedStandard.between(String,String):int"
[javadoc/MemoizedTransposition.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/distance/MemoizedTransposition.html "MemoizedTransposition.between(String,String):int"
[javadoc/TransducerBuilder.algorithm(Algorithm)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#algorithm-com.github.liblevenshtein.Algorithm- "TransducerBuilder.algorithm(Algorithm):TransducerBuilder"
[javadoc/TransducerBuilder.build()]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#build-- "TransducerBuilder.build():ITransducer"
[javadoc/TransducerBuilder.defaultMaxDistance(int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#defaultMaxDistance-int- "TransducerBuilder.defaultMaxDistance(int):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#dictionary-java.util.Collection- "TransducerBuilder.dictionary(Collection):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection,boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#dictionary-java.util.Collection-boolean- "TransducerBuilder.dictionary(Collection,boolean):TransducerBuilder"
[javadoc/TransducerBuilder.includeDistance(boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#includeDistance-boolean- "TransducerBuilder.includeDistance(boolean):TransducerBuilder"
[javadoc/TransducerBuilder.maxCandidates(int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/3.0.0-alpha.1/com/github/liblevenshtein/transducer/factory/TransducerBuilder.html#maxCandidates-int- "TransducerBuilder.maxCandidates(int):TransducerBuilder"

[src/Candidate]: https://github.com/universal-automata/liblevenshtein-java/blob/master/src/main/java/com/github/liblevenshtein/transducer/Candidate.java "Candidate.java"
[src/ITransducer]: https://github.com/universal-automata/liblevenshtein-java/blob/3.0.0-alpha.1/src/main/java/com/github/liblevenshtein/transducer/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/TransducerBuilder.java]: https://github.com/universal-automata/liblevenshtein-java/blob/3.0.0-alpha.1/src/main/java/com/github/liblevenshtein/transducer/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/build.gradle]: https://github.com/universal-automata/liblevenshtein-java/blob/3.0.0-alpha.1/build.gradle "build.gradle"

[top-20-most-common-english-words.txt]: https://raw.githubusercontent.com/universal-automata/liblevenshtein-java/3.0.0-alpha.1/src/test/resources/top-20-most-common-english-words.txt "top-20-most-common-english-words.txt"
