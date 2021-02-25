package org.ilmostro.test.service.statemachine;

/**
 * @author li.bowei
 **/
public enum PayStatus {

    //待支付
    UNPAID,
    //订单关闭
    CLOSE,
    //已支付
    PAID,
    //退款
    REFUND,
}
