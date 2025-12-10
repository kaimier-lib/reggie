package cn.kaimier.reggie.service.impl;

import cn.kaimier.reggie.entity.User;
import cn.kaimier.reggie.mapper.UserMapper;
import cn.kaimier.reggie.service.UserService;
import cn.kaimier.reggie.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User login(String phone, String code) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        User user = getOne(queryWrapper);
        if(user==null){
            user = new User();
            user.setPhone(phone);
            save(user);
        }
        return user;
    }

    @Override
    public String sendMsg(String phone) {
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        log.info("code={}",code);
        return code;
    }
}
