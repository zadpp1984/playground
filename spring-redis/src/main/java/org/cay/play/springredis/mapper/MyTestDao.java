package org.cay.play.springredis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.cay.play.springredis.bean.Mytest;

@Mapper
public interface MyTestDao {

    @Select("select * from mytest where id = #{id}")
    public Mytest getMytestById(Long id);

    @Update("<script> " +
            "update mytest " +
            "<trim prefix='set' suffixOverrides=','>" +
            "<if test=\"name!=null and name!=''\">  name=#{name}, </if>" +
            "<if test=\"age!=null  and age!=0  \">  age=#{age}, </if>" +
            "<if test=\"sex!=null  and sex!='' \">  sex=#{sex}, </if>" +
            "</trim>" +
            "where id=#{id}" +
            "</script> "
    )
    int updateById(Mytest mytest);
}
