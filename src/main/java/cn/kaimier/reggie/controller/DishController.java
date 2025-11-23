package cn.kaimier.reggie.controller;

import cn.kaimier.reggie.common.R;
import cn.kaimier.reggie.dto.DishDto;
import cn.kaimier.reggie.entity.Dish;
import cn.kaimier.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public R<Page<DishDto>> getDishPage(int page, int pageSize,String name) {

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

    @DeleteMapping
    public R<String> deleteDish(@RequestParam List<Long> ids) {
        boolean removed = dishService.deleteByIdWithFlavor(ids);
        if (removed) {
            return R.success("菜品信息删除成功");
        } else {
            return R.error("菜品信息删除失败");
        }
    }

    @GetMapping("/{id}")
    public R<DishDto> getDishById(@PathVariable Long id) {
        DishDto dishDto = dishService.getDishByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto) {
        log.info("修改菜品：{}", dishDto);
        boolean updated = dishService.updateDishWithFlavor(dishDto);
        if (updated) {
            return R.success("修改菜品成功");
        } else {
            return R.error("修改菜品失败");
        }
    }
}
