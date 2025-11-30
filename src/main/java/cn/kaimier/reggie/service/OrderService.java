package cn.kaimier.reggie.service;

import cn.kaimier.reggie.entity.Orders;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderService extends IService<Orders> {
    Page<Orders> pageOrders(int page, int pageSize);
}
