package com.dragon.service.impl;

import com.dragon.mapper.BmAllMapper;
import com.dragon.po.BmAll;
import com.dragon.po.Student;
import com.dragon.service.BmAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/6.
 */
@Service
public class BmAllServiceImpl implements BmAllService {

    @Autowired
    private BmAllMapper bmAllMapper;

    @Override
    public List<BmAll> fetctBmAll() {
        return bmAllMapper.getBmAllList();
    }

    @Override
    public BmAll getBmAllById(String bh) {
        return bmAllMapper.getBmAllById(bh);
    }

    @Override
    public void insertPo(Student student) {
        bmAllMapper.insert(student);
    }

    @Override
    public List<Student> fetchAllStu() {
        return bmAllMapper.selectAllStu();
    }
}
