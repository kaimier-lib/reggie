package cn.kaimier.reggie.controller;

import cn.kaimier.reggie.common.R;
import cn.kaimier.reggie.entity.Category;
import cn.kaimier.reggie.service.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/category")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/page")
    public R<Page<Category>> getCategoryPage(int page, int pageSize) {
        Page<Category> categoryPage = categoryService.getCategoryPage(page, pageSize);
        return R.success(categoryPage);
    }

    @PostMapping
    public R<String> saveCategory(@RequestBody Category category) {
        if (categoryService.saveCategory(category)) {
            return R.success("新增分类成功");
        }
        else
            return R.error("新增分类失败");
    }

    @DeleteMapping
    public R<String> deleteCategory(Long id) {
        boolean removed = categoryService.removeById(id);
        if (removed) {
            return R.success("分类信息删除成功");
        } else {
            return R.error("分类信息删除失败");
        }
    }

    @GetMapping("/list")
    public R<List<Category>> listCategories(@RequestParam(required = false) Integer type) {
        List<Category> categories = categoryService.listCategories(type);

        return R.success(categories);
    }
}
