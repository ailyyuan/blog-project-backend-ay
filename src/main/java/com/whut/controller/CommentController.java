package com.whut.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whut.entity.Article;
import com.whut.entity.Comment;
import com.whut.entity.Result;
import com.whut.service.CommentService;
import com.whut.tools.Tool;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "获取文章所有评论")
    @GetMapping("/getAllArticleComments")
    public Result<Object> getAllArticleComments(Integer articleId) {
        try {
            List<Comment> comments = commentService.getAllArticleComments(articleId);
            return Result.ok().data(comments).message("获取文章评论成功");
        } catch (Exception e) {
            return Result.fail().message("获取文章评论失败: " + e.getMessage());
        }
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("/addComment")
    public Result<Object> addComment(@RequestBody JSONObject json) {
        try {
            System.out.println("addComment");
            Comment comment = JSON.toJavaObject(json.getJSONObject("comment") , Comment.class);
//            Comment comment = new Comment();
//            JSONObject commentJson = json.getJSONObject("comment");
//            comment.setParentId(commentJson.getInteger("parentId"));
//            comment.setResponseId(commentJson.getInteger("responseId"));
//            comment.setContext(commentJson.getString("context"));
//            comment.setTime(Timestamp.valueOf(commentJson.getString("time")));

            Integer userId = Tool.tokenToId();
            Integer articleId = json.getInteger("articleId");
            comment.setId(null);

            System.out.println(userId);
            System.out.println(articleId);
            System.out.println(comment);
            boolean added = commentService.addComment(userId, articleId, comment);
            if (added) {
                return Result.ok().message("评论添加成功").data(true);
            } else {
                return Result.fail().message("评论添加失败，请稍后重试").data(false);
            }
        } catch (Exception e) {
            return Result.fail().message("评论添加失败: " + e.getMessage()).data(false);
        }
    }

    @ApiOperation(value = "删除评论")
    @GetMapping("/deleteComment")
    public Result<Object> deleteComment(Integer commentId) {
        try {
            Integer userId = Tool.tokenToId();
            boolean deleted = commentService.deleteComment(userId, commentId);
            if (deleted) {
                return Result.ok().message("评论删除成功").data(true);
            } else {
                return Result.fail().message("评论删除失败，可能无权限或评论不存在").data(false);
            }
        } catch (Exception e) {
            return Result.fail().message("评论删除失败: " + e.getMessage()).data(false);
        }
    }
}
