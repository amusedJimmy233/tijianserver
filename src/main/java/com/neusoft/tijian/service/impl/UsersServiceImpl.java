package com.neusoft.tijian.service.impl;

import com.neusoft.tijian.mapper.UsersMapper;
import com.neusoft.tijian.po.Users;
import com.neusoft.tijian.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    // 自动按照类型注解
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users getUsersByUserIdByPass(Users users) {
        return usersMapper.getUsersByUserIdByPass(users);
    }

    @Override
    public Users getUsersById(String userId) {
        return usersMapper.getUsersById(userId);
    }

    @Override
    public int saveUsers(Users users) {
        return usersMapper.saveUsers(users);
    }
}
