package org.ilmostro.vertx.verticle;

import org.springframework.stereotype.Service;

@Service
public class SimpleService {

  public String hello(){
    return "hello world";
  }
}
