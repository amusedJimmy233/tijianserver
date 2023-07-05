package com.neusoft.tijian.service;

import com.neusoft.tijian.po.Orders;

public interface OrdersService {
    public int getOrdersByUserId(String userId);
    public int saveOrders (Orders orders);
}
