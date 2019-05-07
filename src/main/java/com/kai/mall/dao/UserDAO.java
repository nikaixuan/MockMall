package com.kai.mall.dao;

import com.kai.mall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by nikaixuan on 3/5/19.
 */
@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
}
