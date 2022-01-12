package com.log.search.dto;

import com.log.search.constant.ViewResultCode;
import lombok.Data;

import java.util.Objects;

@Data
public class ViewResult<T> {

    private T data;
    private int codeStatus;
    private String msg;

    private ViewResult(Builder<T> builder) {

        Objects.requireNonNull(builder.code);
        Objects.requireNonNull(builder.message);

        this.codeStatus = builder.code;
        this.data = builder.data;
        this.msg = builder.message;
    }

    public ViewResult() {}

    private ViewResult(int codeStatus, String msg, T data) {
        this.codeStatus = codeStatus;
        this.msg = msg;
        this.data = data;
    }

    public static<T> ViewResult<T> success(T data) {
        return new ViewResult<>(ViewResultCode.OK_CODE, ViewResultCode.OK_CODE_DESC, data);
    }

    public static<T> ViewResult<T> success(String successMsg) {
        return new ViewResult<>(ViewResultCode.OK_CODE, successMsg, null);
    }

    public static<T> ViewResult<T> success(int successCode, String successMsg) {
        return new ViewResult<>(successCode, successMsg, null);
    }

    public static<T> ViewResult<T> success(String successMsg, T data) {
        return new ViewResult<>(ViewResultCode.OK_CODE, successMsg, data);
    }

    public static<T> ViewResult<T> failed() {
        return new ViewResult<>(ViewResultCode.FAIL_CODE, ViewResultCode.FAIL_CODE_DESC, null);
    }

    public static<T> ViewResult<T> failed(String msg) {
        return new ViewResult<>(ViewResultCode.FAIL_CODE, msg, null);
    }
    public static<T> ViewResult<T> failed(int codeStatus, String msg) {
        return new ViewResult<>(codeStatus, msg, null);
    }

    public static<T> ViewResult<T> failed(int codeStatus, String msg, T data) {
        return new ViewResult<>(codeStatus, msg, data);
    }

    @Deprecated
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    @Deprecated
    public static class Builder<T> {
        private T data;
        private int code;
        private String message;

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> failed(Integer codeStatus, String message) {
            this.code = codeStatus;
            this.message = message;
            return this;
        }

        public Builder<T> failed() {
            this.code = ViewResultCode.FAIL_CODE;
            this.message = ViewResultCode.FAIL_CODE_DESC;
            return this;
        }

        public Builder<T> failed(String message) {
            this.code = ViewResultCode.FAIL_CODE;
            this.message = message;
            return this;
        }

        public Builder<T> success() {
            this.code = ViewResultCode.OK_CODE;
            this.message = ViewResultCode.OK_CODE_DESC;
            return this;
        }

        public Builder<T> success(String message) {
            this.code = ViewResultCode.OK_CODE;
            this.message = message;
            return this;
        }
        public ViewResult<T> build() {
            return new ViewResult<>(this);
        }
    }
}
