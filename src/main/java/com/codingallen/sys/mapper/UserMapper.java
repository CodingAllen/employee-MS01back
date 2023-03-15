package com.codingallen.sys.mapper;

import com.codingallen.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CodingAllen
 * @since 2023-03-14
 */
public interface UserMapper extends BaseMapper<User> {
    public List<String> getRoleNameByUserId(Integer userId);



}
