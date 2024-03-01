package org.ilmostro.pure.test;

/*
 * 测试aop在factory里面失效的场景
 * 1. @PostConstruct这个生命周期更早，所以往factory里面放的时候是放的原生对象
 * 2. spring aop是通过对bean进行代理来实现的，所以在factory里面放的是原生对象，所以aop失效
 */