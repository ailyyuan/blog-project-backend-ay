package com.whut.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    private boolean success;
    private String message;
    private T data;

    private Result(boolean success) {
        this.success = success;
    }

    public static <T> Result<T> ok() {
        return new Result<>(true);
    }

    public static <T> Result<T> fail() {
        return new Result<>(false);
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }
}


