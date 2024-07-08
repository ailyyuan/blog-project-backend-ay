package com.whut.controller;

import com.whut.entity.Article;
import com.whut.entity.User;
import com.whut.service.ArticleService;
import com.whut.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/getArticle")
    public Article getArticle(Integer articleId){
        return articleService.getArticle(articleId);
    }
    //更新和添加验证,鉴权

    @PostMapping("/updateArticle")
    public boolean updateArticle(@RequestBody Article article){
        Integer uid = Tool.tokenToId();
        return articleService.updateArticle(uid,article);
    }

    @PostMapping("/addArticle")
    public boolean addArticle(@RequestBody Article article){
        Integer uid = Tool.tokenToId();
        article.setId(null);
        return articleService.addArticle(uid, article);
    }
    @GetMapping("/getUserAllSummaryArticles")
    public List<Article> getUserSummaryArticles(Integer uid){
        List<Article> articleList = articleService.getUserWithArticles(uid);
        articleList.forEach(Article::showSummaryMark);
        return articleList;
    }
    @GetMapping("/deleteArticle")
    public boolean deleteArticle(Integer id){
        return articleService.deleteArticle(id);
    }
}
