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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author duochuang
 */
@Service
@Slf4j
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
        if (!user.isValid()){
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

    @Override
    public List<UserEntity> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public UserEntity findUserById(String usertoken) {
        return userMapper.findUserById(usertoken);
    }

    @Override
    public String updateUserById(String userid, boolean b) {
        log.info(userid);
        log.info(String.valueOf(b));
        int result = userMapper.updateUserById(userid,b);
        if (result>0){
            return "ok";
        }
        return null;
    }


    @Override
    public UserEntity findByUsername(String username) {
        UserEntity userEntity=userMapper.findByUsername(username);
        return userEntity;
    }
}
