package org.ilmostro.test.service.autowired;

/**
 * @author li.bowei on 2019-10-12.
 * @description 模拟远程调用的方法
 */
public class SimulationRemoteService {

    //没有参数没有返回值
    public void noParamNoResult(){

    }

    //没有参数有返回值
    public String noParamHasResult(){
        return null;
    }

    //有参数没有返回值
    public void hasParamNoResult(String param){

    }

    //有参数有返回值
    public String hasParamHasResult(String param){
        return param;
    }
}
