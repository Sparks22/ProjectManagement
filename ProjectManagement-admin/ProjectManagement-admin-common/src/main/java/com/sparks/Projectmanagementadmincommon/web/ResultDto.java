package com.sparks.Projectmanagementadmincommon.web;

import java.util.Objects;

/**
 * @Author: Sparks
 * @Date: 2024/8/27 19:29
 * @Version 1.0
 * @TODO:
 */
public class ResultDto<T> extends BaseDto{
    private String code;
    private String msg;
    private long total;
    private T data;

    private ResultDto() {
    }

    private ResultDto(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultDto(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResultDto(String code, String msg, long total, T data) {
        this.code = code;
        this.msg = msg;
        this.total = total;
        this.data = data;
    }
    public static <T> ResultDto<T> success(){
        return new ResultDto("0000","操作成功！");
    }
    public static <T> ResultDto<T> success(String msg){
        return new ResultDto("0000",msg);
    }
    public static <T> ResultDto<T> success(T data){
        return new ResultDto("0000","操作成功！",data);
    }
    public static <T> ResultDto<T> success(String msg,T data){
        return new ResultDto("0000",msg,data);
    }
    public static <T> ResultDto<T> success(T data,long total){
        return new ResultDto("0000","操作成功！",total,data);
    }
    public static <T> ResultDto<T> success(String msg,T data, long total){
        return new ResultDto("0000",msg,total,data);
    }
    public static <T> ResultDto<T> success(String code,String msg,T data){
        return new ResultDto(code,msg,data);
    }
    public static <T> ResultDto<T> success(String code,String msg){
        return new ResultDto(code,msg);
    }
    public static <T> ResultDto<T> fail(String msg){
        return new ResultDto("9999",msg);
    }
    public static <T> ResultDto<T> fail(String code, String msg){
        return new ResultDto(code,msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultDto<?> resultDto = (ResultDto<?>) o;
        return total == resultDto.total && Objects.equals(code, resultDto.code) && Objects.equals(msg, resultDto.msg) && Objects.equals(data, resultDto.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, total, data);
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", total=" + total +
                ", data=" + data +
                '}';
    }
}
