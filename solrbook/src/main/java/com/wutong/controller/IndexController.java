/***********************************************
 * File Name: IndexController
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 13 10 2019 21:29
 ***********************************************/

package com.wutong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/")
@Controller
public class IndexController {

    /**
     * 微信服务号校验
     * @return
     */
    @GetMapping("/2dydwc8rOJ.txt")
    @ResponseBody
    public String MP_verify_esLsssssUY04(){
        return  "a1ed89feebcec9911c1e46f4c3e18fe9";
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/uploadBook")
    public String uploadBook(){
        return "upload-book";
    }

    @RequestMapping(value = "/content")
    public String content(){
        return "contentPage";
    }

    @RequestMapping(value = "/adminIndex")
    public String adminIndex(){
        return "adminPage";
    }

    @RequestMapping(value = "/adminLoginPage")
    public String adminLoginPage(){
        return "admin-login";
    }
}
