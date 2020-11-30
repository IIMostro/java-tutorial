package org.ilmostro.user.controller;

import org.ilmostro.user.domain.user.User;
import org.ilmostro.user.dto.BasicResponse;
import org.ilmostro.user.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author li.bowei on 2019-10-29.
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public BasicResponse<Page<User>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){
        return new BasicResponse<>(service.queryAll(page, size));
    }

    @PostMapping
    public BasicResponse<User> save(@RequestBody User user){
        return new BasicResponse<>(service.save(user));
    }

    @GetMapping("/{id}")
    public BasicResponse<User> detail(@PathVariable("id") Long id){
        return new BasicResponse<>(service.detail(id));
    }

    @DeleteMapping("/{id}")
    public BasicResponse<User> delete(@PathVariable("id") Long id){
        return new BasicResponse<>(service.delete(id));
    }

}
