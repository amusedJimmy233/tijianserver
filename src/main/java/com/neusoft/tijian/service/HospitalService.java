package com.neusoft.tijian.service;

import com.neusoft.tijian.po.Hospital;

import java.util.List;

public interface HospitalService {
    public List<Hospital> listHospital(Integer state);
    public Hospital getHospitalById(Integer hpId);
}
