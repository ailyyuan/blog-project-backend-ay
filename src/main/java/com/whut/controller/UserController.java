package com.whut.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSONObject;
import com.whut.entity.Article;
import com.whut.entity.User;
import com.whut.service.UserService;
import com.whut.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cn.dev33.satoken.stp.StpUtil;
import java.util.List;

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JSONObject json){
        String username = (String) json.get("username");
        String password = (String) json.get("password");
        System.out.println(username);
        System.out.println(password);
        User user=userService.doLogin(username,password);
        if( user!=null ){

            StpUtil.login(user.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Login successfully");
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
    }
    @PostMapping("/register")
    public ResponseEntity<String> doRegister(@RequestBody User user) {
        System.out.println(user);
        boolean flag = userService.doRegister(user);
        if(flag){
            return ResponseEntity.status(HttpStatus.OK).body("Register successfully");
        }
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Repeated username");
    }

    @GetMapping("/getAllUser")
    public List<User> getAllUser(){
        List<User> userList = userService.getAllUser();
        userList.forEach(User::mark);
        return userList;
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello world!";
    }
}
