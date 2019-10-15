/***********************************************
 * File Name: UserEntity
 * Author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 14 10 2019 上午 9:47
 ***********************************************/

package com.wutong.common.entity;

public class UserEntity {
    private String userId;
    private String username;
    private String password;
    private String salt;
    private String role;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
