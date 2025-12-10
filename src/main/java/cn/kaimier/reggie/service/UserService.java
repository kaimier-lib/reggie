package cn.kaimier.reggie.service;

import cn.kaimier.reggie.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    User login(String phone, String code);

    String sendMsg(String phone);
}
