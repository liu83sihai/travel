package com.dce.business.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSON;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.token.TokenUtil;

public class LoginFilter extends OncePerRequestFilter {
    private final static Logger logger = Logger.getLogger(LoginFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            String uri = request.getRequestURI();
            HttpSession session = request.getSession();
            String contextPath = session.getServletContext().getContextPath();

            if (uri.equals(contextPath) || uri.equals(contextPath + "/") || isNotFilterUri(uri)) {
                filterChain.doFilter(request, response);
                return;
            }

            String ts = request.getParameter(TokenUtil.TS);
            String sign = request.getParameter(TokenUtil.SIGN);
            String userId = request.getParameter(TokenUtil.USER_ID);

            logger.info("token校验， uri:" + uri + "; ts:" + ts + "; sign:" + sign + "; userId:" + userId);
            if (StringUtils.isBlank(userId) || StringUtils.isBlank(ts) || StringUtils.isBlank(sign)) {
                print(response, Result.failureCode, "用户未登录");
                return;
            }
            //token判断，不加uri加签
            if (!TokenUtil.checkToken("", Integer.valueOf(userId), ts, sign)) {
                //未登录
                print(response, "-1", "登录已过期，请重新登录");
                return;
            }

            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException | BusinessException e) {
            logger.error("业务异常：", e);
            print(response, Result.failureCode, e.getMessage());
        } catch (Exception e) {
            logger.error("系统异常：", e);
            print(response, Result.failureCode, "系统繁忙，请稍后再试");
        }
    }

    /**
     * 不需要拦截的uri 
     * @param uri
     * @return  
     */
    private boolean isNotFilterUri(String uri) {
        List<String> list = Arrays.asList(new String[] { "/user/login", 
        												 "/user/reg", 
        												 "/bank/toBindBankCard",
        												 "/mall/",
        												 "/travelPath/",
        												 "/aboutus/",
        												 "/news/",
        												 "/notice/",
        												 "/activity/addGood",
        												 "/activity/delGood",
        												 "/activity/index",
        												 "/supplier/index",
        												 "/order/notifyUrl" });
        for (String str : list) {
            if (uri.indexOf(str) != -1) {
                return true;
            }
        }
        return false;
    }

    private void print(HttpServletResponse response, String code, String msg) throws IOException {
        Result<?> result = Result.result(code, msg, null);
        //response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(JSON.toJSONString(result));
        pw.close();
    }
}
