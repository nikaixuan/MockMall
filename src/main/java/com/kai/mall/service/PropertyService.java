package com.kai.mall.service;

import com.kai.mall.dao.PropertyDAO;
import com.kai.mall.pojo.Category;
import com.kai.mall.pojo.Property;
import com.kai.mall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames="properties")
public class PropertyService {

    @Autowired
    PropertyDAO propertyDAO;

    @Autowired
    CategoryService categoryService;

    @CacheEvict(allEntries=true)
    public void add(Property property){
        propertyDAO.save(property);
    }

    @CacheEvict(allEntries=true)
    public void update(Property property){
        propertyDAO.save(property);
    }

    @Cacheable(key="'properties-one-'+ #p0")
    public Property getById(int id){
        return propertyDAO.findById(id).get();
    }

    @CacheEvict(allEntries=true)
    public void delete(int id){
        propertyDAO.deleteById(id);
    }

    @Cacheable(key="'properties-cid-'+ #p0.id")
    public List<Property> listByCategory(Category category){
        return propertyDAO.findByCategory(category);
    }

    @Cacheable(key="'properties-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
    public Page4Navigator<Property> list(int cid, int start, int size, int navigatePages){

        Category category = categoryService.get(cid);
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(start,size,sort);
        Page pageFromJPA =propertyDAO.findByCategory(category,pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

}
