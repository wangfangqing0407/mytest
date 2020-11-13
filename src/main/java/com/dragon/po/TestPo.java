package com.dragon.po;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * Created by Administrator on 2018/3/14.
 */
public class TestPo extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String name;
    @ExcelProperty(index = 1)
    private String phone;
    @ExcelProperty(index = 2)
    private String sfzh;
    @ExcelProperty(index = 3)
    private String hjdxz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getHjdxz() {
        return hjdxz;
    }

    public void setHjdxz(String hjdxz) {
        this.hjdxz = hjdxz;
    }
}
