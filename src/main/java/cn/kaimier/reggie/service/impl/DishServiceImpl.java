package cn.kaimier.reggie.service.impl;

import cn.kaimier.reggie.dto.DishDto;
import cn.kaimier.reggie.entity.Dish;
import cn.kaimier.reggie.entity.DishFlavor;
import cn.kaimier.reggie.mapper.DishMapper;
import cn.kaimier.reggie.service.DishFlavorService;
import cn.kaimier.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public boolean saveWithFlavor(DishDto dishDto) {

        dishDto.setCreateTime(LocalDateTime.now());
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setCreateUser(1L); // 假设创建人ID为1
        dishDto.setUpdateUser(1L); // 假设修改人ID为1
        // 保存菜品的基本信息到菜品表dish
        save(dishDto);
        Long dishId = dishDto.getId();

        // 保存菜品口味数据到菜品口味表dish_flavor
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(f -> {

            f.setDishId(dishId);
            f.setCreateTime(LocalDateTime.now());
            f.setUpdateTime(LocalDateTime.now());
            f.setCreateUser(1L); // 假设创建人ID为1
            f.setUpdateUser(1L); // 假设修改人ID为1

        });

        dishFlavorService.saveBatch(flavors);

        return true;
    }

    @Override
    public Page<Dish> pageDish(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        return page(pageInfo, queryWrapper);
    }
}
