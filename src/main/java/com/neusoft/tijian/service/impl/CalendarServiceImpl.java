package com.neusoft.tijian.service.impl;

import com.neusoft.tijian.dto.CalendarResponseDto;
import com.neusoft.tijian.dto.OrdersMapperDto;
import com.neusoft.tijian.mapper.HospitalMapper;
import com.neusoft.tijian.mapper.OrdersMapper;
import com.neusoft.tijian.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Autowired
    private HospitalMapper hospitalMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public List<CalendarResponseDto> listAppointmentCalendar(CalendarResponseDto calendarResponseDto) {
        return null;
    }

    // 获取30天预约日期列表
    private List<CalendarResponseDto> getCalendarList30(Integer hpId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        //做一个临时集合，作为去Orders表中的参数
        List<OrdersMapperDto> parameList = new ArrayList<>();
        for (int i=0;i<30;i++){
            OrdersMapperDto dto = new OrdersMapperDto();
            calendar.add(Calendar.DATE,1);
            dto.setDate(sdf.format(calendar.getTime()));
            dto.setHpId(hpId);
            parameList.add(dto);
        }

        // 根据parameList参数，查询30天预约日期中，每一天的已预约人数
        // 查询结果CalendarResponseDto中只有两个属性ymd，existing，还有两个属性需要填充total，remainder
        List<CalendarResponseDto> calendarList30 = ordersMapper.listAppointmentCalendar(parameList);

        // 根据医院编号，获取预约规则，就能获取每天最多预约人数
        String[] strArrr = hospitalMapper.getHospitalbyId(hpId).getRule().split(",");

        // 继续填充calendarList30返回值中total，remainder属性
        for (CalendarResponseDto cd:calendarList30){
            try{
                calendar.setTime(sdf.parse(cd.getYmd()));
            }catch (ParseException e){
                e.printStackTrace();
            }
            // 通过Calendar对象的get方法获取某天为星期几，就可以通过预约规则数组获取某天的总预约人数
            int total = Integer.parseInt(strArrr[calendar.get(Calendar.DAY_OF_WEEK)-1]);
            // 填充total属性
            cd.setTotal(total);
            // 填充remainder属性
            cd.setRemainder(total-cd.getExisting());
        }
        return calendarList30;
    }
}
