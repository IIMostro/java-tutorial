package org.ilmostro.start.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author li.bowei
 * @date 2020/10/29 16:52
 */
@Service
public class PostService {

    private SimpleService service;

    public void sey() {
        service.say();
    }

    @Autowired
    private void setService(SimpleService service) {
        this.service = service;
    }
}
