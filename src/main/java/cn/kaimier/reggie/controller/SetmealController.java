package cn.kaimier.reggie.controller;

import cn.kaimier.reggie.common.R;
import cn.kaimier.reggie.dto.SetmealDto;
import cn.kaimier.reggie.service.SetmealService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    public R<Page<SetmealDto>> getSetmealPage(int page, int pageSize, @RequestParam(required = false) String name) {
        Page<SetmealDto> setmealPage = setmealService.getSetmealPage(page, pageSize, name);
        if (setmealPage != null) {
            return R.success(setmealPage);
        } else {
            return R.error("获取套餐分页信息失败");
        }
    }

}
