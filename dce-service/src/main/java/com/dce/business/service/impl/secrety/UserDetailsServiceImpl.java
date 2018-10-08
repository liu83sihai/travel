package com.dce.business.service.impl.secrety;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dce.business.entity.secrety.AuthoritiesDo;
import com.dce.business.entity.secrety.ManagersDo;
import com.dce.business.entity.secrety.UserInfos;
import com.dce.business.service.secrety.IAuthoritiesService;
import com.dce.business.service.secrety.IManagerUserService;

//先换成UserInfos，如果可以后面全部统一换成modules
//import com.hehenian.manager.modules.sys.model.UserInfos;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private IManagerUserService managerUserService;
    @Resource
    private IAuthoritiesService authoritiesService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ManagersDo data = managerUserService.getUser(userName);

        List<AuthoritiesDo> authorities = authoritiesService.getGrantedAuthority(data.getId());
        List<GrantedAuthority> results = new ArrayList<GrantedAuthority>();
        for (AuthoritiesDo data1 : authorities) {
            GrantedAuthority ga = new AuthoritiesDo(data1.getName());
            results.add(ga);
        }
        UserInfos details = new UserInfos(data.getId(), data.getUsername(), data.getNickname(), data.getPassword(), data.getSalt(),data.getDeptId(), true, true, true,
                true, results);
        return details;
    }

    
}
