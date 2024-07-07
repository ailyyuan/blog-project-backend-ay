package com.whut.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.sql.Time;

@Data
public class Comment {
    private Integer id;
    private String context;
    private Time time;
    private Integer parentId;
    private Integer responseId;

    @TableLogic
    private Integer deleted;
}
