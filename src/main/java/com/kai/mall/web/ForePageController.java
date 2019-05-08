package com.kai.mall.web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by nikaixuan on 8/5/19.
 */
@Controller
public class ForePageController {
    @GetMapping(value="/")
    public String index(){
        return "redirect:home";
    }
    @GetMapping(value="/home")
    public String home(){
        return "fore/home";
    }
}
