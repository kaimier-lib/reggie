package cn.kaimier.reggie.controller;

import cn.kaimier.reggie.common.R;
import cn.kaimier.reggie.dto.SetmealDto;
import cn.kaimier.reggie.service.SetmealService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    public R<Page<SetmealDto>> pageSetmeals(int page, int pageSize, @RequestParam(required = false) String name) {
        Page<SetmealDto> setmealPage = setmealService.pageSetmeals(page, pageSize, name);
        return R.success(setmealPage);
    }

    @PostMapping
    public R<String> saveSetmeal(@RequestBody SetmealDto setmealDto) {
        boolean saved = setmealService.saveSetmealWithDish(setmealDto);
        if (saved) {
            return R.success("新增套餐成功");
        } else {
            return R.error("新增套餐失败");
        }
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getSetmealWithDish(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.getSetmealWithDish(id);
        if (setmealDto != null) {
            return R.success(setmealDto);
        } else {
            return R.error("获取套餐信息失败");
        }

    }

    @PutMapping
    public R<String> updateSetmealWithDish(@RequestBody SetmealDto setmealDto) {
        boolean updated = setmealService.updateSetmealWithDish(setmealDto);
        if (updated) {
            return R.success("修改套餐成功");
        } else {
            return R.error("修改套餐失败");
        }
    }

    @DeleteMapping
    public R<String> deleteSetmealsWithDish(@RequestParam List<Long> ids) {
        boolean deleted = setmealService.deleteSetmealsWithDish(ids);
        if (deleted) {
            return R.success("套餐删除成功");
        } else {
            return R.error("套餐删除失败");
        }
    }

}
