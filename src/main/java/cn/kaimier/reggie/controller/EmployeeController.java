package cn.kaimier.reggie.controller;

import cn.kaimier.reggie.common.R;
import cn.kaimier.reggie.entity.Employee;
import cn.kaimier.reggie.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.util.Collections;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        Employee loginEmployee = employeeService.login(employee.getUsername(), employee.getPassword());

        // 创建安全上下文
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // 创建 Authentication 对象
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                loginEmployee,null, Collections.emptyList()
        );

        // 将 Authentication 对象存储在安全上下文中
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        // 将安全上下文存储在 HttpSession 中
        request.getSession().setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        return R.success(loginEmployee);
    }
}