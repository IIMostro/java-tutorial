package org.ilmostro.redis.redisson.structure;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.mapreduce.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.Map;

/**
 * @author li.bowei
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OfficialMapReduceTest {


    @Autowired
    private RedissonClient redisson;

    public static class WordMapper implements RMapper<String, String, String, Integer> {
        @Override
        public void map(String key, String value, RCollector<String, Integer> collector) {
            String[] words = value.split("[^a-zA-Z]");
            for (String word : words) {
                collector.emit(word, 1);
            }
        }
    }

    public static class WordReducer implements RReducer<String, Integer> {
        @Override
        public Integer reduce(String reducedKey, Iterator<Integer> iter) {
            int sum = 0;
            while (iter.hasNext()) {
                Integer i = iter.next();
                sum += i;
            }
            return sum;
        }
    }

    public static class WordCollator implements RCollator<String, Integer, Integer> {
        @Override
        public Integer collate(Map<String, Integer> resultMap) {
            int result = 0;
            for (Integer count : resultMap.values()) {
                result += count;
            }
            return result;
        }
    }

    @Test
    public void test() {
        RMap<String, String> map = redisson.getMap("wordsMap");
        map.put("line1", "Alice was beginning to get very tired");
        map.put("line2", "of sitting by her sister on the bank and");
        map.put("line3", "of having nothing to do once or twice she");
        map.put("line4", "had peeped into the book her sister was reading");
        map.put("line5", "but it had no pictures or conversations in it");
        map.put("line6", "and what is the use of a book");
        map.put("line7", "thought Alice without pictures or conversation");
        RMapReduce<String, String, String, Integer> mapReduce
                = map.<String, Integer>mapReduce()
                .mapper(new WordMapper())
                .reducer(new WordReducer());
        // 统计词频
        Map<String, Integer> mapToNumber = mapReduce.execute();
        // 统计字数
        Integer totalWordsAmount = mapReduce.execute(new WordCollator());

        log.info("map to number:[{}], total words amount:[{}]", mapToNumber, totalWordsAmount);
    }
}
