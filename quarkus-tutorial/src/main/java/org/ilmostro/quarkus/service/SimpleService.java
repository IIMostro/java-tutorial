package org.ilmostro.quarkus.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SimpleService {

    public String say(){
        return "hello world";
    }
}
