package cn.kaimier.reggie.service.impl;

import cn.kaimier.reggie.common.BusinessException;
import cn.kaimier.reggie.entity.Employee;
import cn.kaimier.reggie.mapper.EmployeeMapper;
import cn.kaimier.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public Employee login(String username, String rawPassword) {
        // 1️⃣ 根据用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        Employee loginEmployee = getOne(queryWrapper);

        // 2️⃣ 校验用户是否存在
        if (loginEmployee == null) {
            throw new BusinessException("用户名不存在");
        }

        // 3️⃣ 校验密码是否正确
        String encryptedPassword = DigestUtils.md5DigestAsHex(rawPassword.getBytes());
        if (!loginEmployee.getPassword().equals(encryptedPassword)) {
            throw new BusinessException("密码错误");
        }

        // 4️⃣ 校验状态
        if (loginEmployee.getStatus()==0) {
            throw new BusinessException("账号已禁用");
        }

        // 5️⃣ 返回业务对象（不封装 R）
        return loginEmployee;
    }

}
