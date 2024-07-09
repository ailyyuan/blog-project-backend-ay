package com.whut.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.whut.entity.Article;
import com.whut.entity.Articledynamic;
import com.whut.service.ArticleService;
import com.whut.service.UserService;
import com.whut.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    @GetMapping("/tsetToken")
    public String tsetToken(Integer articleId){
        System.out.println(StpUtil.getLoginId());
        return "token验证成功";
    }

    @GetMapping("/getArticle")
    public Article getArticle(Integer articleId){
        System.out.println("请求发起uid： "+StpUtil.getLoginId());
        return articleService.getArticle(articleId);
    }
    //更新和添加验证,鉴权

    @PostMapping("/addArticle")
    public boolean addArticle(@RequestBody Article article){
        System.out.println(StpUtil.getLoginId());
        Integer uid = Tool.tokenToId();
        article.setId(null);
        return articleService.addArticle(uid, article);
    }
    @GetMapping("/deleteArticle")
    public boolean deleteArticle(Integer id){
        Integer uid = Tool.tokenToId();
        return articleService.deleteArticle(uid, id);
    }
    @PostMapping("/updateArticle")
    public boolean updateArticle(@RequestBody Article article){
        System.out.println("updateArticle");
        Integer uid = Tool.tokenToId();
        System.out.println(uid);
        System.out.println("update Article" + uid);
        return articleService.updateArticle(uid,article);
    }
    @GetMapping("/getUserAllSummaryArticles")
    public List<Article> getUserSummaryArticles(Integer uid){
        List<Article> articleList = articleService.getUserWithArticles(uid);
        articleList.forEach(Article::showSummaryMark);
        return articleList;
    }

    @GetMapping("/searchArticle")
    public List<Article>  getSearchArticle(String search) {
        List<Article> articleList = articleService.getSearchArticle(search);
        articleList.forEach(Article::showSummaryMark);
        return articleList;
    }
    @GetMapping("/getArticledynamic")
    public Articledynamic getArticledynamic(Integer articleId){
        return articleService.getArticledynamic(articleId);
    }
}
