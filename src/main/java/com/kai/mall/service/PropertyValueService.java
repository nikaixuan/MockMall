package com.kai.mall.service;

import com.kai.mall.dao.PropertyValueDAO;
import com.kai.mall.pojo.Product;
import com.kai.mall.pojo.Property;
import com.kai.mall.pojo.PropertyValue;
import com.kai.mall.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nikaixuan on 7/5/19.
 */
@Service
@CacheConfig(cacheNames = "propertyValues")
public class PropertyValueService {

    @Autowired
    PropertyValueDAO propertyValueDAO;
    @Autowired
    PropertyService propertyService;

    public void init(Product product){
        PropertyValueService propertyValueService = SpringContextUtil.getBean(PropertyValueService.class);
        List<Property> properties = propertyService.listByCategory(product.getCategory());
        for (Property property:properties){
            PropertyValue propertyValue = propertyValueService.getByPropertyAndProduct(property,product);
            if (propertyValue==null){
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValueDAO.save(propertyValue);
            }
        }
    }

    @Cacheable(key="'propertyValues-one-pid-'+#p0.id+ '-ptid-' + #p1.id")
    public PropertyValue getByPropertyAndProduct(Property property, Product product){
        return propertyValueDAO.getByProductAndProperty(product,property);
    }

    @Cacheable(key="'propertyValues-pid-'+#p0.id")
    public List<PropertyValue> listByProduct(Product product){
        return propertyValueDAO.findByProductOrderByIdDesc(product);
    }

    @CacheEvict(allEntries=true)
    public void update(PropertyValue bean){
        propertyValueDAO.save(bean);
    }
}
