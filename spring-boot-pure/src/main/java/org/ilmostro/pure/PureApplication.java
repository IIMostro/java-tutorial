package org.ilmostro.pure;

import org.ilmostro.pure.annotation.EnableWebSocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author li.bowei
 */

@SpringBootApplication
//@EnableWebSocket
public class PureApplication {

    public static void main(String[] args) {
        SpringApplication.run(PureApplication.class, args);
    }
}
