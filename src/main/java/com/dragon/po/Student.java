package com.dragon.po;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangfq on 2018/6/24.
 */
public class Student extends BaseRowModel {

    @ExcelProperty(value = {"表头1","表头1","表头31"},index = 0)
    private String name;
    @ExcelProperty(value = {"表头1","表头1","表头32"},index = 1)
    private String sfzh;
    @ExcelProperty(value = {"表头3","表头3","表头3"},index = 2)
    private String xf;
    @ExcelProperty(value = {"表头4","表头4","表头4"},index = 3)
    private String zsf;
    @ExcelProperty(value = {"表头5","表头5","表头5"},index = 4, format = "yyyy/MM/dd")
    private Date rq;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getXf() {
        return xf;
    }

    public void setXf(String xf) {
        this.xf = xf;
    }

    public String getZsf() {
        return zsf;
    }

    public void setZsf(String zsf) {
        this.zsf = zsf;
    }

    public Date getRq() {
        return rq;
    }

    public void setRq(Date rq) {
        this.rq = rq;
    }
}
