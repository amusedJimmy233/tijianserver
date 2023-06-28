package com.neusoft.tijian.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrdersMapper {
    //查询是否预约过(凡是有未归档的预约记录的用户，不能再次预约)
    @Select( "select count(*) from orders where state=1 and userId=#{userId}")
    public int getOrdersByUserId(String userId);
}
