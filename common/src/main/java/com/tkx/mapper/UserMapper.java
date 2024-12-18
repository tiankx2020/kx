package com.tkx.mapper;

import com.tkx.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author tkx
 * @Date 2024 12 09 00 53
 **/
@Mapper
public interface UserMapper {

    public List<User> getList();
}
