package org.ilmostro.disruptor.controller;

import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;
import org.ilmostro.disruptor.entity.GoodsElement;
import org.ilmostro.disruptor.service.ProcessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author li.bowei
 */
@RestController
@RequestMapping("/")
@Slf4j
public class WorkbookController {

    private final Disruptor<GoodsElement> disruptor;
    private final ProcessService service;

    private final LongAdder index = new LongAdder();

    public WorkbookController(Disruptor<GoodsElement> disruptor, ProcessService service) {
        this.disruptor = disruptor;
        this.service = service;
    }

    @GetMapping("/sheet")
    public void create() throws IOException {
//        List<String> data = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        int key = workbook.hashCode();
//        for (int sheet = 0; sheet < 10; sheet++) {
//            disruptor.publishEvent((element, sequence) -> {
//                element.setKey(key);
//                element.setSheet(workbook.createSheet());
//                element.setData(data);
//            });
//        }
//        while (!service.complete(key, 10)) {
//            log.info("wait complete!");
//        }
//        String path = "D:/test" + index.intValue() + ".xlsx";
//        index.increment();
//        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
//        workbook.write(fileOutputStream);
    }
}
