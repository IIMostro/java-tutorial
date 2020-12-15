package org.ilmostro.disruptor.entity;

import lombok.Data;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * @author li.bowei
 */
@Data
public class SheetElement {

    private Integer key;
    private Sheet sheet;
    private List<String> data;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
