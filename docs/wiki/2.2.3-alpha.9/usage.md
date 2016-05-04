[Index](index.md) > [Usage](usage.md)

If you've [installed the Maven artifact](installation.md), just use it in your
project as any other dependency.  If you've
[built liblevenshtein from Git](building.md), add it and the other dependencies
listed in the [`build.gradle`][src/build.gradle] file to your Java classpath.

Note that liblevenshtein has been developed against
Java&nbsp;&ge;&nbsp;1.8.  It will not work with prior
versions.

The easiest way to use the library is through the
[`TransducerBuilder`][src/TransducerBuilder.java].  Its primary API is defined
as follows:

1. [`TransducerBuilder.algorithm(algorithm : Algorithm) : TransducerBuilder`][javadoc/TransducerBuilder.algorithm(Algorithm)]
  - Specifies whether you want to build a
  [`STANDARD`][javadoc/Algorithm.TRANSPOSITION],
  [`TRANSPOSITION`][javadoc/Algorithm.TRANSPOSITION],or
  [`MERGE_AND_SPLIT`][javadoc/Algorithm.MERGE_AND_SPLIT] automaton.
  - If you do not specify an algorith, the
  [`STANDARD`][javadoc/Algorithm.TRANSPOSITION] one will be used.
2. [`TransducerBuilder.defaultMaxDistance(defaultMaxDistance : int) : TransducerBuilder`][javadoc/TransducerBuilder.defaultMaxDistance(int)]
  - The [`ITransducer`][src/ITransducer] interface lets you specify the maximum
  edit distance allowable for your current query, and it lets you use a default
  distance specified by this setter.
  - If you do not specify a maximum distance, then `Integer.MAX_VALUE` will be
  used, which will return all results from the dictionary.  You probably want to
  specify a default maximum unless you plan on specifying it every time you
  perform a query.
3. [`TransducerBuilder.includeDistance(includeDistance : boolean) TransducerBuilder`][javadoc/TransducerBuilder.includeDistance(boolean)]
  - If this is `true` then a collection of [`Candidate`][src/Candidate] objects
  will be returned when you query the transducer.  These objects contain two
  getters: `Candidate.term() : String` and `Candidate.distance() : int`.
  The former is the spelling candidate (self-explanatory), and the latter is
  the minimum edit distance of that term from your query term, given the current
  algorithm.
  - If this is `false` then your queries will just return a collection of
  `String` objects, which are the spelling candidates.
  - By default, this is `true`.
4. [`TransducerBuilder.maxCandidates(maxCandidates : int) : TransducerBuilder`][javadoc/TransducerBuilder.maxCandidates(int)]
  - NOTE: This option has been deprecated, and will be removed in the next,
  major version.  Candidate collections are now lazy, so this method is no
  longer needed.
  - If you want to limit the number of candidates returned, then you may specify
  this parameter.  No more candidates then what you specify will be returned
  from any given query.  Keep in mind that at the moment, there is no ordering
  on the candidates returned so you are not guaranteed to get the nearest
  candidates, first.  I'm working on an admissible heuristic that will let me
  return the candidates in an ordered fashion, customizable upon construction.
  - By default this is set to `Integer.MAX_VALUE`.
5. [`TransducerBuilder.dictionary(dictionary : Collection<String>) : ITransducerBuilder`][javadoc/TransducerBuilder.dictionary(Collection)]
  - Specifies the dictionary of candidates to use with the transducer.
  - If you use this method instead of the latter, then the builder will assume
  the collection is unsorted and will sort it for you (in the current
  implementation).
  - You MUST specify the dictionary from either this method or
  `TransducerBuilder.dictionary(Collection<String>, boolean) : ITransducerBuilder`.
6. [`TransducerBuilder.dictionary(dictionary : Collection<String>, isSorted : boolean) : ITransducerBuilder`][javadoc/TransducerBuilder.dictionary(Collection,boolean)]
  - Specifies the dictionary of candidates to use with the transducer.
  - The second parameter, `isSorted`, specifies whether the collection is
  already sorted.  If it is not then the current implementation will sort it for
  you.
  - You MUST specify the dictionary from either this method or
  `TransducerBuilder.dictionary(Collection<String>) : ITransducerBuilder`.
7. [`<CandidateType> TransducerBuilder.build() : ITransducer<CandidateType>`][javadoc/TransducerBuilder.build()]
  - Builds and returns an instance of `ITransducer` using the parameters defined
  above.
  - If you are including the candidate distances, then the `CandidateType` will
  be [`Candidate`][src/Candidate].  Otherwise, it will be
  [`String`][javadoc/String].

