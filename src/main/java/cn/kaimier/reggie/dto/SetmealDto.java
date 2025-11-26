package cn.kaimier.reggie.dto;

import cn.kaimier.reggie.entity.Setmeal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetmealDto extends Setmeal {
    private String categoryName;
}
