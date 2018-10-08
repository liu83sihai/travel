package com.dce.manager.provider;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dce.business.entity.secrety.ManagersDo;
import com.dce.business.service.secrety.IManagerUserService;
/**
 * 自定义验证码校验
 * @author wanglfmf
 * @date 2017年7月19日
 */
public class UsernamePasswordCaptchaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private Logger logger = Logger.getLogger(UsernamePasswordCaptchaAuthenticationFilter.class);

    @Resource
    private IManagerUserService managerUserService;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String mobileCode = request.getParameter("mobileCode");
        String username = obtainUsername(request);
        logger.info("用户" + username + "登录，获取验证码为：" + mobileCode);

        //如果是白名单用户，不校验验证码
        boolean isWhiteList = managerUserService.isWhiteList(username);
        if (isWhiteList) {
            return super.attemptAuthentication(request, response);
        }

        ManagersDo user = managerUserService.getUser(username);

        if (user != null) {
            String mobile = user.getMobile();
            if (StringUtils.isBlank(mobile)) {
                logger.error("没有手机号");
                throw new BadCredentialsException("该用户未绑定手机号码，请联系管理员");
            }
            logger.info("手机号为：" + mobile);
            // 验证手机验证码
            //boolean flag = identifyCodeService.checkIdentifyCode(mobile, mobileCode);
            boolean flag = true;
            if (!flag) {
                throw new BadCredentialsException("手机验证码匹配错误");
            }
        }
        return super.attemptAuthentication(request, response);
    }
}
