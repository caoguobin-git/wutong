/***********************************************
 * File Name: UserRealm
 * @author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 07 11 2019 下午 12:25
 ***********************************************/

package com.wutong.realm;


import com.wutong.common.entity.UserEntity;
import com.wutong.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("授权管理");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        UserEntity user = userService.findByUsername(username);
        if(user == null){
            //没找到账号
            throw new UnknownAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                //salt = username+salt
                ByteSource.Util.bytes(user.getSalt()),
                getName());
        return authenticationInfo;
    }
}
