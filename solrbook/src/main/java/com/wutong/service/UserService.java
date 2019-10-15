/***********************************************
 * File Name: UserService
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 14 10 2019 上午 9:25
 ***********************************************/
package com.wutong.service;

import com.wutong.common.entity.UserEntity;

public interface UserService {
    String register(String username, String password, String role);

    UserEntity userLogin(String username, String password);
}
