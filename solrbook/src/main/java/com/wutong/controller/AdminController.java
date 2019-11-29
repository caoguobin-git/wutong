/***********************************************
 * File Name: AdminController
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 20 10 2019 上午 10:16
 ***********************************************/

package com.wutong.controller;

import com.wutong.common.entity.UserEntity;
import com.wutong.common.vo.JsonResult;
import com.wutong.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/adminPages/{path}")
    public String adminPages(@PathVariable("path")String path){
        System.out.println("admin-pages/"+path);
        return "admin-pages/"+path;
    }
    @PostMapping(value = "/login")
    @ResponseBody
    public JsonResult userLogin(@RequestParam("username")String username, @RequestParam("password")String password){

        log.info(username+password);
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken userToken = new UsernamePasswordToken(username, password);
        UserEntity user = userService.findByUsername(username);

        //执行登录方法,用捕捉异常去判断是否登录成功
        try {
            userToken.setRememberMe(true);
            subject.login(userToken);
            if (!"admin".equalsIgnoreCase(user.getRole())){
                return new JsonResult("403", "角色没有权限", "没有权限访问");
            }
            return new JsonResult(user);
        } catch (UnknownAccountException e) {
            //用户名不存在
            return new JsonResult("403", "账号或密码错误", "登录失败");
        } catch (IncorrectCredentialsException e) {
            //密码错误
            return new JsonResult("403", "账号或密码错误", "登录失败");
        }
    }
}
