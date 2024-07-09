package com.whut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String context;
    private Timestamp time;
    private Integer parentId;
    private Integer responseId;

    @TableLogic
    private Integer deleted;
}
