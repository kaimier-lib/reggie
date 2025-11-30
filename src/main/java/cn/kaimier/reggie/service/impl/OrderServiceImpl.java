package cn.kaimier.reggie.service.impl;

import cn.kaimier.reggie.entity.Orders;
import cn.kaimier.reggie.mapper.OrderMapper;
import cn.kaimier.reggie.service.OrderService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Override
    public Page<Orders> pageOrders(int page, int pageSize) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);

        return page(pageInfo);
    }
}
