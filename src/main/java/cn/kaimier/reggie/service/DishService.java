package cn.kaimier.reggie.service;

import cn.kaimier.reggie.dto.DishDto;
import cn.kaimier.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DishService extends IService<Dish> {
    boolean saveWithFlavor(DishDto dishDto);

    Page<Dish> pageDish(int page, int pageSize, String name);
}
