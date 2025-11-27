package cn.kaimier.reggie.service.impl;

import cn.kaimier.reggie.dto.DishDto;
import cn.kaimier.reggie.entity.Category;
import cn.kaimier.reggie.entity.Dish;
import cn.kaimier.reggie.entity.DishFlavor;
import cn.kaimier.reggie.mapper.DishMapper;
import cn.kaimier.reggie.service.CategoryService;
import cn.kaimier.reggie.service.DishFlavorService;
import cn.kaimier.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

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
    public Page<DishDto> pageDish(int page, int pageSize, String name) {

        Page<Dish> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        page(pageInfo, queryWrapper);


        // Page里的List拿出来进行处理
        Map<Long, String> categoryMap = categoryService.list().stream().collect(Collectors.toMap(Category::getId, Category::getName));

        // pageInfo 的分页数据，缺少菜品分类名称，需要进行填充后生成新的 dishDtoPage 对象返回
        Page<DishDto> dishDtoPage = new Page<>();
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<DishDto> dishDtoList = pageInfo.getRecords().stream().map(dish -> {
            DishDto dishDto = new DishDto();
            // 将 dish 的属性拷贝到 dishDto 中
            BeanUtils.copyProperties(dish, dishDto);
            // 根据 categoryId 查询分类名称
            dishDto.setCategoryName(categoryMap.get(dish.getCategoryId()));
            return dishDto;
        }).toList();

        dishDtoPage.setRecords(dishDtoList);
        return dishDtoPage;

    }

    @Override
    public DishDto getDishWithFlavorById(Long id) {
        DishDto dishDto = new DishDto();
        // Dish 基本信息取出来复制到 DishDto
        Dish dish = getById(id);
        BeanUtils.copyProperties(dish, dishDto);

        // 菜品口味信息取出来放到 DishDto
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> flavors = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    public boolean updateDishWithFlavor(DishDto dishDto) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDto,dish,"createTime", "createUser", "updateTime", "updateUser");
        dish.setUpdateTime(LocalDateTime.now());
        dish.setUpdateUser(1L); // 假设修改人ID为1
        updateById(dish);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        dishFlavorService.remove(queryWrapper);

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(flavor -> {
            flavor.setDishId(dish.getId());
            flavor.setCreateTime(LocalDateTime.now());
            flavor.setUpdateTime(LocalDateTime.now());
            flavor.setCreateUser(1L); // 假设创建人ID为1
            flavor.setUpdateUser(1L); // 假设修改人ID为1
        });

        dishFlavorService.saveBatch(flavors);


        return true;
    }

    @Override
    public boolean deleteDishWithFlavorById(List<Long> ids) {
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.in(Dish::getId, ids);
        remove(dishQueryWrapper);

        LambdaQueryWrapper<DishFlavor> dishFlavorQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorQueryWrapper.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(dishFlavorQueryWrapper);

        return true;
    }

    @Override
    public List<Dish> listDishes(Long categoryId) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId, categoryId);
        queryWrapper.eq(Dish::getStatus, 1); // 只查询起售的菜品
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        return list(queryWrapper);
    }
}
