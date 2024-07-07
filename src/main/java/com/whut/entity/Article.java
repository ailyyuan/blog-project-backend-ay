package com.whut.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Article {
    private Integer id;
    private String mdText;
    private String title;
    private String titleImg;

    @TableLogic
    private Integer deleted;
}
