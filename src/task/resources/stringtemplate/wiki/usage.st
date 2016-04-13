usage(project) ::= <<
[Home](Home) > [Usage](usage) > Java

If you've [installed the Maven artifact](installation.java), just use it in your project as any other dependency.  If you've [built liblevenshtein from Git](building.java), add it and the other dependencies listed in the [`build.gradle`](https://github.com/universal-automata/liblevenshtein-java/blob/master/build.gradle) file to your Java classpath.

Note that liblevenshtein has been developed against Java >= $project.java.targetVersion$.  It will not work with prior versions.

The easiest way to use the library is through the [`TransducerBuilder`](https://github.com/universal-automata/liblevenshtein-java/blob/master/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.java).  Its primary API is defined as follows:

1. [`TransducerBuilder.algorithm(algorithm : Algorithm) : TransducerBuilder`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#algorithm-com.github.dylon.liblevenshtein.levenshtein.Algorithm-)
  - Specifies whether you want to build a
    [`STANDARD`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#STANDARD), [`TRANSPOSITION`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#TRANSPOSITION), or [`MERGE_AND_SPLIT`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/Algorithm.html#MERGE_AND_SPLIT) automaton.
  - If you do not specify an algorith, the `STANDARD` one will be used.
2. [`TransducerBuilder.defaultMaxDistance(defaultMaxDistance : int) : TransducerBuilder`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#defaultMaxDistance-int-)
  - The [`ITransducer`](https://github.com/universal-automata/liblevenshtein-java/blob/master/src/main/java/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.java) interface lets you specify the maximum edit distance allowable for your current query, and it lets you use a default distance specified by this setter.
  - If you do not specify a maximum distance, then `Integer.MAX_VALUE` will be used, which will return all results from the dictionary.  You probably want to specify a default maximum unless you plan on specifying it every time you perform a query.
3. [`TransducerBuilder.includeDistance(includeDistance : boolean) TransducerBuilder`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#includeDistance-boolean-)
  - If this is `true` then a collection of [`Candidate`](https://github.com/universal-automata/liblevenshtein-java/blob/master/src/main/java/com/github/dylon/liblevenshtein/levenshtein/Candidate.java) objects will be returned when you query the transducer.  These objects contain two getters: `Candidate.term() : String` and `Candidate.distance() : int`.  The former is the spelling candidate (self-explanatory), and the latter is the minimum edit distance of that term from your query term, given the current algorithm.
  - If this is `false` then your queries will just return a collection of `String` objects, which are the spelling candidates.
  - By default, this is `true`.
4. [`TransducerBuilder.maxCandidates(maxCandidates : int) : TransducerBuilder`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#maxCandidates-int-)
  - NOTE: This option has been deprecated, and will be removed in the next, major version.  Candidate collections are now lazy, so this method is no longer needed.
  - If you want to limit the number of candidates returned, then you may specify this parameter.  No more candidates then what you specify will be returned from any given query.  Keep in mind that at the moment, there is no ordering on the candidates returned so you are not guaranteed to get the nearest candidates, first.  I'm working on an admissible heuristic that will let me return the candidates in an ordered fashion, customizable upon construction.
  - By default this is set to `Integer.MAX_VALUE`.
5. [`TransducerBuilder.dictionary(dictionary : Collection<String>) : ITransducerBuilder`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#dictionary-java.util.Collection-)
  - Specifies the dictionary of candidates to use with the transducer.
  - If you use this method instead of the latter, then the builder will assume the collection is unsorted and will sort it for you (in the current implementation).
  - You MUST specify the dictionary from either this method or `TransducerBuilder.dictionary(Collection<String>, boolean) : ITransducerBuilder`.
6. [`TransducerBuilder.dictionary(dictionary : Collection<String>, isSorted : boolean) : ITransducerBuilder`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#dictionary-java.util.Collection-boolean-)
  - Specifies the dictionary of candidates to use with the transducer.
  - The second parameter, `isSorted`, specifies whether the collection is already sorted.  If it is not then the current implementation will sort it for you.
  - You MUST specify the dictionary from either this method or `TransducerBuilder.dictionary(Collection<String>) : ITransducerBuilder`.
7. [`<CandidateType> TransducerBuilder.build() : ITransducer<CandidateType>`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/factory/TransducerBuilder.html#build--)
  - Builds and returns an instance of `ITransducer` using the parameters defined above.
  - If you are including the candidate distances, then the `CandidateType` will be [`Candidate`](https://github.com/universal-automata/liblevenshtein-java/blob/master/src/main/java/com/github/dylon/liblevenshtein/levenshtein/Candidate.java).  Otherwise, it will be [`String`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html).

Once you have an instance of [`ITransducer`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/ITransducer.html), you may query it via [`ITransducer<CandidateType>.transduce(term : String) : ICandiateCollection<CandidateType>`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/ITransducer.html#transduce-java.lang.String-) or [`ITransducer<CandidateType>.transduce(term : String, maxDistance : int) : ICandiateCollection<CandidateType>`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/ITransducer.html#transduce-java.lang.String-int-).  You should use the latter if you want to use a maximum edit distance that is different from the default (specified by `TransducerBuilder.defaultMaxDistance(defaultMaxDistance : int) : TransducerBuilder`).  The collection returned is [`Iterable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html?is-external=true), so you probably want to use a `for-each` loop (you may certainly use the [`Iterator`](https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html) directly, if that's your cup of tea).

Please not that the transducer may be reused, by repeatedly calling [`ITransducer.transduce(String)`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/ITransducer.html#transduce-java.lang.String-).  It is also threadsafe and non-blocking, so you may share the same transducer across multiple threads.  The [`ICandidateCollection`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/ICandidateCollection.html) is lazy, so subsequent spelling candidates won't be determined until the next time you call [`Iterator.next()`](https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html#next--).

Here's a toy example that uses the `TRANSPOSITION` algorithm:

```java
package pkg.of.awesomeness;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.Candidate;
import com.github.dylon.liblevenshtein.levenshtein.ITransducer;
import com.github.dylon.liblevenshtein.levenshtein.factory.TransducerBuilder;

public class GetSpellingCandidates {

  public static void main(final String... args) throws IOException {
    int argsIdx = 0;
    final String dictionaryPath = args[argsIdx ++];
    final int maxDistance = Integer.parseInt(args[argsIdx ++]);

    final Collection<String> dictionary = buildDictionary(dictionaryPath);

    final ITransducer<Candidate> transducer = new TransducerBuilder()
      .algorithm(Algorithm.TRANSPOSITION)
      .defaultMaxDistance(maxDistance)
      .dictionary(dictionary)
      .build();

    for (int i = argsIdx; i < args.length; ++i) {
      final String queryTerm = args[i];
      for (final Candidate candidate : transducer.transduce(queryTerm)) {
        final String candidateTerm = candidate.term();
        final int distance = candidate.distance();
        System.out.printf("d(%s, %s) = %d%n", queryTerm, candidateTerm, distance);
      }
    }
  }

  private static Collection<String> buildDictionary(final String dictionaryPath)
      throws IOException {

    try (final Reader dictionaryReader = new FileReader(dictionaryPath);
         final BufferedReader lineReader = new BufferedReader(dictionaryReader)) {

      final List<String> dictionary = new ArrayList<>();

      String line;
      while (null != (line = lineReader.readLine())) {
        dictionary.add(line);
      }

      return dictionary;
    }
  }
}
```

```sh
# Calls GetSpellingCandidates with the newline-delimited, dictionary of terms, a
# maximum distance of 2, and the following terms to find spelling candidates
# for: foo; bar; baz
java pkg.of.awesomeness.GetSpellingCandidates /path/to/dictionary.txt 2 foo bar baz
```

### Traditional Levenshtein Metrics

There are some traditional metrics, that measure the Levenshtein distances between two terms.  They are included mainly for validating the correctness of the Levenshtein automata, but there may be other uses for them.  The implementations memoize distances between terms to make them more performant, so keep in mind that their memory usage may grow with repeated invocation.

- [`MemoizedStandard.between(String,String):int`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedStandard.html)
- [`MemoizedTransposition.between(String,String):int`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedTransposition.html)
- [`MemoizedMergeAndSplit.between(String,String):int`](http://universal-automata.github.io/liblevenshtein/docs/javadoc/com/github/dylon/liblevenshtein/levenshtein/distance/MemoizedMergeAndSplit.html)

The algorithms are the same as those described above, but the metrics measure the distance between two, and exactly two terms.

```java
final IDistance<String> distance = new MemoizedTransposition();
System.out.println(distance.between("foo", "ofo")); //-> 1
```

[liblevenshtein](https://github.com/universal-automata/liblevenshtein) is maintained by [@dylon](https://github.com/dylon)
>>
/* vim: set ft=ghmarkdown: */
