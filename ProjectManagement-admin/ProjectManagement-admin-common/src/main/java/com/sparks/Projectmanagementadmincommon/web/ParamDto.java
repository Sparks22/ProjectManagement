package com.sparks.Projectmanagementadmincommon.web;

import java.util.Objects;

/**
 * @Author: Sparks
 * @Date: 2024/8/27 22:00
 * @Version 1.0
 * @TODO:
 */
public class ParamDto<T> extends BaseDto{
    private int pageNum;
    private int pageSize;
    private String sort;
    private String currAuth;
    private T param;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCurrAuth() {
        return currAuth;
    }

    public void setCurrAuth(String currAuth) {
        this.currAuth = currAuth;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParamDto<?> paramDto = (ParamDto<?>) o;
        return pageNum == paramDto.pageNum && pageSize == paramDto.pageSize && Objects.equals(sort, paramDto.sort) && Objects.equals(currAuth, paramDto.currAuth) && Objects.equals(param, paramDto.param);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNum, pageSize, sort, currAuth, param);
    }

    @Override
    public String toString() {
        return "ParamDto{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sort='" + sort + '\'' +
                ", currAuth='" + currAuth + '\'' +
                ", param=" + param +
                '}';
    }
}
