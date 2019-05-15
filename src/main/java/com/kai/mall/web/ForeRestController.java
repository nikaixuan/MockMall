package com.kai.mall.web;

import com.kai.mall.pojo.*;
import com.kai.mall.service.*;
import com.kai.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

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
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    PropertyValueService propertyValueService;

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

    @GetMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid") int pid ) {
        Product product = productService.getById(pid);

        List<ProductImage> productSingleImages = productImageService.listSingleProductImages(product);
        List<ProductImage> productDetailImages = productImageService.listDetailProductImages(product);

        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        List<Review> reviews = reviewService.list(product);
        List<PropertyValue> propertyValues = propertyValueService.listByProduct(product);
        productService.setSaleAndReviewNumber(product);
        productImageService.setFirstProductImage(product);

        Map<String,Object> map= new HashMap<>();
        map.put("product",product);
        map.put("pvs",propertyValues);
        map.put("reviews",reviews);

        return map;
    }

    @GetMapping("/forecategory/{cid}")
    public Object category(@PathVariable("cid") int cid, String sort){
        Category category = categoryService.get(cid);

        productService.fill(category);
        productService.setSaleAndReviewNumber(category.getProducts());
        categoryService.removeCategoryFromProduct(category);

        if(null!=sort){
            switch(sort){
                case "review":
                    Collections.sort(category.getProducts(),
                            (o1,o2)->Integer.compare(o1.getReviewCount(),o2.getReviewCount()));
                    break;
                case "date" :
                    Collections.sort(category.getProducts(),
                            (o1,o2)->o2.getCreateDate().compareTo(o1.getCreateDate()));
                    break;

                case "saleCount" :
                    Collections.sort(category.getProducts(),
                            (o1,o2)->Integer.compare(o1.getSaleCount(),o2.getSaleCount()));
                    break;

                case "price":
                    Collections.sort(category.getProducts(),
                            (o1,o2)->Float.compare(o1.getPromotePrice(),o2.getPromotePrice()));
                    break;

                case "all":
                    Collections.sort(category.getProducts(),
                            (o1,o2)->o2.getReviewCount()*o2.getSaleCount()-o1.getReviewCount()*o1.getSaleCount());
                    break;
            }
        }
        return category;
    }

    @PostMapping("foresearch")
    public Object search(String keyword){
        if (keyword==null){
            keyword="";
        }
        List<Product> products = productService.search(keyword,0,10);
        productImageService.setFirstProductImages(products);
        productService.setSaleAndReviewNumber(products);
        return products;
    }

}
