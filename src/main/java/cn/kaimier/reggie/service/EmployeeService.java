package cn.kaimier.reggie.service;

import cn.kaimier.reggie.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

public interface EmployeeService extends IService<Employee> {

    Employee login(String name, String password);
}
