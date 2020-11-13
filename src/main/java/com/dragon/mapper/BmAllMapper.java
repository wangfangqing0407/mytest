package com.dragon.mapper;

import com.dragon.po.BmAll;
import com.dragon.po.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2018/3/6.
 */
@Mapper
public interface BmAllMapper {

    @Select("SELECT * FROM BM_ALL WHERE BH = #{bh}")
    BmAll getBmAllById(String bh);

    @Select("SELECT * FROM BM_ALL")
    List<BmAll> getBmAllList();

    @Insert("insert into T_STUDENT (name, sfzh, xf, zsf) values(#{name}, #{sfzh}, #{xf}, #{zsf})")
    int insert(Student student);

    //@Select("select t.name, t.sfzh, t.xf, t.zsf, to_char(t.rq, 'yyyy/MM/dd') as rq from T_STUDENT t")
    @Select("select t.* from T_STUDENT t")
    List<Student> selectAllStu();
}
