package com.dce.manager.provider;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

import com.dce.business.entity.secrety.UserInfos;

//import com.hehenian.manager.modules.sys.model.UserInfos;

/**
 * 自定义盐值 
 * @author wanglfmf
 * @date 2017年7月19日
 */ 
public class CustomSaltSource implements SaltSource {

    public Object getSalt(UserDetails user) {
        if (user instanceof UserInfos) {
            return ((UserInfos) user).getSalt();
        }

        return null;
    }

}
