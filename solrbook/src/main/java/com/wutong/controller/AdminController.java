/***********************************************
 * File Name: AdminController
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 20 10 2019 上午 10:16
 ***********************************************/

package com.wutong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "/adminPages/{path}")
    public String adminPages(@PathVariable("path")String path){
        System.out.println("admin-pages/"+path);
        return "admin-pages/"+path;
    }
}
