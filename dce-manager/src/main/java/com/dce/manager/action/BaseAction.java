package com.dce.manager.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.dce.business.entity.secrety.UserInfos;
import com.dce.manager.util.DataEncrypt;
import com.dce.manager.util.StringUtil;

public class BaseAction {
    public final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    /*liminglong add date:2015-05-08 */
    public static final String EXECUTE_STATUS = "executeStatus";
    public static final String VALUES = "values";
    public static final String EXECUTE_SUCCESS = "1";
    public static final String EXECUTE_FAILURE = "0";
    /*liminglong add date:2015-05-08 */
	
	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new SpringDateConvert());
	}
	

    public String getBaseBath() {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(0, path.indexOf("/WEB-INF")) + "/upload/images";
        return path;
    }

    public HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    public int getInt(String name) {
        return getInt(name, -1);
    }

    public int getInt(String name, int defaultValue) {
        return NumberUtils.toInt(getRequest().getParameter(name), defaultValue);
    }

    public final String getIpAddr() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "";
        }

        String ipaddr = request.getHeader("Cdn-Src-Ip");
        if (StringUtils.isNotBlank(ipaddr)) {
            return ipaddr;
        }

        ipaddr = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(ipaddr)) {
            return ipaddr;
        }

        ipaddr = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (StringUtils.isBlank(ipaddr)) {
            ipaddr = request.getRemoteAddr();
        }
        return ipaddr;
    }

    protected void outPrint(HttpServletResponse response, Object result) {
        try {
            response.setCharacterEncoding("utf-8");

            PrintWriter out = response.getWriter();
            out.print(result.toString());
        } catch (IOException e) {
            logger.error("ioexception:", e);
        }
    }

    public int getUserId() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            UserInfos user = (UserInfos) auth.getPrincipal();
            return user.getUserId();
        }
        return 0;
    }

    public String getUserName() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            UserInfos user = (UserInfos) auth.getPrincipal();
            return user.getUsername();
        }
        return "";
    }

    public UserInfos getUserInfos() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            UserInfos user = (UserInfos) auth.getPrincipal();
            return user;
        }
        return null;
    }

    public void removeUserInfosWhenLogout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public boolean hasRoleAdmin() {
        boolean result = false;
//        UserInfos user = this.getUserInfos();
//        if (user != null) {
//            Collection<GrantedAuthority> grantedAuthorityList = user.getAuthorities();
//            for (GrantedAuthority authority : grantedAuthorityList) {
//                if (RoleConstants.ROLE_LOANDKADMIN.equals(authority.getAuthority())) {
//                    result = true;
//                    break;
//                }
//            }
//        }
        System.out.println("判断用户是否有admin权限：" + result);
        return result;
    }

    /**
     * 
     * 判断当前用户是否能访问本url
     *
     * zhangyunhmf
     *
     */
    protected boolean canAccess(HttpServletRequest request, String url) {

//        List<ResourcesDo> resourcesList = (List<ResourcesDo>) request.getSession().getAttribute("resourcesList");
//        if (resourcesList == null || resourcesList.isEmpty()) {
//            return false;
//        }

        boolean result = false;
        logger.info("开始判断用户是否有" + url + "的权限");
//        for (ResourcesDo resources : resourcesList) {
//            if (url.equals(resources.getResourceStr())) {
//                //这里通过角色去获取用户的权限,再通过权限去获取资源
//                result = true;
//                break;
//            }
//        }
        return result;
    }

    /**
     * 从request中获取参数值，去掉前后空格
     * @param request
     * @param name 参数名称
     * @return
     */
    protected String getString(HttpServletRequest request, String name) {
        String paraVal = request.getParameter(name);
        if (StringUtils.isNotBlank(paraVal)) {
            paraVal = StringUtil.FilteSqlInAndScript(paraVal).trim();
        }

        return paraVal;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public String getString(String name) {
        return getString(name, null);
    }

    public String getString(String name, String defaultValue) {
        return Objects.toString(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 转成JSON字符串
     * @param object
     * @return
     */
    protected String toJSONString(Object object) {
        if (object == null) {
            return "";
        }

        return JSON.toJSONString(object);
    }

    /** 
     * 从request中获取参数，如果不为空，添加到map中
     * @param map
     * @param key
     * @param requestParamName  
     */
    protected void setRequestParams(Map<String, Object> map, String key, String requestParamName) {
        String value = getString(requestParamName);
        if (StringUtils.isNotBlank(value)) {
            map.put(key, value);
        }
    }

    protected void setSearchStrParam(Map<String, Object> map) {
        String searchStr = getString("searchStr");
        if (StringUtils.isNotBlank(searchStr)) {
            searchStr = searchStr.trim();
            boolean result = searchStr.matches("[0-9]+");
            if (result) {
                if (searchStr.length() > 3) {
                    map.put("mobile", searchStr);
                }
            } else {
                if (searchStr.startsWith("D")) {
                    map.put("orderCode", searchStr);
                } else {
                    map.put("realName", DataEncrypt.encrypt(searchStr));
                }
            }
        }
    }
    
    /**
     * 是否有权限
     *
     * @param rolename 角色
     * @return
     */
    public boolean hasRoleCheckStep(String rolename) {
        boolean result = false;
//        UserInfos user = this.getUserInfos();
//        if (user == null) {
//            return false;
//        }
//        Collection<GrantedAuthority> grantedAuthorityList = user.getAuthorities();
//        for (GrantedAuthority authority : grantedAuthorityList) {
//            if (rolename.equals(authority.getAuthority())) {
//                return true;
//            }
//        }
        return result;
    }
    
	/**
	 * 读取图片的url	
	 * @param filePath
	 * @return
	 */
	public String getReadImgUrl(String filePath,String readImgUrl) {
		StringBuffer sb = new StringBuffer();
		sb.append(readImgUrl);
		sb.append(filePath);
		return sb.toString();
	}
}
