package org.ilmostro.basic.seg;

import lombok.extern.slf4j.Slf4j;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;

import java.util.List;

/**
 * @author li.bowei
 */
@Slf4j
public class SegTest {

    public static void main(String[] args) {
        Forest forest = DicLibrary.get();
        String message = "中国人民";
        Result parse = ToAnalysis.parse(message);
        List<Term> terms = parse.getTerms();
        for (Term term : terms) {
            log.info("name:{}, nature:{}", term.getName(), term.getNatureStr());
        }
    }
}
