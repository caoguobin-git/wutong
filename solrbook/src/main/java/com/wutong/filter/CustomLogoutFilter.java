/***********************************************
 * File Name: CustomLogoutFilter
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 13 08 2019 下午 1:31
 ***********************************************/

package com.wutong.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomLogoutFilter extends LogoutFilter {


    public void setCacheManager() {
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        System.out.println("开始过滤...");
//        // 清空相关缓存
        Subject subject = getSubject(request, response);
        subject.logout();
//        if(subject != null && subject.getPrincipal() != null) {
//            Session session = subject.getSession();
//            LoginBean loginBean = (LoginBean) subject.getPrincipal();
//            String staffNo = loginBean.getStaffNo();
//            Serializable sessionId = session.getId();
//            Deque<Serializable> deque = cache.get(staffNo);
//            if(deque != null && deque.contains(sessionId)) {
//                //清除为了校验单一登录留下的缓存
//                deque.remove(sessionId);
//                if(deque.size() == 0) {
//                    cache.remove(staffNo);
//                } else {
//                    cache.put(staffNo, deque);
//                }
//                session.setAttribute(checkOutFlag, null);
//            }
//        }
//        String redirectUrl = getRedirectUrl(request, response, subject);
//        try {
//            subject.logout();
//        } catch (SessionException ise) {
//            ise.printStackTrace();
//        }
        System.out.println("退出登录");
        issueRedirect(request, response, "/adminLoginPage");
//        return false;
        return  false;
    }

}