package com.example.riskcontrol.model;

import java.io.Serializable;
import java.util.Date;

public class RiskEventEsModel implements Serializable {

    private String scene;

    /**
     * 事件id
     */
    private String id;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 事件评分
     */
    private int score;

    /**
     * 手机号
     */
    private String mobile;

    private String operateIp;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 手机号段
     */
    private String mobileSeg;

    /**
     * 手机归属地和运营商
     */
    private String mobileIpArea;

    /**
     * 操作ip归属地和运营商
     */
    private String operateIpArea;

    /**
     * 订单号
     */
    private String orderNo;
}
