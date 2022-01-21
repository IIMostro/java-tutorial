package org.ilmostro.quarkus.service;

import org.ilmostro.quarkus.annotation.Logging;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SimpleService {

    @Logging
    public String say(){
        return "hello world";
    }
}
