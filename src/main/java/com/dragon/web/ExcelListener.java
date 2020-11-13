package com.dragon.web;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangfq on 2019/3/20.
 */
public class ExcelListener extends AnalysisEventListener {

    private List<Object> datas = new ArrayList<Object>();

    @Override
    public void invoke(Object object, AnalysisContext analysisContext) {
        System.out.println("当前行："+analysisContext.getCurrentRowNum());
        System.out.println(object);
        datas.add(object);//数据存储到list，供批量处理，或后续自己业务逻辑处理。
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
