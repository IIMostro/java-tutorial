package org.ilmostro.basic.jsoup;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author li.bowei
 */
@Slf4j
public class Start {

    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream(new File("/Users/li.bowei/Documents/imported/big_excel_6.xls"));
        Document parse = Jsoup.parse(is, "GBK", "");
        Elements worksheet = parse.getElementsByTag("Worksheet");
        for (Element element : worksheet) {
            Elements rows = element.getElementsByTag("Row");
            for (Element row : rows) {
                Elements cells = row.getElementsByTag("Cell");
                for (Element cell : cells) {
                    Elements data = cell.getElementsByTag("Data");
                    log.info("cell index:{}, data:{}", cell.attr("ss:Index"), data.text());
                }
            }
        }
    }
}
