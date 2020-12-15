package org.ilmostro.disruptor.service;

import com.lmax.disruptor.EventHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.ilmostro.disruptor.entity.SheetElement;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author li.bowei
 */
@Service
public class SheetService implements EventHandler<SheetElement> {

    @Override
    public void onEvent(SheetElement sheetElement, long l, boolean b) throws Exception {
        Sheet sheet = sheetElement.getSheet();
        List<String> data = sheetElement.getData();
        for (int rows = 0; rows < 10; rows++) {
            Row row = sheet.createRow(rows);
            for (int column = 0; column < 10; column++) {
                Cell cell = row.createCell(column);
                cell.setCellValue(data.get(column));
            }
        }
    }
}
