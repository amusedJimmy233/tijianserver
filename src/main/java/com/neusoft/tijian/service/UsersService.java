package com.neusoft.tijian.service;

import com.neusoft.tijian.po.Users;

public interface UsersService {
    public Users getUsersByUserIdByPass(Users users);
    public Users getUsersById(String userId);
    public int saveUsers(Users users);
}
