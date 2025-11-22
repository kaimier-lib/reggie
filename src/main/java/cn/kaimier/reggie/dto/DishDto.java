package cn.kaimier.reggie.dto;

import cn.kaimier.reggie.entity.Dish;
import cn.kaimier.reggie.entity.DishFlavor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();
}
