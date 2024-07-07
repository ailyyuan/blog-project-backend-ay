package com.whut.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Articledynamic{
    private Integer id;
    private Integer eye_view;
    private Integer interesting;
    private Integer boring;
    private Integer comments_count;

    @TableLogic
    private Integer deleted;
}
