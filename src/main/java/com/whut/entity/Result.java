package com.whut.entity;

import lombok.Data;

@Data
public class Result<T> {
    private boolean success;
    private String message;
    private T data;

    // 私有构造方法，通过静态方法创建实例
    private Result(boolean success) {
        this.success = success;
    }

    // 静态方法，返回成功的 Result 实例
    public static <T> Result<T> ok() {
        return new Result<>(true);
    }

    // 静态方法，返回失败的 Result 实例
    public static <T> Result<T> fail() {
        return new Result<>(false);
    }

    // 设置消息，并返回当前实例，支持链式调用
    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    // 设置数据，并返回当前实例，支持链式调用
    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    // 获取是否成功的标志
    public boolean isSuccess() {
        return success;
    }

    // 获取消息内容
    public String getMessage() {
        return message;
    }

    // 获取数据
    public T getData() {
        return data;
    }
}

