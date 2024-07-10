package com.whut.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.whut.entity.Article;
import com.whut.entity.Result;
import com.whut.entity.User;
import com.whut.service.UserService;
import com.whut.tools.Tool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cn.dev33.satoken.stp.StpUtil;

import javax.mail.MessagingException;
import java.util.List;

@Api(tags = "登录操作相关接口")
@RestController
@Slf4j
@RequestMapping("/user")
class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "发送邮箱验证码")
    @GetMapping(value = "sendEmail/{email}")
    public Result<Object> sendCode(@PathVariable String email) throws MessagingException {
        log.info("邮箱码：{}",email);
        //从redis中取出验证码信息
        String code = redisTemplate.opsForValue().get(email);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok().message(email + ":" + code + "已存在，还未过期");
        }
        boolean b = userService.mail(email);
        if (b) {
            return Result.ok().message("验证码发送成功！");
        }
        return Result.fail().message("邮箱不正确或为空！");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JSONObject json){
        String username = (String) json.get("username");
        String password = (String) json.get("password");
        System.out.println(username);
        System.out.println(password);
        User user=userService.doLogin(username,password);
        if( user!=null ){
            StpUtil.login(user.getId());
            return ResponseEntity.status(HttpStatus.OK).body(StpUtil.getTokenInfo().tokenValue);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("login unsuccessfully");
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

    @GetMapping("/deleteUser")
    public Boolean deleteUser(){
        Integer uid = Tool.tokenToId();
        return userService.deleteUser(uid);
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello world!";
    }

    @PostMapping("/changePassword")
    public boolean changePassword(JSONObject json){
        String username = (String) json.get("username");
        String newPassword = (String) json.get("newPassword");
        return userService.changPassword(username,newPassword);
    }
    @GetMapping("/getUser")
    public User getUser(){
        Integer uid = Tool.tokenToId();
        return userService.getUser(uid);
    }

    @GetMapping("/getOtherUser")
    public User getOtherUser(Integer other_uid){
        return userService.getUser(other_uid);
    }

}
