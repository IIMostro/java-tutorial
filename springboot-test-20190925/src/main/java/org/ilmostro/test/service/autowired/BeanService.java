package org.ilmostro.test.service.autowired;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei on 2019-09-25.
 * @description
 */
@Service
@Slf4j
public class BeanService {

    private String name;

    public void set(String str){
        this.name = str;
//        doSomeThing();
        log.info(name);
    }

    public String get(){
        return name;
    }

}
