package com.kai.mall.web;

import com.kai.mall.pojo.User;
import com.kai.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nikaixuan on 7/5/19.
 */
@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/serverlogin")
    public User login(@RequestBody User bean){
        User user = userService.findByName(bean.getName());
        if (user!=null&&user.getPassword().equals(bean.getPassword())){
            return user;
        }else {
            return null;
        }
    }
}
