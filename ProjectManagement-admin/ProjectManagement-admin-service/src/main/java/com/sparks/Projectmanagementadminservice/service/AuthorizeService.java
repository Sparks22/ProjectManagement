package com.sparks.Projectmanagementadminservice.service;

import com.sparks.Projectmanagementadmincommon.entity.TAccountInfo;
import com.sparks.Projectmanagementadminservice.domain.service.TAccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: Sparks
 * @Date: 2024/9/1 0:02
 * @Version 1.0
 * @TODO:
 */
@Slf4j
@Service
public class AuthorizeService implements UserDetailsService {

    private TAccountInfoService tAccountInfoService;

    @Autowired
    public void setTAccountInfoService(TAccountInfoService tAccountInfoService) {
        this.tAccountInfoService = tAccountInfoService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.isEmpty()) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        TAccountInfo tAccountInfo = tAccountInfoService.selectAccountByUsername(username);
        if ( tAccountInfo == null) {
            throw new UsernameNotFoundException("用户名不存在或密码错误");
        }
        return User
                .withUsername(tAccountInfo.getUsername())
                .password(tAccountInfo.getPassword())
                .roles("user")
                .build();
    }
}
