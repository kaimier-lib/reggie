package cn.kaimier.reggie.controller;

import cn.kaimier.reggie.common.R;
import cn.kaimier.reggie.entity.ShoppingCart;
import cn.kaimier.reggie.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> listShoppingCart() {
        List<ShoppingCart> shoppingCarts = shoppingCartService.listShoppingCart();
        return R.success(shoppingCarts);
    }
}
