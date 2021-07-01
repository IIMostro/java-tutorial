//package org.ilmostro.disruptor.service;
//
//import com.lmax.disruptor.EventHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.ilmostro.disruptor.entity.GoodsElement;
//import org.ilmostro.disruptor.repository.GoodsElementRepository;
//import org.springframework.stereotype.Service;
//
///**
// * @author li.bowei
// */
//@Service
//@Slf4j
//public class DataSourceGoodsService implements EventHandler<GoodsElement> {
//
//    private final GoodsElementRepository repository;
//
//    public DataSourceGoodsService(GoodsElementRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public void onEvent(GoodsElement sheetElement, long sequence, boolean b) throws Exception {
//        log.info("this sequence is :{}, current thread:{}", sequence, Thread.currentThread().getName());
//        repository.save(sheetElement);
////        log.info("this is datasource service! and current thread is:{}", Thread.currentThread().getName());
//    }
//}
