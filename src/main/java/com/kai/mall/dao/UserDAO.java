package com.kai.mall.dao;

import com.kai.mall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by nikaixuan on 3/5/19.
 */
public interface UserDAO extends JpaRepository<User, Integer> {
}
