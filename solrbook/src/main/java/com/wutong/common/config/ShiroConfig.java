/***********************************************
 * File Name: ShiroConfig
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 12 08 2019 下午 3:44
 ***********************************************/

package com.wutong.common.config;



import com.wutong.filter.CustomLogoutFilter;
import com.wutong.filter.RememberMeFilter;
import com.wutong.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //未授权跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/page/fail.html");
        //登录成功跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/adminIndex");
        shiroFilterFactoryBean.setLoginUrl("/adminLoginPage");
        Map<String, Filter> filterMap=new HashMap<>();
        //配置自定义登出 覆盖 logout 之前默认的LogoutFilter
        filterMap.put("logout", getCustonLogoutFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/admin/logout", "logout");
//        filterChainDefinitionMap.put("/user/logout", "logout");
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/userLoginPage", "anon");
        filterChainDefinitionMap.put("/user/register", "anon");
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        filterChainDefinitionMap.put("/adminIndex", "authc");
        filterChainDefinitionMap.put("/admin/login", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/chat/**", "anon");
        filterChainDefinitionMap.put("/page/**", "anon");
        filterChainDefinitionMap.put("/bootstrap/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/adminLoginPage", "anon");

        filterChainDefinitionMap.put("/book/saveChapterByBookId", "authc");
        filterChainDefinitionMap.put("/book/saveChapterDetailByChapterId", "authc");
        filterChainDefinitionMap.put("/book/savePic", "authc");
        filterChainDefinitionMap.put("/user/register", "authc");
        filterChainDefinitionMap.put("/user/updateUserById", "authc");
        filterChainDefinitionMap.put("/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器
     * 由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(3);
        return hashedCredentialsMatcher;
    }
    @Bean
    public UserRealm myShiroRealm() {
        UserRealm myShiroRealm = new UserRealm();
        //使用加密
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public FilterRegistrationBean registrationBean(CustomLogoutFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public RememberMeFilter rememberMeFilter() {
        return new RememberMeFilter();
    }

    @Bean
    public CustomLogoutFilter getCustonLogoutFilter() {
        CustomLogoutFilter logoutFilter= new CustomLogoutFilter();
        logoutFilter.setRedirectUrl("/adminLoginPage");
        return logoutFilter;
    }


//    /**
//     * 注册全局异常处理
//     * @return
//     */
//    @Bean(name = "exceptionHandler")
//    public HandlerExceptionResolver handlerExceptionResolver() {
//        return new ExceptionHandler();
//    }
}
