package com.whut.tools;

import cn.dev33.satoken.stp.StpUtil;

public class Tool {
    public static Integer tokenToId(){
        System.out.println(StpUtil.getTokenInfo().getLoginId());
        return Integer.valueOf((String) StpUtil.getTokenInfo().getLoginId());
    }

}
