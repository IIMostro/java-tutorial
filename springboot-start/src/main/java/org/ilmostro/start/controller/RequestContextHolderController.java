package org.ilmostro.start.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 * @date 2020/8/14 10:45
 */
@RestController
@RequestMapping("")
@Slf4j
public class RequestContextHolderController {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            10, 10000, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    @GetMapping("/thread")
    public void simple(String name) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute("name", name);
        RequestContextHolder.setRequestAttributes(requestAttributes, true);
        log.info("main thread sessionId:{}", request.getRequestedSessionId());

        List<CompletableFuture> futures = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(CompletableFuture.runAsync(RequestContextHolderController::test, executor));
        }

        CompletableFuture[] wait = new CompletableFuture[futures.size()];
        for (int i = 0; i < futures.size(); i++) {
            wait[i] = futures.get(i);
        }
        CompletableFuture.allOf(wait).join();
    }

    public static void test() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        log.info("current thread name:{}, sessionId:{}, attr:{}", Thread.currentThread().getName(), request.getRequestedSessionId(), request.getAttribute("name"));
    }
}
