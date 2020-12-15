package org.ilmostro.disruptor.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ilmostro.disruptor.entity.ElementEventFactory;
import org.ilmostro.disruptor.entity.SheetElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author li.bowei
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SheetServiceTest {

    @Autowired
    private Disruptor<SheetElement> disruptor;
    @Autowired
    private ProcessService service;

    @Test
    public void handler() throws IOException {
        List<String> data = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
        XSSFWorkbook workbook = new XSSFWorkbook();
        int key = workbook.hashCode();
        for(int sheet = 0; sheet < 10; sheet ++){
            disruptor.publishEvent((element, sequence) -> {
                element.setKey(key);
                element.setSheet(workbook.createSheet());
                element.setData(data);
            });
        }
        while (!service.complete(key, 10)){
            log.info("wait complete!");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:/test.xlsx"));
        workbook.write(fileOutputStream);
    }
}