package com.dragon.service.impl;

import com.dragon.mapper.TLsbkRyJbxxMapper;
import com.dragon.po.TLsbkRyJbxx;
import com.dragon.service.TLsbkRyJbxxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
@Service
public class TLsbkRyJbxxServiceImpl implements TLsbkRyJbxxService {

    @Autowired
    private TLsbkRyJbxxMapper tLsbkRyJbxxMapper;

    @Override
    public List<TLsbkRyJbxx> fetchAll() {
        return tLsbkRyJbxxMapper.fetchAllJbxx();
    }
}
