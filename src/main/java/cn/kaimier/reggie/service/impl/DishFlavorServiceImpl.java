package cn.kaimier.reggie.service.impl;

import cn.kaimier.reggie.entity.DishFlavor;
import cn.kaimier.reggie.mapper.DishFlavorMapper;
import cn.kaimier.reggie.service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
