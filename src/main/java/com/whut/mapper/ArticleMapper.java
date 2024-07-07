package com.whut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whut.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
