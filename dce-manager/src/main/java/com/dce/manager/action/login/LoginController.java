package com.dce.manager.action.login;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.entity.secrety.UserInfos;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.secrety.IManagerUserService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.common.IResult;
import com.dce.manager.common.ResultSupport;
import com.dce.manager.util.ResponseUtils;

@Controller
@RequestMapping("/auth/*")
public class LoginController extends BaseAction {

	@Resource
	private IManagerUserService managerUserService;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {

		String name = principal.getName();

		model.addAttribute("username", name);
		model.addAttribute("message", "Spring Security Custom Form example");
		return "/login/hello";

	}

	@RequestMapping("accessDenied")
	public String accessDenied() {
		return "/login/accessDenied";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "/login/login";
	}

	/**
	 * 登录成功
	 */
	@RequestMapping(value = "/loginsuccess", method = RequestMethod.GET)
	public void loginSuccess(HttpServletResponse response) {
		IResult<?> result = ResultSupport.buildSuccessResult(null);

		outPrint(response, toJSONString(result));
	}

	/**
	 * 登录失败
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public void loginerror(HttpServletResponse response) {
		String errorMsg = "登录失败";
		// 获取异常信息
		Object exception = getRequest().getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if (exception instanceof BadCredentialsException) {
			errorMsg = ((BadCredentialsException) exception).getMessage();
		}
		IResult<?> result = ResultSupport.buildErrorResult(errorMsg);
		outPrint(response, toJSONString(result));
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, ModelMap model) {
		removeUserInfosWhenLogout();
		request.getSession().invalidate();
		return "/login/login";

	}

	@ResponseBody
	@RequestMapping("resetLoginUserPwd")
	public void resetLoginUserPwd(HttpServletRequest request, HttpServletResponse response) {
		try {
			String oldPassword = getString("oldPassword");
			String newPassword = getString("newPassword");
			String newPasswordAgain = getString("newPasswordAgain");
			// 旧密码、新密码都不能为空，确认密码必须等于新密码
			if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)
					|| StringUtils.isBlank(newPasswordAgain) || !newPassword.equals(newPasswordAgain)) {
				ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
				return;
			}
			UserInfos currentUser = getUserInfos();
			int userId = currentUser.getUserId();
			int ret = managerUserService.resetCurrentUserPwd(userId, oldPassword, newPassword);
			if (ret > 0) {

				ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
			} else {

				ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
			}
		} catch (Exception e) {
			logger.error("用户重置密码异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}

	/**
	 * 发送注册验证码
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sendMobileCode")
	public void sendMobileCode(HttpServletRequest request, HttpServletResponse response) {
		// try {
		// String username = request.getParameter("username");
		//
		// logger.info("发送验证码, username:" + username);
		//
		// if (StringUtils.isBlank(username)) {
		// outPrint(response, toJSONString(ResultSupport.buildResult("2",
		// "用户名不能为空")));
		// return;
		// }
		//
		// //拿手机号
		// ManagersDo user = managerUserService.getUser(username);
		// if (user == null) {
		// logger.info("用户不能为空");
		// outPrint(response, toJSONString("用户不能为空"));
		// }
		// String mobile = user.getMobile();
		// if (StringUtils.isBlank(mobile)) { //用户没有手机号
		// outPrint(response,
		// toJSONString(ResultSupport.buildErrorResult("该用户未绑定手机号码，请联系管理员")));
		// return;
		// }
		// logger.info("手机号：" + mobile);
		// // 调用短信发送接口
		// String token = DigestUtils.md5Hex(mobile + Constants.SMS_PASS_KEY);
		// String ip = GetReqParams.getIpAddr(request);
		// String template = "sms_template_hehuayidai.ftl";
		// IResult<Map<String, Object>> result =
		// identifyCodeService.sendIdentifyCode(token, ip, mobile,
		// SmsType.CL.name(), template);
		// if (result.isSuccess()) {
		// String endNum = mobile.substring(mobile.length() - 4,
		// mobile.length());
		// logger.info("手机尾号：" + endNum);
		// result.setResultMessage("短信验证码已发到您尾号为(" + endNum + "）的手机，请注意查收！");
		// } else if (!"-888".equals(result.getResultCode())) {
		// logger.info(result.getResultMessage());
		// result.setResultMessage("发送失败");
		// }
		// outPrint(response, toJSONString(result));
		// } catch (Exception e) {
		// logger.error("发送验证码错误", e);
		// outPrint(response,
		// toJSONString(ResultSupport.buildErrorResult("系统繁忙，请稍后再试")));
		// }
	}
}
