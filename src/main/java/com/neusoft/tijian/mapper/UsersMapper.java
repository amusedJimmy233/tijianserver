package com.neusoft.tijian.mapper;

import com.neusoft.tijian.po.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersMapper {

    // 登录
    @Select("select * from users where userId=#{userId} and password=#{password)")
    public Users getUsersByUserIdByPass(Users users);
}
