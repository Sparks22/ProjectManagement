package com.sparks.Projectmanagementadminservice.domain.mapper;

import com.sparks.Projectmanagementadmincommon.entity.TAccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: Sparks
 * @Date: 2024/8/31 23:49
 * @Version 1.0
 * @TODO:
 */
@Mapper
public interface TAccountInfoMapper {
    @Select("select * from t_account_info where username = #{username}")
    TAccountInfo selectAccountByUsername(String username);
}
