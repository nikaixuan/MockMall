package com.kai.mall.dao;

import com.kai.mall.pojo.Order;
import com.kai.mall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * Created by nikaixuan on 7/5/19.
 */
@Repository
public interface OrderDAO extends JpaRepository<com.kai.mall.pojo.Order,Integer> {
    public List<Order> findByUserAndStatusNotOrderByIdDesc(User user, String status);
}
