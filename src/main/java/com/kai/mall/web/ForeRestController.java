package com.kai.mall.web;

import com.kai.mall.pojo.Category;
import com.kai.mall.pojo.User;
import com.kai.mall.service.CategoryService;
import com.kai.mall.service.ProductService;
import com.kai.mall.service.UserService;
import com.kai.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by nikaixuan on 8/5/19.
 */
@RestController
public class ForeRESTController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    @GetMapping("/forehome")
    public Object home(){
        List<Category> cs = categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        categoryService.removeCategoryFromProduct(cs);
        return cs;
    }

    @PostMapping("/foreregister")
    public Object register(@RequestBody User user){
        String name = HtmlUtils.htmlEscape(user.getName());
        user.setName(name);
        if (userService.isExist(name)){
            String message = "Name already exist";
            return Result.fail(message);
        }
        userService.add(user);
        return Result.success();
    }

    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam, HttpSession session) {
        String name =  userParam.getName();
        name = HtmlUtils.htmlEscape(name);

        User user =userService.get(name,userParam.getPassword());
        if(null==user){
            String message ="Wrong information";
            return Result.fail(message);
        }
        else{
            session.setAttribute("user", user);
            return Result.success();
        }
    }

    @GetMapping("/forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:home";
    }
}
