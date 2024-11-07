package org.ilmostro.pure.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DynamicRouterRegister {

    private final RequestMappingHandlerMapping mapping;

    @SneakyThrows
    @PostConstruct
    void before(){
        addRoute("/dynamic/hello");
    }

    public void addRoute(String uuid) throws NoSuchMethodException {
        // 定义路由信息
        RequestMappingInfo mappingInfo = RequestMappingInfo
            .paths("/" + uuid)
            .methods(RequestMethod.POST)
            .build();
        // 注册路由
        mapping.registerMapping(mappingInfo, new TestController(), TestController.class.getMethod("test"));
    }

    @ResponseBody
    public static class TestController {

        public String test() {
            return "hello world!";
        }
    }
}
