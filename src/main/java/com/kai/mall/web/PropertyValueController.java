package com.kai.mall.web;

import com.kai.mall.pojo.Product;
import com.kai.mall.pojo.PropertyValue;
import com.kai.mall.service.ProductService;
import com.kai.mall.service.PropertyService;
import com.kai.mall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by nikaixuan on 7/5/19.
 */
@RestController
public class PropertyValueController {

    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;

    @GetMapping("/products/{pid}/propertyValues")
    public List<PropertyValue> list(@PathVariable("pid") int pid){
        Product product = productService.getById(pid);
        propertyValueService.init(product);
        return propertyValueService.listByProduct(product);
    }

    @PutMapping("/propertyValues")
    public PropertyValue update(@RequestBody PropertyValue bean){
        propertyValueService.update(bean);
        return bean;
    }
}
