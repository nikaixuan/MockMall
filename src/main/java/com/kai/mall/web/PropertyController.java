package com.kai.mall.web;

import com.kai.mall.pojo.Property;
import com.kai.mall.service.PropertyService;
import com.kai.mall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nikaixuan on 2/5/19.
 */
@RestController
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("/properties/{id}")
    public Property get(@PathVariable("id") int id) throws Exception{
        return propertyService.getById(id);
    }

    @DeleteMapping("/properties/{id}")
    public void delete(@PathVariable("id") int id) throws Exception{
        propertyService.delete(id);
    }

    @PutMapping("/properties")
    public Property update(@RequestBody Property bean) throws  Exception{
        propertyService.update(bean);
        return bean;
    }

    @PostMapping("/properties")
    public Property add(@RequestBody Property bean) throws Exception{
        propertyService.update(bean);
        return bean;
    }

    @GetMapping("/categories/{cid}/properties")
    public Page4Navigator<Property> list(@RequestParam(value = "start",defaultValue = "0") int start,
                                         @RequestParam(value = "size",defaultValue = "10") int size,
                                         @PathVariable("cid") int cid) throws Exception{

        start = start<0?0:start;
        Page4Navigator<Property> propertyPage4Navigator = propertyService.list(cid,start,size,5);
        return propertyPage4Navigator;
    }


}
