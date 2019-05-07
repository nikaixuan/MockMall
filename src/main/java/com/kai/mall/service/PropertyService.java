package com.kai.mall.service;

import com.kai.mall.dao.PropertyDAO;
import com.kai.mall.pojo.Category;
import com.kai.mall.pojo.Property;
import com.kai.mall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nikaixuan on 2/5/19.
 */
@Service
public class PropertyService {

    @Autowired
    PropertyDAO propertyDAO;

    @Autowired
    CategoryService categoryService;

    public void add(Property property){
        propertyDAO.save(property);
    }

    public void update(Property property){
        propertyDAO.save(property);
    }

    public Property getById(int id){
        return propertyDAO.findById(id).get();
    }

    public void delete(int id){
        propertyDAO.deleteById(id);
    }

    public List<Property> listByCategory(Category category){
        return propertyDAO.findByCategory(category);
    }

    public Page4Navigator<Property> list(int cid, int start, int size, int navigatePages){

        Category category = categoryService.get(cid);
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(start,size,sort);
        Page pageFromJPA =propertyDAO.findByCategory(category,pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

}
