package com.kai.mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.Order;

/**
 * Created by nikaixuan on 7/5/19.
 */
public interface OrderDAO extends JpaRepository<com.kai.mall.pojo.Order,Integer> {
}
