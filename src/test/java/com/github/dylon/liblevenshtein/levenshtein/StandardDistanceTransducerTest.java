package com.github.dylon.liblevenshtein.levenshtein;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.val;

import com.github.dylon.liblevenshtein.levenshtein.distance.MemoizedStandard;
import com.github.dylon.liblevenshtein.levenshtein.factory.TransducerBuilder;
import com.github.dylon.liblevenshtein.serialization.BytecodeSerializer;
import com.github.dylon.liblevenshtein.serialization.ProtobufSerializer;
import com.github.dylon.liblevenshtein.serialization.Serializer;
import static com.github.dylon.liblevenshtein.assertion.CandidateAssertions.assertThat;
import static com.github.dylon.liblevenshtein.assertion.SetAssertions.assertThat;

@SuppressWarnings("unchecked")
public class StandardDistanceTransducerTest extends AbstractTransducerTest {
  private static final int MAX_DISTANCE = 3;
  private static final String QUERY_TERM = "Jvaa";

  private ITransducer<Candidate> transducer;
  private Set<Candidate> expectedCandidates;

  @BeforeTest
  public void setUp() throws IOException {
    try (final InputStream istream =
        getClass().getResourceAsStream("/programming-languages.txt")) {

      final Collection<String> dictionary = readLines(istream);

      this.transducer = new TransducerBuilder()
        .defaultMaxDistance(MAX_DISTANCE)
        .dictionary(dictionary, true)
        .build();
    }

    this.expectedCandidates = new HashSet<>();
    expectedCandidates.add(new Candidate("Java", 2));
    expectedCandidates.add(new Candidate("Ada", 3));
    expectedCandidates.add(new Candidate("Agda", 3));
    expectedCandidates.add(new Candidate("Cola", 3));
    expectedCandidates.add(new Candidate("J", 3));
    expectedCandidates.add(new Candidate("J#", 3));
    expectedCandidates.add(new Candidate("J++", 3));
    expectedCandidates.add(new Candidate("JADE", 3));
    expectedCandidates.add(new Candidate("Jako", 3));
    expectedCandidates.add(new Candidate("JAL", 3));
    expectedCandidates.add(new Candidate("JASS", 3));
    expectedCandidates.add(new Candidate("JCL", 3));
    expectedCandidates.add(new Candidate("JEAN", 3));
    expectedCandidates.add(new Candidate("JOSS", 3));
    expectedCandidates.add(new Candidate("Joy", 3));
    expectedCandidates.add(new Candidate("Julia", 3));
    expectedCandidates.add(new Candidate("Lava", 3));
    expectedCandidates.add(new Candidate("Leda", 3));
    expectedCandidates.add(new Candidate("Lua", 3));
    expectedCandidates.add(new Candidate("Max", 3));
    expectedCandidates.add(new Candidate("Maya", 3));
    expectedCandidates.add(new Candidate("Mesa", 3));
    expectedCandidates.add(new Candidate("Nial", 3));
    expectedCandidates.add(new Candidate("Oak", 3));
    expectedCandidates.add(new Candidate("Opa", 3));
    expectedCandidates.add(new Candidate("Opal", 3));
    expectedCandidates.add(new Candidate("Reia", 3));
    expectedCandidates.add(new Candidate("Rlab", 3));
    expectedCandidates.add(new Candidate("Scala", 3));
    expectedCandidates.add(new Candidate("Span", 3));
    expectedCandidates.add(new Candidate("Stata", 3));
    expectedCandidates.add(new Candidate("Tea", 3));
    expectedCandidates.add(new Candidate("Trac", 3));
    expectedCandidates.add(new Candidate("Vala", 3));
    expectedCandidates.add(new Candidate("Vvvv", 3));
  }

  @DataProvider(name = "serializers")
  public Iterator<Object[]> serializers() {
    final List<Object[]> serializers = new LinkedList<>();
    serializers.add(new Object[] {new BytecodeSerializer()});
    serializers.add(new Object[] {new ProtobufSerializer()});
    return serializers.iterator();
  }

  @Test(dataProvider = "serializers")
  public void testSerialization(final Serializer serializer) throws Exception {
    final byte[] bytes = serializer.serialize(transducer);
    final ITransducer<Candidate> actualTransducer =
      (ITransducer<Candidate>)
        serializer.deserialize(Transducer.class, bytes);
    assertThat(actualTransducer).isEqualTo(transducer);
  }

  @Test
  public void testTransduce() {
    final ICandidateCollection<Candidate> actualCandidates = transducer.transduce(QUERY_TERM);
    final Iterator<Candidate> actualIter = actualCandidates.iterator();

    val distance = new MemoizedStandard();

    while (actualIter.hasNext()) {
      final Candidate actualCandidate = actualIter.next();
      assertThat(actualCandidate).hasDistance(distance, QUERY_TERM);
      assertThat(expectedCandidates).contains(actualCandidate);
      expectedCandidates.remove(actualCandidate);
    }

    assertThat(expectedCandidates).isEmpty();
  }
}
