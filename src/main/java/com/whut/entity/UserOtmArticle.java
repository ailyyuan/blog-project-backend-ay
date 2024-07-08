package com.whut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class UserOtmArticle {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private Integer uid;
    private Integer articleId;

    @TableLogic
    private Integer deleted;
}
