package com.dce.manager.provider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 登录限制
 * @author wanglfmf
 * @date 2017年7月19日
 */
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {
    //
//    @Resource
//    private ILoanDictService loanDictService;
//    @Resource
//    private ICacheService cacheService;
//    @Resource
//    private ILoanUserService loanUserService;
//
//
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //统计错误次数
//        String key = "manager_errorCount_" + authentication.getName();
//        int maxCount = getMaxErrorCount();
        //输错密码次数校验
//            int count = getErrorCount(key);
//            if (count >= maxCount) {
//                throw new BusinessException(ErrorConstant.USER_LOCKING);
//            }

        //密码过期校验
//            checkPassword(authentication.getName());

        Authentication auth = super.authenticate(authentication);

        //登录成功，清除失败缓存
//            cacheService.delete(key);

        return auth;

    }

    /**
     * 获取用户连续输错密码次数
     * @param key
     * @return
     */
    private Integer getErrorCount(String key) {
        return null;
//        Integer count = 0;
//        String str = cacheService.getString(key);
//        if (StringUtils.isNotBlank(str)) {
//            count = Integer.valueOf(str);
//        }
//
//        return count;
    }

    /**
     * 校验密码是否过期
     * @param username
     */
    private void checkPassword(String username) {
//    	UserInfos data = (UserInfos)this.getUserDetailsService().loadUserByUsername(username);
//
//        IResult<?> result = loanUserService.checkPasswordExpire(Long.valueOf(data.getUserId()), Platform.MANAGER.type());
//
//        if (!result.isSuccess()) {//密码失效
//            logger.info("用户：" + username + "密码已过期");
//            throw new BusinessException(result.getResultMessage());
//        }
    }

    private Integer getMaxErrorCount() {
//        //拿到数据库配置的最大错误次数
//        String errorCount = loanDictService.getLoanDictDtl("lockConfig", "errorCount").getRemark();
//        if (errorCount == null) { //如果没配置，默认为5
//            errorCount = "5";
//        }
//
//        return Integer.valueOf(errorCount);
        return null;
    }

    /**
     * 登录失败，计数加一
     * @param key
     * @return
     */
    private int count(String key) {
        Integer count = 0;
//        String existErrorCount = cacheService.getString(key);
//        if (StringUtils.isNotBlank(existErrorCount)) {
//            count = Integer.valueOf(existErrorCount);
//        }
//
//        //锁定时间
//        int lockTime = Integer.parseInt(loanDictService.getLoanDictDtl("lockConfig", "lockTime").getRemark());
//
//        count++;
//        cacheService.setString(key, count + "", lockTime);

        return count;
    }

}
