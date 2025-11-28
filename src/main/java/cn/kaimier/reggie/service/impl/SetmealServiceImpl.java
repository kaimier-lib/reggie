package cn.kaimier.reggie.service.impl;

import cn.kaimier.reggie.dto.SetmealDto;
import cn.kaimier.reggie.entity.Setmeal;
import cn.kaimier.reggie.entity.SetmealDish;
import cn.kaimier.reggie.mapper.SetmealMapper;
import cn.kaimier.reggie.service.CategoryService;
import cn.kaimier.reggie.service.SetmealDishService;
import cn.kaimier.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {
    private final CategoryService categoryService;
    private final SetmealDishService setMealDishService;

    @Override
    public Page<SetmealDto> getSetmealPage(int page, int pageSize, String name) {

        Page<SetmealDto> pageDto = new Page<>();

        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name !=null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        page(pageInfo, queryWrapper);

        BeanUtils.copyProperties( pageInfo, pageDto, "records");

        List<SetmealDto> list = pageInfo.getRecords().stream().map(setmeal -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            setmealDto.setCategoryName(categoryService.getById(setmeal.getCategoryId()).getName());
            return setmealDto;
        }).toList();

        pageDto.setRecords(list);

        return pageDto;
    }

    @Override
    @Transactional
    public boolean saveSetmealWithDish(SetmealDto setmealDto) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto, setmeal);
        save(setmeal);

        Long setmealId = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.forEach(item -> item.setSetmealId(setmealId)
        );
        setMealDishService.saveBatch(setmealDishes);

        return true;
    }

    @Override
    @Transactional
    public SetmealDto getSetmealWithDish(Long id) {
        // 查询套餐基本信息，并拷贝到SetmealDto
        SetmealDto setmealDto = new SetmealDto();
        Setmeal setmeal = getById(id);
        BeanUtils.copyProperties(setmeal, setmealDto);

        // 查询套餐关联的菜品信息,并设置到SetmealDto
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> setmealDishes = setMealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(setmealDishes);

        // 返回套餐DTO对象
        return setmealDto;
    }

    @Override
    public boolean updateSetmealWithDish(SetmealDto setmealDto) {
        // 更新套餐基本信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto, setmeal, "createTime", "createUser", "updateTime", "updateUser");
        updateById(setmeal);

        // 删除旧的套餐菜品关联信息
        Long setmealId = setmealDto.getId();
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealId);
        setMealDishService.remove(queryWrapper);

        // 添加新的套餐菜品关联信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.forEach(item -> item.setSetmealId(setmealId));
        setMealDishService.saveBatch(setmealDishes);

        // 返回更新结果
        return true;
    }

    @Override
    @Transactional
    public boolean deleteSetmealsWithDish(List<Long> ids) {
        // 删除套餐基本信息
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.in(Setmeal::getId, ids);
        remove(setmealQueryWrapper);

        // 删除套餐关联的菜品信息
        LambdaQueryWrapper<SetmealDish> setmealDishQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setMealDishService.remove(setmealDishQueryWrapper);

        return true;
    }
}
