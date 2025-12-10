package cn.kaimier.reggie.service.impl;

import cn.kaimier.reggie.common.BaseContext;
import cn.kaimier.reggie.entity.ShoppingCart;
import cn.kaimier.reggie.mapperl.ShoppingCartMapper;
import cn.kaimier.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Override
    public List<ShoppingCart> listShoppingCart() {

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        return list(queryWrapper);
    }
}
