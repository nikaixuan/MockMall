package com.kai.mall.web;

import com.kai.mall.pojo.Product;
import com.kai.mall.service.ProductImageService;
import com.kai.mall.service.ProductService;
import com.kai.mall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by nikaixuan on 3/5/19.
 */
@RestController
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;

    @GetMapping("/categories/{cid}/products")
    public Page4Navigator<Product> list(@PathVariable("cid") int cid, @RequestParam(value = "start", defaultValue = "0")
    int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception{
        start = start<0?0:start;
        Page4Navigator<Product> page = productService.list(cid,start,size,5);
        productImageService.setFirstProductImages(page.getContent());
        return page;
    }

    @PostMapping("/products")
    public Product add(@RequestBody Product bean) throws Exception{
        System.out.println(bean.getOriginalPrice());
        bean.setCreateDate(new Date());

        productService.add(bean);
        return bean;
    }

    @PutMapping("/products")
    public Product update(@RequestBody Product bean) throws Exception{
        productService.add(bean);
        return bean;
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable("id") int id) throws Exception{
        productService.delete(id);
    }

    @GetMapping("/products/{id}")
    public Product get(@PathVariable("id") int id) throws Exception{
        return productService.getById(id);
    }
}
