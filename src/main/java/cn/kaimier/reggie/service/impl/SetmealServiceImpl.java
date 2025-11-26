package cn.kaimier.reggie.service.impl;

import cn.kaimier.reggie.dto.SetmealDto;
import cn.kaimier.reggie.entity.Setmeal;
import cn.kaimier.reggie.mapper.CategoryMapper;
import cn.kaimier.reggie.mapper.SetmealMapper;
import cn.kaimier.reggie.service.CategoryService;
import cn.kaimier.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    public SetmealServiceImpl(CategoryMapper categoryMapper, CategoryService categoryService) {
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
    }

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
}
