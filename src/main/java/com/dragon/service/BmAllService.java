package com.dragon.service;

import com.dragon.po.BmAll;
import com.dragon.po.Student;

import java.util.List;

/**
 * Created by Administrator on 2018/3/6.
 */
public interface BmAllService {

    List<BmAll> fetctBmAll();

    BmAll getBmAllById(String bh);

    void insertPo(Student student);

    List<Student> fetchAllStu();

}
