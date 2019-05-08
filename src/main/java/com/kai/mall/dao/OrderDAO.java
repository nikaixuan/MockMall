package com.kai.mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Order;

/**
 * Created by nikaixuan on 7/5/19.
 */
@Repository
public interface OrderDAO extends JpaRepository<com.kai.mall.pojo.Order,Integer> {
}
