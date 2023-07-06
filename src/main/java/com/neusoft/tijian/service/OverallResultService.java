package com.neusoft.tijian.service;

import com.neusoft.tijian.po.OverallResult;

import java.util.List;

public interface OverallResultService {
    public List<OverallResult> listOverallResultByOrderId(Integer orderId);
}
