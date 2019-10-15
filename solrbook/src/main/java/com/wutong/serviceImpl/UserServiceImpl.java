/***********************************************
 * File Name: UserServiceImpl
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 14 10 2019 上午 9:25
 ***********************************************/

package com.wutong.serviceImpl;

import com.wutong.common.entity.UserEntity;
import com.wutong.common.util.MD5HashUtils;
import com.wutong.mapper.UserMapper;
import com.wutong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author duochuang
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String register(String username, String password, String role) {
        if (findByUsername(username)!=null){
            return "用户已存在";
        }


        String salt = MD5HashUtils.getSalt();
        String userId = MD5HashUtils.getRandomUUID();
        StringBuffer nick = new StringBuffer();
        for (int i = 0; i < 32; i +=4){
            nick.append(userId.charAt(i));
        }
        password = MD5HashUtils.getMD5HashWithSalt(password, salt);
        userMapper.register(userId, username, password, salt,role);
        return "ok";
    }

    @Override
    public UserEntity userLogin(String username, String password) {
        UserEntity user = findByUsername(username);
        if (user==null){
            return null;
        }
        String salt = user.getSalt();
        String pssword= MD5HashUtils.getMD5HashWithSalt(password, salt);
        if (pssword.equals(user.getPassword())){
            user.setPassword(null);
            user.setSalt(null);
            return user;
        }else {
            return null;
        }
    }


    public UserEntity findByUsername(String username) {
        UserEntity userEntity=userMapper.findByUsername(username);
        return userEntity;
    }
}
