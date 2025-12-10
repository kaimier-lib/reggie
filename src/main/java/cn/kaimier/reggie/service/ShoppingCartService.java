package cn.kaimier.reggie.service;

import cn.kaimier.reggie.entity.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {
    List<ShoppingCart> listShoppingCart();
}
