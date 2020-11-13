package com.dragon.mapper;

import com.dragon.po.TLsbkRyJbxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
@Mapper
public interface TLsbkRyJbxxMapper {

    @Select("select * from T_LSBK_RY_JBXX")
    List<TLsbkRyJbxx> fetchAllJbxx();

}
