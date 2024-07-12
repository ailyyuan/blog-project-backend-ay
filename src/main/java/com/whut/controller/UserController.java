package com.whut.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whut.entity.Article;
import com.whut.entity.Comment;
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
import java.util.Objects;

@Api(tags = "登录操作相关接口")
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "发送邮箱验证码")
    @GetMapping("/sendEmail")
    public Result<Object> sendCode(String email) throws MessagingException {
        log.info("邮箱码：{}", email);
        // 从redis中取出验证码信息
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
    public ResponseEntity<Result<Object>> login(@RequestBody JSONObject json) {
        String username = json.getString("username");
        String password = json.getString("password");
        log.info("用户登录：{}", username);
        User user = userService.doLogin(username, password);
        if (user != null) {
            StpUtil.login(user.getId());
            return ResponseEntity.ok(Result.ok().data(StpUtil.getTokenInfo().tokenValue).message("登录成功"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.fail().message("登录失败，用户名或密码错误"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Result<Object>> doRegister(@RequestBody JSONObject json) {
        User user = JSON.toJavaObject(json.getJSONObject("user") , User.class);
        System.out.println(user);
        String captcha = json.getString("captcha");
        String certify_code = redisTemplate.opsForValue().get(user.getEmail());
        if(certify_code == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Result.fail().message("邮箱未验证！"));
        }
        else if(Objects.equals(captcha, certify_code)){
            log.info("用户注册：{}", user.getUsername());
            boolean flag = userService.doRegister(user);
            if (flag) {
                return ResponseEntity.ok(Result.ok().message("注册成功"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Result.fail().message("用户名重复，注册失败"));
            }
        }
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Result.fail().message("验证码错误"));
    }

    @GetMapping("/getAllUser")
    public Result<Object> getAllUser() {
        List<User> userList = userService.getAllUser();
        userList.forEach(User::mark);
        return Result.ok().data(userList).message("获取所有用户成功");
    }

    @GetMapping("/deleteUser")
    public Result<Object> deleteUser() {
        Integer uid = Tool.tokenToId();
        boolean deleted = userService.deleteUser(uid);
        if (deleted) {
            return Result.ok().message("删除成功").data(true);
        } else {
            return Result.fail().message("删除失败，请检查用户权限").data(false);
        }
    }

    @GetMapping("/hello")

    public String hello() {

        System.out.println("hello");
        return "Hello world!";
    }

    @PostMapping("/changePassword")
    public Result<Object> changePassword(@RequestBody JSONObject json) {
        String username = json.getString("username");
        String oldPassword = json.getString("oldPassword");
        String newPassword = json.getString("newPassword");

        boolean changed = userService.changePassword(username, oldPassword, newPassword);
        if (changed) {
            return Result.ok().message("密码修改成功").data(true);
        } else {
            return Result.fail().message("密码修改失败，请确认原密码是否正确").data(false);
        }
    }

    @GetMapping("/getUser")
    public Result<Object> getUser() {
        Integer uid = Tool.tokenToId();
        User user = userService.getUser(uid);
        if (user != null) {
            return Result.ok().data(user).message("获取用户信息成功");
        } else {
            return Result.fail().message("获取用户信息失败，用户不存在或未登录");
        }
    }

    @GetMapping("/getOtherUser")
    public Result<Object> getOtherUser(@RequestParam Integer other_uid) {
        User user = userService.getUser(other_uid);
        if (user != null) {
            return Result.ok().data(user).message("获取用户信息成功");
        } else {
            return Result.fail().message("获取用户信息失败，用户不存在");
        }
    }

    @PostMapping("/testRequestBody")
    public Result<Object> getRequest(@RequestBody User user){
        System.out.println(user);
        return Result.ok().message("测试成功");
    }
}
