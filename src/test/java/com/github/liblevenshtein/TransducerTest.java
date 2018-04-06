package com.github.liblevenshtein;

import com.github.liblevenshtein.collection.dictionary.SortedDawg;
import com.github.liblevenshtein.transducer.Algorithm;
import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.github.liblevenshtein.transducer.factory.TransducerBuilder;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.testng.Assert.assertTrue;

public class TransducerTest {

    private static final String[] DICT1 = new String[]{
            "1","2","a","ab","abc","abcd","abcde",
            "b","bc","bcd","bcde","bcdef","c","co",
            "cou","coun","count","countr","country",
            "m","ma","mar","mark","marke","market",
            "s","so","som","some"};

    @Test
    public void testTransducer_Search_Failing() {
        ITransducer<?> transducer = createTransducer(Arrays.asList(DICT1));

        for (String term : DICT1) {
            Stream<?> candidates = transduce(transducer, term);
            assertTrue(candidates.count() > 0, "No candidates for term '" + term + "'");
        }
    }

    @Test
    public void testTransducer_Search_OK() {
        String[] DICT2 = new String[DICT1.length + 1];
        System.arraycopy(DICT1, 0, DICT2, 0, DICT1.length);
        DICT2[DICT2.length - 1] = "z";

        ITransducer<?> transducer = createTransducer(Arrays.asList(DICT2));

        for (String term : DICT2) {
            Stream<?> candidates = transduce(transducer, term);
            assertTrue(candidates.count() > 0, "No candidates for term '" + term + "'");
        }
    }

    private ITransducer<Candidate> createTransducer(Collection<String> terms) {
        SortedDawg dict = new SortedDawg();
        List<String> list = new ArrayList<>(terms);
        Collections.sort(list);
        dict.addAll(list);
        return new TransducerBuilder()
                .dictionary(dict, true)
                .build();
    }

    private <C> Stream<C> transduce(ITransducer<C> transducer, String term) {
        return StreamSupport.stream(transducer.transduce(term).spliterator(), false);
    }
}
