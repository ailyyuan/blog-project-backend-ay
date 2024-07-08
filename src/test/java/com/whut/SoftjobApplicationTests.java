package com.whut;

import com.whut.entity.Article;
import com.whut.mapper.ArticleMapper;
import com.whut.service.ArticleService;
import com.whut.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SoftjobApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

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

}
