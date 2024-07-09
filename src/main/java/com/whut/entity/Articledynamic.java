package com.whut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Articledynamic{
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private Integer eyeView;
    private Integer interesting;
    private Integer boring;
    private Integer commentsCount;

    @TableLogic
    private Integer deleted;
}
