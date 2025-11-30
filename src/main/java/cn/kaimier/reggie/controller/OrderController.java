package cn.kaimier.reggie.controller;

import cn.kaimier.reggie.common.R;
import cn.kaimier.reggie.entity.Orders;
import cn.kaimier.reggie.service.OrderService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    public R<Page<Orders>> pageOrders(int page, int pageSize) {
        Page<Orders> pageInfo = orderService.pageOrders(page, pageSize);
        return R.success(pageInfo);
    }
}
