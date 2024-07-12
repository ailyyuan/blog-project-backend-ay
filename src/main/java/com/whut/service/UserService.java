package com.whut.service;


import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.whut.entity.Article;
import com.whut.entity.User;
import com.whut.entity.UserOtmArticle;
import com.whut.mapper.ArticleMapper;
import com.whut.mapper.UserMapper;
import com.whut.mapper.UserOtmArticleMapper;
import com.whut.tools.CodeGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Resource
    private JavaMailSenderImpl mailSender;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public boolean mail(String email) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //生成随机验证码
        String code = CodeGeneratorUtil.generateCode(6);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //设置一个html邮件信息
        helper.setText("<p style='color: blue'>你的验证码为：" + code + "(有效期为一分钟)</p>", true);
        //设置邮件主题名
        helper.setSubject("FlowerPotNet验证码----验证码");
        //发给谁-》邮箱地址
        helper.setTo(email);
        //谁发的-》发送人邮箱
        helper.setFrom("2233085367@qq.com");
        //将邮箱验证码以邮件地址为key存入redis,10分钟过期
        redisTemplate.opsForValue().set(email, code, Duration.ofMinutes(10));
        mailSender.send(mimeMessage);
        return true;
    }
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

        User user =  userMapper.selectById(uid);
        user.mark();
        return user;
    }

    public boolean deleteUser(Integer uid) {
        try{
            userMapper.deleteById(uid);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = doLogin(username , oldPassword);
        if(user!=null){
            user.setPassword(newPassword);
            userMapper.updateById(user);
            return true;
        }
        return false;
    }
}
