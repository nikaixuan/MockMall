package com.kai.mall.dao;

import com.kai.mall.pojo.Product;
import com.kai.mall.pojo.Property;
import com.kai.mall.pojo.PropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nikaixuan on 6/5/19.
 */
@Repository
public interface PropertyValueDAO extends JpaRepository<PropertyValue, Integer> {

    List<PropertyValue> findByProductOrderByIdDesc(Product product);

    PropertyValue getByProductAndProperty(Product product, Property property);
}
