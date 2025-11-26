package cn.kaimier.reggie.service;

import cn.kaimier.reggie.dto.SetmealDto;
import cn.kaimier.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SetmealService extends IService<Setmeal> {
    Page<SetmealDto> getSetmealPage(int page, int pageSize, String name);
}
