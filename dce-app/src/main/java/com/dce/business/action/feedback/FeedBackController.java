package com.dce.business.action.feedback;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.entity.feedback.FeedBackDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.feedback.IFeedBackService;
import com.dce.business.service.user.IUserService;

@RestController
@RequestMapping("/feedBack")
public class FeedBackController extends BaseController {

	private final static Logger logger = Logger.getLogger(FeedBackController.class);

	@Resource
	private IFeedBackService feedBackService;

	// 用户信息
	@Resource
	private IUserService userService;

	/** 
	 * @api {POST} /feedBack/addFeedBack.do 用户反馈
	 * @apiName addFeedBack
	 * @apiGroup feedBack 
	 * @apiVersion 1.0.0 
	 * @apiDescription 用户反馈 
	 *  
	 * @apiParam  {java.lang.Integer}  userId 当前用户ID,
	 * @apiParam  {java.lang.String}  feedBackContent 反馈内容
	 *  
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} model 返回成功信息
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "result": {
	 *	"model": {
	 *		
	 * 	},
	 *	  "status": {
	 *	    "code": 200,
	 *	    "msg": "请求成功"
	 *	  }
	 *	}
	 */ 
	@RequestMapping(value = "/addFeedBack", method = RequestMethod.POST)
	public Result<?> addApply() {
		logger.info("用户反馈....");

		FeedBackDo feedback = new FeedBackDo();

		// 获取前台传过来的反馈信息
		String feedBackContent = getString("feedBackContent") == null ? "" : getString("feedBackContent");

		// 获取用户id
		Integer userId = getUserId();
		UserDo user = new UserDo();
		user = userService.getUser(userId);
		if (user == null) {
			return Result.failureResult("用户不存在,请重新登陆");
		} else {

			feedback.setFeedbackcontent(feedBackContent);
			feedback.setUserid(userId);

			Result<?> result = feedBackService.feedBack(feedback);

			return result;
		}
	}

}
