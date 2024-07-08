package com.whut.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whut.entity.Article;
import com.whut.entity.User;
import com.whut.entity.UserOtmArticle;
import com.whut.mapper.ArticleMapper;
import com.whut.mapper.UserMapper;
import com.whut.mapper.UserOtmArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User doLogin(String username, String password){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", username);
        qw.eq("password", password);
        User user = userMapper.selectOne(qw);
        System.out.println(user);
        return user;
    }

    public boolean doRegister(User user){
        boolean type = false;
        if(user!=null){
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq("username", user.getUsername());
            if(userMapper.selectOne(qw) != null){
                return type;
            }
            if(userMapper.insert(user)>0){
                type = true;
            }
        }
        return type;
    }
    public List<User> getAllUser(){
        QueryWrapper<User> qw = new QueryWrapper<>();
        return userMapper.selectList(qw);
    }
    public User getUser(Integer uid) {
        return userMapper.selectById(uid);
    }

    public boolean deleteUser(Integer uid) {
        try{
            userMapper.deleteById(uid);
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
