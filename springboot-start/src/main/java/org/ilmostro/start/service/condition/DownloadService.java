package org.ilmostro.start.service.condition;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author li.bowei
 * @date 2020/9/7 16:17
 */
@Service
public class DownloadService {

    public void download(HttpServletResponse response) throws IOException {
        File file = new File("D:/nb.rar");
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(new FileInputStream(file), outputStream);
    }
}
