package com.kai.mall.dao;

import com.kai.mall.pojo.Order;
import com.kai.mall.pojo.OrderItem;
import com.kai.mall.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nikaixuan on 7/5/19.
 */
@Repository
public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrderOrderByIdDesc(Order order);
    List<OrderItem> findByProduct(Product product);
}
