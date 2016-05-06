usage(project, cmd) ::= <<
[Index](index.md) > [Usage](usage.md)

If you've [installed the Maven artifact](installation.md), just use it in your
project as any other dependency.  If you've
[built liblevenshtein from Git](building.md), add it and the other dependencies
listed in the [`build.gradle`][src/build.gradle] file to your Java classpath.

Note that liblevenshtein has been developed against
Java&nbsp;&ge;&nbsp;$project.java.targetVersion$.  It will not work with prior
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
  - The [`ITransducer`][src/ITransducer] interface lets you specify its default,
  maximum edit distance allowable for your current query.
  - If you do not specify a maximum distance, then `2` will be used, which is a
  	sane default.  You may set this to `Integer.MAX_VALUE` if you want all terms
  	in the dictionary to be returned (to validate distances, etc.).
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
4. [`TransducerBuilder.dictionary(dictionary : Collection<String>) : ITransducerBuilder`][javadoc/TransducerBuilder.dictionary(Collection)]
  - Specifies the dictionary of candidates to use with the transducer.
  - If you use this method instead of the latter, then the builder will assume
  the collection is unsorted and will sort it for you (in the current
  implementation).
  - You MUST specify the dictionary from either this method or
  `TransducerBuilder.dictionary(Collection<String>, boolean) : ITransducerBuilder`.
5. [`TransducerBuilder.dictionary(dictionary : Collection<String>, isSorted : boolean) : ITransducerBuilder`][javadoc/TransducerBuilder.dictionary(Collection,boolean)]
  - Specifies the dictionary of candidates to use with the transducer.
  - The second parameter, `isSorted`, specifies whether the collection is
  already sorted.  If it is not then the current implementation will sort it for
  you.
  - You MUST specify the dictionary from either this method or
  `TransducerBuilder.dictionary(Collection<String>) : ITransducerBuilder`.
6. [`<CandidateType> TransducerBuilder.build() : ITransducer<CandidateType>`][javadoc/TransducerBuilder.build()]
  - Builds and returns an instance of `ITransducer` using the parameters defined
  above.
  - If you are including the candidate distances, then the `CandidateType` will
  be [`Candidate`][src/Candidate].  Otherwise, it will be
  [`String`][javadoc/String].

Once you have an instance of [`ITransducer`][src/ITransducer], you may query it
via
[`ITransducer<CandidateType>.transduce(term : String) : Iterable<CandidateType>`][javadoc/ITransducer.transduce(String)]
or
[`ITransducer<CandidateType>.transduce(term : String, maxDistance : int) : Iterable<CandidateType>`][javadoc/ITransducer.transduce(String,int)].
You should use the latter if you want to use a maximum edit distance that is
different from the default
(specified by `TransducerBuilder.defaultMaxDistance(defaultMaxDistance : int) : TransducerBuilder`).
The collection returned is [`Iterable`][javadoc/Iterable], so you probably want
to use a `for-each` loop (you may certainly use the
[`Iterator`][javadoc/Iterator] directly, if that's your cup of tea).

Please not that the transducer may be reused, by repeatedly calling
[`ITransducer.transduce(String)`][javadoc/ITransducer.transduce(String)].  It is
also threadsafe and non-blocking, so you may share the same transducer across
multiple threads.  The [`Iterable`][javadoc/Iterable] is
lazy, so subsequent spelling candidates won't be determined until the next time
you call [`Iterator.next()`][javadoc/Iterator.next()].

Here's a toy example that uses the `TRANSPOSITION` algorithm:

$/wiki/sample(project, cmd)$

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

$footer(project, cmd)$
>>
/* vim: set ft=ghmarkdown: */
