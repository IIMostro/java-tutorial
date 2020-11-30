package org.ilmostro.stream.controller;

import org.ilmostro.stream.servie.LogService;
import org.ilmostro.stream.servie.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.bowei
 * @date 2020/11/30 14:17
 */
@RestController
@RequestMapping("/")
public class MessageController {

    private final LogService logService;
    private final UserService userService;

    public MessageController(LogService logService, UserService userService) {
        this.logService = logService;
        this.userService = userService;
    }

    @GetMapping("/send")
    public String send(String msg){
        logService.send(msg);
        userService.send(msg);
        return "OK";
    }
}
