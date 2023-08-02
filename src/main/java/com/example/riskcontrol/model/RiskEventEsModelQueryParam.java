package com.example.riskcontrol.model;

import java.io.Serializable;
public class RiskEventEsModelQueryParam implements Serializable {

    private String indexName;

    private String scene;

    /**
     * 操作时间
     */
    private String operateStartTime;

    private String operateEndTime;

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
     * 订单号
     */
    private String orderNo;

    private Integer currentPage;

    private Integer pageSize;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getOperateStartTime() {
        return operateStartTime;
    }

    public void setOperateStartTime(String operateStartTime) {
        this.operateStartTime = operateStartTime;
    }

    public String getOperateEndTime() {
        return operateEndTime;
    }

    public void setOperateEndTime(String operateEndTime) {
        this.operateEndTime = operateEndTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
