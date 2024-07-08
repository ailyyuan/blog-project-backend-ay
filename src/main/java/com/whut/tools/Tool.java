package com.whut.tools;

import cn.dev33.satoken.stp.StpUtil;
import com.whut.entity.Article;
import com.whut.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class Tool {
    public static Integer tokenToId(){
        System.out.println(StpUtil.getTokenInfo().getLoginId());
        return Integer.valueOf((String) StpUtil.getTokenInfo().getLoginId());
    }

}
