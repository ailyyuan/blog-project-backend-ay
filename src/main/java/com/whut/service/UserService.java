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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserOtmArticleMapper userOtmArticleMapper;

    public User getUserById(Integer uid) {
        return userMapper.selectById(uid);
    }
    public User getUserWithArticles(Integer uid) {
        // 查询用户信息
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("uid", uid);
        User user = userMapper.selectOne(qw);
        if (user != null) {
            // 查询用户关联的文章信息
            QueryWrapper<UserOtmArticle> uaqw=new QueryWrapper<>();
            uaqw.eq("uid", uid);
            List<UserOtmArticle> userOtmArticles = userOtmArticleMapper.selectList(uaqw);

            // 根据用户关联的文章ID查询文章详情
            if (!userOtmArticles.isEmpty()) {
                QueryWrapper<Article> aqw = new QueryWrapper<>();
                aqw.in("id", userOtmArticles.stream().map(UserOtmArticle::getArticleId).toArray());
                List<Article> articles = articleMapper.selectList(aqw);

                // 设置用户的文章列表
                user.setArticles(articles);
            }
        }

        return user;
    }
}
