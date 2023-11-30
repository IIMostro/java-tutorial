package org.ilmostro.basic.datastruct.voyager;

import com.spotify.voyager.jni.Index;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class VoyagerIndexTests {

    @Test
    void test_index_should_work() {
        try(Index index = new Index(Index.SpaceType.Euclidean, 4)){
            index.addItem(new float[]{1.0f, 2.0f, 3.0f, 4.0f});
            index.addItem(new float[]{2.0f, 3.0f, 4.0f, 5.0f});

            Index.QueryResults results = index.query(new float[]{3.0f, 4.0f, 5.0f, 6.0f}, 1);
            log.info("{}", results);
        } catch (Exception ex){
            // ignore
        }
    }
}
