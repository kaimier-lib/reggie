package cn.kaimier.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kaimier.reggie.entity.Category;
import cn.kaimier.reggie.entity.Dish;
import cn.kaimier.reggie.entity.Setmeal;
import cn.kaimier.reggie.mapper.CategoryMapper;
import cn.kaimier.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> 
        implements CategoryService{

    @Override
    public Page<Category> getCategoryPage(int page, int pageSize) {
        Page<Category> categoryPage = new Page<>(page, pageSize);
        return page(categoryPage);
    }

    @Override
    public boolean saveCategory(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(1L); // 假设创建人ID为1
        category.setUpdateUser(1L); // 假设修改人ID为1
        return save(category);
    }

    @Override
    public List<Category> listCategories(Category category) {

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null, Category::getType, category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        return list(queryWrapper);
    }
}