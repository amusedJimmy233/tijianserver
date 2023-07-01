package com.neusoft.tijian.mapper;

import com.neusoft.tijian.dto.CalendarResponseDto;
import com.neusoft.tijian.dto.OrdersMapperDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrdersMapper {
    //查询是否预约过(凡是有未归档的预约记录的用户，不能再次预约)
    @Select( "select count(*) from orders where state=1 and userId=#{userId}")
    public int getOrdersByUserId(String userId);

    // 根据parameList参数，查询30天预约日期中，每一天的已预约人数
    public List<CalendarResponseDto> listAppointmentCalendar(List<OrdersMapperDto> list);
}
