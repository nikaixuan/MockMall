package com.kai.mall.dao;

import com.kai.mall.pojo.Category;
import com.kai.mall.pojo.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nikaixuan on 2/5/19.
 */
@Repository
public interface PropertyDAO extends JpaRepository<Property,Integer> {

    Page<Property> findByCategory(Category category, Pageable pageable);
    List<Property> findByCategory(Category category);
}
