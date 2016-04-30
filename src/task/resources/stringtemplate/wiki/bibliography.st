bibliography(project, cmd) ::= <<
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

[github-author]: https://github.com/$project.meta.username$ "$project.meta.author$ <$project.meta.email$>"
[github-demo]: $project.github.demo$ "liblevenshtein demo"
[github-email]: mailto:$project.meta.email$ "$project.meta.author$ <$project.meta.email$>"
[github-repo]: $project.meta.url$ "$project.github.org$/$project.github.repo$"

[wikipedia-damerau-levenshtein-distance]: https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance "Damerau–Levenshtein distance"
[wikipedia-levenshtein-distance]: https://en.wikipedia.org/wiki/Levenshtein_distance "Levenshtein distance"

[master-branch]: https://github.com/universal-automata/liblevenshtein-java/tree/master
[release-branch]: https://github.com/universal-automata/liblevenshtein-java/tree/release

[wiki]: http://universal-automata.github.io/liblevenshtein-java/docs/wiki/$project.maven.version$/index.md "$project.maven.artifactId$ $project.maven.version$ Wiki"
[javadoc]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/index.html "$project.maven.artifactId$ $project.maven.version$ API"
[tagged-source]: https://github.com/universal-automata/liblevenshtein-java/tree/$project.maven.version$/src "$project.maven.artifactId$ $project.maven.version$"

[java-lib]: https://github.com/universal-automata/liblevenshtein-java "liblevenshtein-java"
[java-cli]: https://github.com/universal-automata/liblevenshtein-java-cli "liblevenshtein-java-cli"
[java-cli-readme]: https://github.com/universal-automata/liblevenshtein-java-cli/blob/master/README.md "liblevenshtein-java-cli, README.md"

[javadoc/Iterable]: https://docs.oracle.com/javase/$project.java.targetVersion$/docs/api/java/lang/Iterable.html?is-external=true "java.lang.Iterable"
[javadoc/Iterator.next()]: https://docs.oracle.com/javase/$project.java.targetVersion$/docs/api/java/util/Iterator.html#next-- "java.util.Iterator.next()"
[javadoc/Iterator]: https://docs.oracle.com/javase/$project.java.targetVersion$/docs/api/java/util/Iterator.html "java.util.Iterator"
[javadoc/String]: https://docs.oracle.com/javase/$project.java.targetVersion$/docs/api/java/lang/String.html "java.lang.String"

[javadoc/Algorithm.MERGE_AND_SPLIT]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#MERGE_AND_SPLIT "Algorithm.MERGE_AND_SPLIT"
[javadoc/Algorithm.STANDARD]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#STANDARD "Algorithm.STANDARD"
[javadoc/Algorithm.TRANSPOSITION]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#TRANSPOSITION "Algorithm.TRANSPOSITION"
[javadoc/ICandidateCollection]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/ICandidateCollection.html "ICandidateCollection"
[javadoc/ITransducer.transduce(String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/ITransducer.html#transduce-java.lang.String- "ITransducer.transduce(String):ICandidateCollection"
[javadoc/ITransducer.transduce(String,int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/ITransducer.html#transduce-java.lang.String-int- "ITransducer.transduce(String,int):ICandidateCollection"
[javadoc/MemoizedMergeAndSplit.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedMergeAndSplit.html "MemoizedMergeAndSplit.between(String,String):int"
[javadoc/MemoizedStandard.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedStandard.html "MemoizedStandard.between(String,String):int"
[javadoc/MemoizedTransposition.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedTransposition.html "MemoizedTransposition.between(String,String):int"
[javadoc/TransducerBuilder.algorithm(Algorithm)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#algorithm-com.github.dylon.liblevenshtein.levenshtein.Algorithm- "TransducerBuilder.algorithm(Algorithm):TransducerBuilder"
[javadoc/TransducerBuilder.build()]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#build-- "TransducerBuilder.build():ITransducer"
[javadoc/TransducerBuilder.defaultMaxDistance(int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#defaultMaxDistance-int- "TransducerBuilder.defaultMaxDistance(int):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#dictionary-java.util.Collection- "TransducerBuilder.dictionary(Collection):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection,boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#dictionary-java.util.Collection-boolean- "TransducerBuilder.dictionary(Collection,boolean):TransducerBuilder"
[javadoc/TransducerBuilder.includeDistance(boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#includeDistance-boolean- "TransducerBuilder.includeDistance(boolean):TransducerBuilder"
[javadoc/TransducerBuilder.maxCandidates(int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/$project.maven.version$/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#maxCandidates-int- "TransducerBuilder.maxCandidates(int):TransducerBuilder"

[src/Candidate]: https://github.com/universal-automata/liblevenshtein-java/blob/master/src/main/java/com/github/dylon/liblevenshtein/levenshtein/Candidate.java "Candidate.java"
[src/ITransducer]: https://github.com/universal-automata/liblevenshtein-java/blob/$project.maven.version$/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/TransducerBuilder.java]: https://github.com/universal-automata/liblevenshtein-java/blob/$project.maven.version$/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/build.gradle]: https://github.com/universal-automata/liblevenshtein-java/blob/$project.maven.version$/build.gradle "build.gradle"
>>
/* vim: set ft=ghmarkdown: */
