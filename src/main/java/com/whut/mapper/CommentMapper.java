package com.whut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whut.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
