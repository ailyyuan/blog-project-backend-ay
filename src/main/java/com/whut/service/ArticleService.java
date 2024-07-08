package com.whut.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whut.entity.Article;
import com.whut.entity.User;
import com.whut.entity.UserOtmArticle;
import com.whut.mapper.ArticleMapper;
import com.whut.mapper.UserMapper;
import com.whut.mapper.UserOtmArticleMapper;
import com.whut.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserOtmArticleMapper userOtmArticleMapper;

    public Article getArticle(Integer id) {
        return articleMapper.selectById(id);
    }

    public List<Article> getUserWithArticles(Integer uid) {
        System.out.println(uid);
        // 查询用户信息
        User user = userService.getUser(uid);
        if (user != null) {
            // 查询用户关联的文章信息
            QueryWrapper<UserOtmArticle> uaqw=new QueryWrapper<>();
            uaqw.eq("uid", uid);
            List<UserOtmArticle> userOtmArticles = userOtmArticleMapper.selectList(uaqw);

            // 根据用户关联的文章ID查询文章详情
            if (!userOtmArticles.isEmpty()) {
                QueryWrapper<Article> aqw = new QueryWrapper<>();
                aqw.in("id", userOtmArticles.stream().map(UserOtmArticle::getArticleId).toArray());

                return articleMapper.selectList(aqw);
            }
        }

        return null;
    }

    public  boolean certifyArticle(Integer uid, Integer id){
        List<Article> articleList = this.getUserWithArticles(uid);
        for(Article article : articleList){
            if (Objects.equals(article.getId(), id)){
                return true;
            }
        }
        return false;
    }

    public boolean updateArticle(Integer uid, Article article) {
        if(certifyArticle(uid, article.getId())){
            articleMapper.updateById(article);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean addArticle(Integer uid, Article article) {

        if(article!=null){
            articleMapper.insert(article);
            UserOtmArticle userOtmArticle = new UserOtmArticle();
            userOtmArticle.setUid(uid);
            userOtmArticle.setArticleId(article.getId());
            userOtmArticleMapper.insert(userOtmArticle);
            return true;
        } else{
            return false;
        }
    }

    public boolean deleteArticle(Integer id) {
        try{
            articleMapper.deleteById(id);
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
