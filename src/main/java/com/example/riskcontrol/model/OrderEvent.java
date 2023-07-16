package com.example.riskcontrol.model;

public class OrderEvent extends Event {

    public final static String ORDERNO = "orderNo";

    //订单号
    private String orderNo;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
