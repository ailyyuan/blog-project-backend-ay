package com.whut;

import com.whut.entity.Comment;
import com.whut.mapper.ArticleMapper;
import com.whut.service.ArticleService;
import com.whut.service.CommentService;
import com.whut.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.sql.Timestamp;

@SpringBootTest
class SoftjobApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void testDoLogin(){
        userService.doLogin("user1","password1");
    }

    @Test
    public void testDeleteUser(){
        userService.deleteUser(4);
    }

    @Test
    public void testGetAllArticleComment() {
        commentService.getAllArticleComments(1);
    }

    @Test
    public void testSearchArticle(){
        articleService.getSearchArticle("标题");
    }

    @Test
    public void testDeleteArticle(){
        System.out.println(articleService.deleteArticle(1,5));
    }

    @Test
    public void testDeleteComment(){
        System.out.println(      commentService.deleteComment(1,4));

    }

    @Test
    public void testAddComment() {
        // Create a fixed Comment object for testing
        Comment comment = new Comment();
        comment.setContext("This is a test comment.");
        comment.setParentId(1); // Assuming parentId needs to be set
        comment.setResponseId(0); // Assuming responseId needs to be set
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        comment.setTime(time);
        // Assuming userId and articleId are known values for testing
        Integer userId = 1;
        Integer articleId = 1;

        boolean added = commentService.addComment(userId, articleId, comment);
        System.out.println(added);
    }

    @Test
    public void testGetArticledynamic(){
        System.out.println(articleService.getArticledynamic(1));
    }

}


