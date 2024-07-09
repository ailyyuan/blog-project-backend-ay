package com.whut.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.whut.entity.Article;
import com.whut.entity.Comment;
import com.whut.service.CommentService;
import com.whut.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/getAllArticleComments")
    public List<Comment> getAllArticleComments(Integer articleId){
        System.out.println("getAllArticleComments");
        System.out.println(articleId);
        return commentService.getAllArticleComments(articleId);
    }

    @PostMapping("/addComment")
    public boolean addComment(@RequestBody JSONObject json){
        System.out.println("addComment"+json);
        Comment comment = new Comment();
        JSONObject commentJson = json.getJSONObject("comment");
        comment.setParentId((Integer) commentJson.get("parentId"));
        comment.setResponseId((Integer) commentJson.get("responseId"));
        comment.setContext((String) commentJson.get("context"));
        comment.setTime(Timestamp.valueOf((String) commentJson.get("time")));
        Integer userId = Tool.tokenToId();
        Integer articleId =(Integer) json.get("articleId");
        comment.setId(null);
        System.out.println(comment);
        return commentService.addComment(userId,articleId,comment);
    }
    @GetMapping("/deleteComment")
    public boolean deleteComment(Integer commentId){
        Integer uid = Tool.tokenToId();
        System.out.println(uid);
        System.out.println(commentId);
        return commentService.deleteComment(uid,commentId);
    }
}
