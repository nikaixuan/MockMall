package com.kai.mall.dao;

import com.kai.mall.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by nikaixuan on 1/5/19.
 */
@Repository
public interface CategoryDAO extends JpaRepository<Category, Integer> {
}
