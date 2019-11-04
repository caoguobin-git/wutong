/***********************************************
 * File Name: UserController
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 12 10 2019 上午 9:21
 ***********************************************/

package com.wutong.controller;

import com.google.common.base.Strings;
import com.wutong.common.entity.UserEntity;
import com.wutong.common.vo.JsonResult;
import com.wutong.mapper.UserMapper;
import com.wutong.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    public static Map<String,UserEntity> loginUsers=new HashMap<>();

    @RequestMapping(value = "/login")
    @ResponseBody
    public JsonResult userLogin(String username,String password){
        log.info(username);
        log.info(password);
        UserEntity userEntity= userService.userLogin(username,password);
        if (userEntity==null){
            return new JsonResult("403","账号或密码错误",null);
        }
        loginUsers.put(userEntity.getUserId(),userEntity);
        Map<String,String> result = new HashMap<>();
        result.put("usertoken", userEntity.getUserId());
        return new JsonResult(result);
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public JsonResult logout(String usertoken){
        loginUsers.remove(usertoken);
        return new JsonResult("logout ok");
    }


    @RequestMapping(value = "/adminLogin")
    @ResponseBody
    public JsonResult adminLogin(){
        return null;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult register(String username, String password,String role) {
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password) ||Strings.isNullOrEmpty(role)) {
            return new JsonResult("401", "参数错误", "参数错误");
        }
        String result = userService.register(username, password,role);
        if (result.equalsIgnoreCase("ok")) {
            return new JsonResult("注册成功,请返回登录");
        } else {
            return new JsonResult("402", "注册失败", result);
        }
    }

    @RequestMapping(value = "/getAllUsers")
    @ResponseBody
    public JsonResult getAllUsers(){
        List<UserEntity> users= userService.getAllUsers();
        return new JsonResult(users);
    }

    @RequestMapping(value = "/updateUserById",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateUserById(String userId,boolean valid){
        String result = userService.updateUserById(userId,!valid);
        if ("ok".equals(result)){
            return new JsonResult("状态修改成功");
        }
        return new JsonResult("405","状态修改失败",null);
    }
}
