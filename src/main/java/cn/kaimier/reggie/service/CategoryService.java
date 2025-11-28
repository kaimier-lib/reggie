package cn.kaimier.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kaimier.reggie.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    Page<Category> getCategoryPage(int page, int pageSize);

    boolean saveCategory(Category category);

    List<Category> listCategories(Integer type);
}