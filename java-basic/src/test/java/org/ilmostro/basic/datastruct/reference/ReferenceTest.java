package org.ilmostro.basic.datastruct.reference;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ReferenceTest {

    ReferenceQueue<List<String>> queue = new ReferenceQueue<>();
    SoftReference<List<String>> reference = new SoftReference<>(new LinkedList<>(), queue);


    @Test
    public void test(){
        List<String> data = reference.get();
        if(Objects.isNull(data) || data.isEmpty()){
            data = new LinkedList<>();
            reference = new SoftReference<>(data, queue);
        }
        for(int index = 0; index <10; index++){
            data.add(String.valueOf(index));
        }

        for(String var1: reference.get()){
            log.info("add after data:{}", var1);
        }

        System.gc();

        Reference<? extends List<String>> poll = queue.poll();
        while(Objects.isNull(poll)){
            poll = queue.poll();
            log.info("wait gc");
        }
        List<String> list = poll.get();
        for(String var1: list){
            log.info("gc after data:{}", var1);
        }
    }
}
