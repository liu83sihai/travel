package com.dce.business.common.filter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;

public class TokenInterceptor extends HandlerInterceptorAdapter  {
	Logger logger = Logger.getLogger(TokenInterceptor.class);

    static String splitFlag = "_";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.add();
                if (needSaveSession) {
                    Random random = new Random();
                    String uuid = UUID.randomUUID().toString().replace(splitFlag, String.valueOf(random.nextInt(100000)));
                    String tokenValue = String.valueOf(System.currentTimeMillis());
                    request.setAttribute("token", uuid + splitFlag + tokenValue);
                    // session 中 token 的key 每次都是变化的[适应浏览器 打开多个带有token的页面不会有覆盖session的key]
                    request.getSession(true).setAttribute(uuid, tokenValue);
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        logger.warn("please don't repeat submit,url:" + request.getServletPath());
                        return false;
                    }
                    String clinetToken = request.getParameter("token");
                    if (clinetToken != null && clinetToken.indexOf(splitFlag) > -1) {
                        request.getSession(true).removeAttribute(clinetToken.split("_")[0]);
                    }
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 判断是否是重复提交
     *
     * @param request
     * @return
     */
    private boolean isRepeatSubmit(HttpServletRequest request) {

        String clinetToken = request.getParameter("token");

        if (clinetToken == null) {
            return true;
        }
        if (clinetToken.indexOf(splitFlag) > -1) {
            String uuid = clinetToken.split("_")[0];
            String token = clinetToken.split("_")[1];
            String serverToken = (String) request.getSession(true).getAttribute(uuid);
            if (serverToken == null) {
                return true;
            }
            if (!serverToken.equals(token)) {
                return true;
            }
        }

        return false;
    }
}
