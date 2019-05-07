package com.kai.mall.service;

import com.kai.mall.dao.UserDAO;
import com.kai.mall.pojo.User;
import com.kai.mall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nikaixuan on 3/5/19.
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public Page4Navigator<User> list(int start, int size, int navigatePage){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(start,size,sort);

        return new Page4Navigator<>(userDAO.findAll(pageable),navigatePage);
    }

    public User findById(int id){
        return userDAO.findById(id).get();
    }
}
