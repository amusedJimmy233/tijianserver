package com.neusoft.tijian.mapper;

import com.neusoft.tijian.po.Users;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersMapper {

    // 登录
    @Select("select * from users where userId=#{userId} and password=#{password)")
    public Users getUsersByUserIdByPass(Users users);

    // 用户电话号码是否已经存在的验证
    @Select("select * from users where userId=#{userId}")
    public Users getUsersById(String userId);

    // 注册
    @Select("insert into users values(#{userId},#{password},#{realName},#{sex},#{identityCard},#{birthday},#{userType})")
    public int saveUsers(Users users);
}
