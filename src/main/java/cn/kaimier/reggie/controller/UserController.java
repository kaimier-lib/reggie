package cn.kaimier.reggie.controller;

import cn.kaimier.reggie.common.R;
import cn.kaimier.reggie.dto.LoginRequest;
import cn.kaimier.reggie.entity.User;
import cn.kaimier.reggie.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        String code = userService.sendMsg(phone);
        session.setAttribute( phone, code);
        return R.success("手机验证码短信发送成功");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody LoginRequest request, HttpSession session) {
        User user = userService.login(request.getPhone(),request.getCode());
        session.setAttribute("user", user.getId());
        return R.success(user);
    }
}
