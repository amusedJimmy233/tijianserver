package com.neusoft.tijian.mapper;

import com.neusoft.tijian.po.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    //查询体检套餐列表(包含检查项信息)
    public List<Setmeal> listSetmealByType(Integer type);
    //根据主键查询体检套餐
    @Select( "select * from setmeal where smId=#{smId}")
    public Setmeal getSetmealById(Integer smId);

}
