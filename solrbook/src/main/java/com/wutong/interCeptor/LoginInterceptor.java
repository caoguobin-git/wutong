/***********************************************
 * File Name: LoginInterCeptor
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 14 10 2019 下午 1:05
 ***********************************************/

package com.wutong.interCeptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wutong.controller.UserController;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author duochuang
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String usertoken = request.getParameter("usertoken");
        if(UserController.loginUsers.get(usertoken)!=null){
            return true;
        }


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;
        out=response.getWriter();
        Map<String,String> map = new HashMap<>();
        map.put("data", null);
        map.put("message", "未登录或登录已过期");
        map.put("status", "403");
        response.setStatus(403);
        String s = new ObjectMapper().writeValueAsString(map);
        out.println(s);
        out.flush();
        out.close();

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
