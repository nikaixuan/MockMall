package com.kai.mall.dao;


import java.util.List;

import com.kai.mall.pojo.Product;
import com.kai.mall.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by nikaixuan on 9/5/19.
 */

@Repository
public interface ReviewDAO extends JpaRepository<Review,Integer>{

    List<Review> findByProductOrderByIdDesc(Product product);
    int countByProduct(Product product);

}
