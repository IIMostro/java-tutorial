package org.ilmostro.start.controller;

import org.ilmostro.start.service.condition.DownloadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author li.bowei
 * @date 2020/9/7 16:19
 */
@RestController
public class DownloadController {

    private final DownloadService service;

    public DownloadController(DownloadService service) {
        this.service = service;
    }

    @PostMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        service.download(response);
    }
}
