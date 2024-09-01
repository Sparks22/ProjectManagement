package com.sparks.Projectmanagementadminservice.domain.service.Impl;

import com.sparks.Projectmanagementadmincommon.entity.TAccountInfo;
import com.sparks.Projectmanagementadminservice.domain.mapper.TAccountInfoMapper;
import com.sparks.Projectmanagementadminservice.domain.service.TAccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Sparks
 * @Date: 2024/8/31 23:59
 * @Version 1.0
 * @TODO:
 */
@Slf4j
@Service
public class TAccountInfoServiceImpl implements TAccountInfoService {

    private TAccountInfoMapper tAccountInfoMapper;

    @Autowired
    public TAccountInfoServiceImpl(TAccountInfoMapper tAccountInfoMapper) {
        this.tAccountInfoMapper = tAccountInfoMapper;
    }

    @Override
    public TAccountInfo selectAccountByUsername(String username) {
        return tAccountInfoMapper.selectAccountByUsername(username);
    }
}
