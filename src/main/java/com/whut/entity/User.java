package com.whut.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


import java.util.List;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;




    @TableLogic
    private Integer deleted;

    @TableField(exist = false) // 不映射到数据库表字段
    private List<Article> articles;

    public void mark(){
        this.id=null;
        this.password=null;
    }
}
