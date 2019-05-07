package com.kai.mall.service;

import com.kai.mall.dao.PropertyValueDAO;
import com.kai.mall.pojo.Product;
import com.kai.mall.pojo.Property;
import com.kai.mall.pojo.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nikaixuan on 7/5/19.
 */
@Service
public class PropertyValueService {

    @Autowired
    PropertyValueDAO propertyValueDAO;
    @Autowired
    PropertyService propertyService;

    public void init(Product product){
        List<Property> properties = propertyService.listByCategory(product.getCategory());
        for (Property property:properties){
            PropertyValue propertyValue = getByPropertyAndProduct(property,product);
            if (propertyValue==null){
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValueDAO.save(propertyValue);
            }
        }
    }

    public PropertyValue getByPropertyAndProduct(Property property, Product product){
        return propertyValueDAO.getByProductAndProperty(product,property);
    }

    public List<PropertyValue> listByProduct(Product product){
        return propertyValueDAO.findByProductOrderByIdDesc(product);
    }

    public void update(PropertyValue bean){
        propertyValueDAO.save(bean);
    }
}
