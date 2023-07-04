package com.neusoft.tijian.service.impl;

import com.neusoft.tijian.dto.CalendarRequestDto;
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

    // 生成预约日历
    @Override
    public List<CalendarResponseDto> listAppointmentCalendar(CalendarRequestDto dto) {
        // 获取预约日期列表
        List<CalendarResponseDto> calendarList30 = getCalendarList30(dto.getHpId());
        // 获取当前日历（当前日历中只有ymd属性，total、existing、remainder需要从calendarList30获取)
        List<CalendarResponseDto> currentCalendarList = getCurrentCalendarList(dto.getYear(),dto.getMonth());
        //给当前日历填充total、existing.remainder属性
        for(CalendarResponseDto current : currentCalendarList) {
            for( CalendarResponseDto calendar : calendarList30) {
                if( current.getYmd() != null) {
                    if (current.getYmd().equals(calendar.getYmd())) {
                        current.setTotal(calendar.getTotal());
                        current.setExisting(calendar.getExisting());
                        current.setRemainder(calendar.getRemainder());
                    }
                }
            }
        }

        return currentCalendarList;
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
        String[] strArr = hospitalMapper.getHospitalbyId(hpId).getRule().split(",");

        // 继续填充calendarList30返回值中total，remainder属性
        for (CalendarResponseDto cd:calendarList30){
            try{
                calendar.setTime(sdf.parse(cd.getYmd()));
            }catch (ParseException e){
                e.printStackTrace();
            }
            // 通过Calendar对象的get方法获取某天为星期几，就可以通过预约规则数组获取某天的总预约人数
            int total = Integer.parseInt(strArr[calendar.get(Calendar.DAY_OF_WEEK)-1]);
            // 填充total属性
            cd.setTotal(total);
            // 填充remainder属性
            cd.setRemainder(total-cd.getExisting());
        }
        return calendarList30;
    }

    // 获取当前年和当前月的日历
    private List<CalendarResponseDto> getCurrentCalendarList(Integer year, Integer month){
        List<CalendarResponseDto> currentCalendar = new ArrayList<>();
        //做万年历
        boolean isRun = false;
        if((year%4==0&&year%100!=0) || (year%400==0)) {
            isRun = true;
        }
        //计算从1900-01-01到现在的天数
        int totalDays = 0;
        for(int i=1900;i<year;i++) {
            if((i%4==0&&i%100!=0) || (i%400==0)){
                totalDays+=366;
            }else {
                totalDays+=365;
            }
        }
        int beforeDays = 0;
        int currentDays = 0;
        for (int j=1;j<=month;j++){
            switch (j){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    currentDays = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    currentDays = 30;
                    break;
                case 2:
                    if (isRun){
                        currentDays = 29;
                    }else{
                        currentDays = 28;
                    }
                    break;
            }
            if (j<month){
                beforeDays += currentDays;
            }
        }
        totalDays += beforeDays;
        int firstDayOfMonth = totalDays % 7 + 1;
        if(firstDayOfMonth == 7) {
            firstDayOfMonth = 0;}
        for(int k=0;k<firstDayOfMonth;k++) {
            currentCalendar.add( new CalendarResponseDto());
        }
        for(int i=1;i<=currentDays;i++) {
            String m = month<10?"0"+month:month+"";
            String d = i<10?"0"+i:i+"";
            currentCalendar.add( new CalendarResponseDto(year+"-"+m+"-"+d));
        }

        return currentCalendar;

    }
}
