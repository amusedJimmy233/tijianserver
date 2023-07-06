package com.neusoft.tijian.po;

import java.util.List;

public class CiReport {
    private Integer cirId;
    private Integer ciId;
    private String ciName;
    private Integer orderId;
    //一对多
    private List<CiDetailedReport> cidrList;

    public Integer getCirId() {
        return cirId;
    }

    public void setCirId(Integer cirId) {
        this.cirId = cirId;
    }

    public Integer getCiId() {
        return ciId;
    }

    public void setCiId(Integer ciId) {
        this.ciId = ciId;
    }

    public String getCiName() {
        return ciName;
    }

    public void setCiName(String ciName) {
        this.ciName = ciName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<CiDetailedReport> getCidrList() {
        return cidrList;
    }

    public void setCidrList(List<CiDetailedReport> cidrList) {
        this.cidrList = cidrList;
    }
}
