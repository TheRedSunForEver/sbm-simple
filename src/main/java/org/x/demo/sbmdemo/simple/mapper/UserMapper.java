package org.x.demo.sbmdemo.simple.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.x.demo.sbmdemo.simple.model.User;

@Mapper
public interface UserMapper {
    @Select("select * from sys_user where id = #{id}")
    User findById(Long id);
}