Once you have an instance of [`ITransducer`][src/ITransducer], you may query it
via
[`ITransducer<CandidateType>.transduce(term : String) : ICandidateCollection<CandidateType>`][javadoc/ITransducer.transduce(String)]
or
[`ITransducer<CandidateType>.transduce(term : String, maxDistance : int) : ICandiateCollection<CandidateType>`][javadoc/ITransducer.transduce(String,int)].
You should use the latter if you want to use a maximum edit distance that is
different from the default
(specified by `TransducerBuilder.defaultMaxDistance(defaultMaxDistance : int) : TransducerBuilder`).
The collection returned is [`Iterable`][javadoc/Iterable], so you probably want
to use a `for-each` loop (you may certainly use the
[`Iterator`][javadoc/Iterator] directly, if that's your cup of tea).

Please not that the transducer may be reused, by repeatedly calling
[`ITransducer.transduce(String)`][javadoc/ITransducer.transduce(String)].  It is
also threadsafe and non-blocking, so you may share the same transducer across
multiple threads.  The [`ICandidateCollection`][javadoc/ICandidateCollection] is
lazy, so subsequent spelling candidates won't be determined until the next time
you call [`Iterator.next()`][javadoc/Iterator.next()].

Here's a toy example that uses the `TRANSPOSITION` algorithm:

Let's say you have the following content in a plain text file called,
[top-20-most-common-english-words.txt][top-20-most-common-english-words.txt]
(note that the file has one term per line):

```
the
be
to
of
and
a
in
that
have
I
it
for
not
on
with
he
as
you
do
at
```

The following provides you a way to query its content:

```java
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.dylon.liblevenshtein.collection.dawg.SortedDawg;
import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.Candidate;
import com.github.dylon.liblevenshtein.levenshtein.ITransducer;
import com.github.dylon.liblevenshtein.levenshtein.factory.TransducerBuilder;
import com.github.dylon.liblevenshtein.serialization.PlainTextSerializer;
import com.github.dylon.liblevenshtein.serialization.ProtobufSerializer;
import com.github.dylon.liblevenshtein.serialization.Serializer;

// ...

final SortedDawg dictionary;
final Path dictionaryPath =
  Paths.get("/path/to/top-20-most-common-english-words.txt");
try (final InputStream stream = Files.newInputStream(dictionaryPath)) {
  // The PlainTextSerializer constructor accepts an optional boolean specifying
  // whether the dictionary is already sorted lexicographically, in ascending
  // order.  If it is sorted, then passing true will optimize the construction
  // of the dictionary; you may pass false whether the dictionary is sorted or
  // not (this is the default and safest behavior if you don't know whether the
  // dictionary is sorted).
  final Serializer serializer = new PlainTextSerializer(false);
  dictionary = serializer.deserialize(SortedDawg.class, stream);
}

final ITransducer<Candidate> transducer = new TransducerBuilder()
  .dictionary(dictionary)
  .algorithm(Algorithm.TRANSPOSITION)
  .defaultMaxDistance(2)
  .includeDistance(true)
  .build();

for (final String queryTerm : new String[] {"foo", "bar"}) {
  System.out.println(
    "+-------------------------------------------------------------------------------");
  System.out.printf("| Spelling Candidates for Query Term: \"%s\"%n", queryTerm);
  System.out.println(
    "+-------------------------------------------------------------------------------");
  for (final Candidate candidate : transducer.transduce(queryTerm)) {
    System.out.printf("| d(\"%s\", \"%s\") = [%d]%n",
      queryTerm,
      candidate.term(),
      candidate.distance());
  }
}

// +-------------------------------------------------------------------------------
// | Spelling Candidates for Query Term: "foo"
// +-------------------------------------------------------------------------------
// | d("foo", "do") = [2]
// | d("foo", "of") = [2]
// | d("foo", "on") = [2]
// | d("foo", "to") = [2]
// | d("foo", "for") = [1]
// | d("foo", "not") = [2]
// | d("foo", "you") = [2]
// +-------------------------------------------------------------------------------
// | Spelling Candidates for Query Term: "bar"
// +-------------------------------------------------------------------------------
// | d("bar", "a") = [2]
// | d("bar", "as") = [2]
// | d("bar", "at") = [2]
// | d("bar", "be") = [2]
// | d("bar", "for") = [2]

// ...
```

If you want to serialize your dictionary to a format that's easy to read later,
do the following:

```java
final Path serializedDictionaryPath =
  Paths.get("/path/to/top-20-most-common-english-words.protobuf.bytes");
try (final OutputStream stream = Files.newOutputStream(serializedDictionaryPath)) {
  final Serializer serializer = new ProtobufSerializer();
  serializer.serialize(dictionary, stream);
}
```

Then, you can read the dictionary later, in much the same way you read the plain
text version:

```java
final SortedDawg deserializedDictionary;
try (final InputStream stream = Files.newInputStream(serializedDictionaryPath)) {
  final Serializer serializer = new ProtobufSerializer();
  deserializedDictionary = serializer.deserialize(SortedDawg.class, stream);
}
```

Serialization is not restricted to dictionaries, you may also (de)serialize
transducers.

### Traditional Levenshtein Metrics

There are some traditional metrics, that measure the Levenshtein distances
between two terms.  They are included mainly for validating the correctness of
the Levenshtein automata, but there may be other uses for them.  The
implementations memoize distances between terms to make them more performant, so
keep in mind that their memory usage may grow with repeated invocation.

- [`MemoizedStandard.between(String,String):int`][javadoc/MemoizedStandard.between(String,String)]
- [`MemoizedTransposition.between(String,String):int`][javadoc/MemoizedTransposition.between(String,String)]
- [`MemoizedMergeAndSplit.between(String,String):int`][javadoc/MemoizedMergeAndSplit.between(String,String)]

The algorithms are the same as those described above, but the metrics measure
the distance between two, and exactly two terms.

```java
final IDistance<String> distance = new MemoizedTransposition();
System.out.println(distance.between("foo", "ofo")); //-> 1
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

[wiki]: http://universal-automata.github.io/liblevenshtein-java/docs/wiki/2.2.3-alpha.9/index.md "liblevenshtein 2.2.3-alpha.9 Wiki"
[javadoc]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/index.html "liblevenshtein 2.2.3-alpha.9 API"
[tagged-source]: https://github.com/universal-automata/liblevenshtein-java/tree/2.2.3-alpha.9/src "liblevenshtein 2.2.3-alpha.9"

[java-lib]: https://github.com/universal-automata/liblevenshtein-java "liblevenshtein-java"
[java-cli]: https://github.com/universal-automata/liblevenshtein-java-cli "liblevenshtein-java-cli"
[java-cli-readme]: https://github.com/universal-automata/liblevenshtein-java-cli/blob/master/README.md "liblevenshtein-java-cli, README.md"

[javadoc/Iterable]: https://docs.oracle.com/javase/1.8/docs/api/java/lang/Iterable.html?is-external=true "java.lang.Iterable"
[javadoc/Iterator.next()]: https://docs.oracle.com/javase/1.8/docs/api/java/util/Iterator.html#next-- "java.util.Iterator.next()"
[javadoc/Iterator]: https://docs.oracle.com/javase/1.8/docs/api/java/util/Iterator.html "java.util.Iterator"
[javadoc/String]: https://docs.oracle.com/javase/1.8/docs/api/java/lang/String.html "java.lang.String"

[javadoc/Algorithm.MERGE_AND_SPLIT]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#MERGE_AND_SPLIT "Algorithm.MERGE_AND_SPLIT"
[javadoc/Algorithm.STANDARD]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#STANDARD "Algorithm.STANDARD"
[javadoc/Algorithm.TRANSPOSITION]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#TRANSPOSITION "Algorithm.TRANSPOSITION"
[javadoc/ICandidateCollection]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/ICandidateCollection.html "ICandidateCollection"
[javadoc/ITransducer.transduce(String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/ITransducer.html#transduce-java.lang.String- "ITransducer.transduce(String):ICandidateCollection"
[javadoc/ITransducer.transduce(String,int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/ITransducer.html#transduce-java.lang.String-int- "ITransducer.transduce(String,int):ICandidateCollection"
[javadoc/MemoizedMergeAndSplit.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedMergeAndSplit.html "MemoizedMergeAndSplit.between(String,String):int"
[javadoc/MemoizedStandard.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedStandard.html "MemoizedStandard.between(String,String):int"
[javadoc/MemoizedTransposition.between(String,String)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedTransposition.html "MemoizedTransposition.between(String,String):int"
[javadoc/TransducerBuilder.algorithm(Algorithm)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#algorithm-com.github.dylon.liblevenshtein.levenshtein.Algorithm- "TransducerBuilder.algorithm(Algorithm):TransducerBuilder"
[javadoc/TransducerBuilder.build()]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#build-- "TransducerBuilder.build():ITransducer"
[javadoc/TransducerBuilder.defaultMaxDistance(int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#defaultMaxDistance-int- "TransducerBuilder.defaultMaxDistance(int):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#dictionary-java.util.Collection- "TransducerBuilder.dictionary(Collection):TransducerBuilder"
[javadoc/TransducerBuilder.dictionary(Collection,boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#dictionary-java.util.Collection-boolean- "TransducerBuilder.dictionary(Collection,boolean):TransducerBuilder"
[javadoc/TransducerBuilder.includeDistance(boolean)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#includeDistance-boolean- "TransducerBuilder.includeDistance(boolean):TransducerBuilder"
[javadoc/TransducerBuilder.maxCandidates(int)]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc/2.2.3-alpha.9/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#maxCandidates-int- "TransducerBuilder.maxCandidates(int):TransducerBuilder"

[src/Candidate]: https://github.com/universal-automata/liblevenshtein-java/blob/master/src/main/java/com/github/dylon/liblevenshtein/levenshtein/Candidate.java "Candidate.java"
[src/ITransducer]: https://github.com/universal-automata/liblevenshtein-java/blob/2.2.3-alpha.9/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/TransducerBuilder.java]: https://github.com/universal-automata/liblevenshtein-java/blob/2.2.3-alpha.9/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.java "TransducerBuilder.java"
[src/build.gradle]: https://github.com/universal-automata/liblevenshtein-java/blob/2.2.3-alpha.9/build.gradle "build.gradle"

[top-20-most-common-english-words.txt]: https://raw.githubusercontent.com/universal-automata/liblevenshtein-java/2.2.3-alpha.9/src/test/resources/top-20-most-common-english-words.txt "top-20-most-common-english-words.txt"
