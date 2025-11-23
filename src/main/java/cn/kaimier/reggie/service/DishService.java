package cn.kaimier.reggie.service;

import cn.kaimier.reggie.dto.DishDto;
import cn.kaimier.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DishService extends IService<Dish> {
    boolean saveWithFlavor(DishDto dishDto);

    Page<DishDto> pageDish(int page, int pageSize, String name);


    DishDto getDishWithFlavorById(Long id);

    boolean updateDishWithFlavor(DishDto dishDto);

    boolean deleteDishWithFlavorById(List<Long> ids);
}
