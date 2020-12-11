package org.ilmostro.start.service.condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/9/30 13:39
 */
@Service
public class Circulation2 {

    @Autowired
    private Circulation1 circulation;

    public void say(){
        circulation.say();
        System.out.println("circulation 2 say");
    }
}
