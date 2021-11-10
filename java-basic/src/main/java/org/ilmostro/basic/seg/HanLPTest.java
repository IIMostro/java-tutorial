package org.ilmostro.basic.seg;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author li.bowei
 */
@Slf4j
public class HanLPTest {

    public static void main(String[] args) {
        String message = "中国人民";
        List<Term> segment = StandardTokenizer.segment(message);
        for (Term term : segment) {
            log.info("name:{}, nature:{}", term.word, term.nature);
        }
    }
}
