package com.github.dylon.liblevenshtein.levenshtein;

import com.github.dylon.liblevenshtein.levenshtein.factory.TransducerBuilder;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class CollisionInFinalNodesTest {

    @Test
    public void testZeroDistance() {
        List<String> mockDictionary = new ArrayList<String>();

        mockDictionary.add("Representatives");
        mockDictionary.add("Resource");
        mockDictionary.add("Resources");

        final ITransducer<Candidate> transducer = new TransducerBuilder()
                .algorithm(Algorithm.TRANSPOSITION)
                .defaultMaxDistance(2)
                .dictionary(mockDictionary)
                .build();

        for(String query: mockDictionary) {

            boolean exactMatchFound = false;

            for(Candidate candidate : transducer.transduce(query)) {
                if(candidate.distance() == 0) {
                    exactMatchFound = true;
                    break;
                }
            }

            assertTrue(exactMatchFound);
        }
    }
}
