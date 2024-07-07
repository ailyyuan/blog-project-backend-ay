package com.whut.controller;

import com.whut.entity.Article;
import com.whut.entity.User;
import com.whut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public List<Article> test(){
        User user = userService.getUserWithArticles(1);
        return user.getArticles();
    }
    @GetMapping("/getUserById")
    public User getUserById(){
        return userService.getUserById(1);
    }
}
