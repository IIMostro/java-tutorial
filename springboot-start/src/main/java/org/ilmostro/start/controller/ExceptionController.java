package org.ilmostro.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author li.bowei
 * @date 2020/11/7 15:19
 */
@Controller
public class ExceptionController {

    @GetMapping("/exception")
    @ResponseBody
    public void exception(){
        throw new RuntimeException("hello world");
    }
}
