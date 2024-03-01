package org.neptunus.rabbit.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MethodQueueAdapter {

    String queue();
}
