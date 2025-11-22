package cn.kaimier.reggie.controller;

import cn.kaimier.reggie.common.R;
import cn.kaimier.reggie.dto.DishDto;
import cn.kaimier.reggie.entity.Dish;
import cn.kaimier.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public R<Page<Dish>> getDishPage(int page, int pageSize,String name) {

        return R.success(dishService.pageDish(page, pageSize, name));

    }

    @PostMapping
    public R<String> saveDish(@RequestBody DishDto dishDto) {
        log.info("新增菜品：{}", dishDto);
        // 带着口味信息保存菜品
        boolean saved = dishService.saveWithFlavor(dishDto);
        if (saved) {
            return R.success("新增菜品成功");
        } else {
            return R.error("新增菜品失败");
        }
    }

}
