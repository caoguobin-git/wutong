/***********************************************
 * File Name: RememberMeFilter
 * @author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 07 11 2019 下午 12:46
 ***********************************************/

package com.wutong.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RememberMeFilter extends FormAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject=getSubject(request,response);
        if(!subject.isAuthenticated() && subject.isRemembered()){
            if(subject.getSession().getAttribute("user")==null &&subject.getPrincipal()!=null){
                subject.getSession().setAttribute("user",subject.getPrincipal());
            }

        }

        return subject.isAuthenticated() || subject.isRemembered();
    }
}
