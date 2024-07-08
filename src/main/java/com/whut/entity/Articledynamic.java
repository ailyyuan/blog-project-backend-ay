package com.whut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Articledynamic{
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private Integer eye_view;
    private Integer interesting;
    private Integer boring;
    private Integer comments_count;

    @TableLogic
    private Integer deleted;
}
