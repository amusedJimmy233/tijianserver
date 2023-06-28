package com.neusoft.tijian.mapper;

import com.neusoft.tijian.po.Hospital;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HospitalMapper {

    //查询正常营业的医院
    @Select("select * from hospital where state=#{state} order by hpId" )
    public List<Hospital> listHospital(Integer state);
}
