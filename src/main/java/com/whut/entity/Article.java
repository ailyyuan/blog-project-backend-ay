package com.whut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Article {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String mdText;
    private String title;
    private String titleImg;

    @TableLogic
    private Integer deleted;

    public void showSummaryMark(){
        this.mdText=null;
    }
}
