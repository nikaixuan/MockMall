package com.kai.mall.dao;

import com.kai.mall.pojo.Order;
import com.kai.mall.pojo.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by nikaixuan on 7/5/19.
 */
public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrderOrderByIdDesc(Order order);
}
