/***********************************************
 * File Name: UserMapper
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 14 10 2019 上午 9:48
 ***********************************************/
package com.wutong.mapper;

import com.wutong.common.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    UserEntity findByUsername(String username);

    void register(@Param("userId") String userId, @Param("username") String username, @Param("password") String password, @Param("salt") String salt, @Param("role") String role);
}
