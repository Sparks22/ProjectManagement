package com.sparks.Projectmanagementadminservice.domain.service;

import com.sparks.Projectmanagementadmincommon.entity.TAccountInfo;

/**
 * @Author: Sparks
 * @Date: 2024/8/31 23:59
 * @Version 1.0
 * @TODO:
 */
public interface TAccountInfoService {
    TAccountInfo selectAccountByUsername(String username);
}
