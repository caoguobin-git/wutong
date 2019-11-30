/***********************************************
 * File Name: CustomLogoutFilter
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 13 08 2019 下午 1:31
 ***********************************************/

package com.wutong.filter;

import com.wutong.realm.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Slf4j
public class CustomLogoutFilter extends LogoutFilter {


    public void setCacheManager() {
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //登出操作 清除缓存  subject.logout() 可以自动清理缓存信息, 这些代码是可以省略的  这里只是做个笔记 表示这种方式也可以清除
        Subject subject = getSubject(request,response);
//        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
//        UserRealm shiroRealm = (UserRealm) securityManager.getRealms().iterator().next();
//        PrincipalCollection principals = subject.getPrincipals();
//        shiroRealm.clearCache(principals);

        //登出
        subject.logout();

        //获取登出后重定向到的地址
        String redirectUrl = getRedirectUrl(request,response,subject);
        //重定向
        log.info(redirectUrl);
        issueRedirect(request,response,redirectUrl);
        return false;
    }

}