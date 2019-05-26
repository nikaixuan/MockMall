package com.kai.mall.service;

import com.kai.mall.dao.UserDAO;
import com.kai.mall.pojo.User;
import com.kai.mall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nikaixuan on 3/5/19.
 */
@Service
@CacheConfig(cacheNames="users")
public class UserService {
    @Autowired
    UserDAO userDAO;

    @Cacheable(key="'users-page-'+ #p0 +'-'+#p1")
    public Page4Navigator<User> list(int start, int size, int navigatePage){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(start,size,sort);

        return new Page4Navigator<>(userDAO.findAll(pageable),navigatePage);
    }

    @Cacheable(key = "'users-one-id-'+ #p0")
    public User findById(int id){
        return userDAO.findById(id).get();
    }

    @Cacheable(key = "'users-one-name-'+ #p0")
    public User findByName(String name){
        return userDAO.findByName(name);
    }

    @CacheEvict(allEntries = true)
    public void add(User bean){
        userDAO.save(bean);
    }

    public boolean isExist(String name){
        User user = userDAO.findByName(name);
        return user!=null;
    }

    @Cacheable(key="'users-one-name-'+ #p0 +'-password-'+ #p1")
    public User get(String name, String password) {
        return userDAO.getByNameAndPassword(name,password);
    }
}
