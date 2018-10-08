package com.dce.business.actions.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dce.business.actions.common.BaseController;
import com.dce.business.common.enums.MessageType;
import com.dce.business.common.result.Result;
import com.dce.business.entity.message.MessageAndNewsDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.message.IMessageService;
import com.dce.business.service.user.IUserService;

/**
 * 新闻公告、消息列表
 * 
 * @author parudy
 * @date 2018年4月4日
 * @version v1.0
 */
@RestController
@RequestMapping("/msg")
public class MessageController extends BaseController {
	private final static Logger logger = Logger.getLogger(MessageController.class);

	@Resource
	private IMessageService messageService;
	@Resource
	private IUserService userService;

	/**
	 * 新闻公告列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<?> flow() {

		logger.info("查询用户消息列表.....");

		Integer userId = getUserId();

		String pageNum = getString("pageNum"); // 当前页
		String rows = getString("rows"); // 每页显示条数

		if (StringUtils.isBlank(pageNum)) {
			pageNum = "1";
		}

		if (StringUtils.isBlank(rows)) {
			rows = "10";
		}

		logger.info("查询用户消息列表:userId=" + userId + ",pageNum=" + pageNum + ",rows=" + rows);

		List<MessageAndNewsDo> messageList = messageService.selectMessageByUseId(userId, Integer.parseInt(pageNum),
				Integer.parseInt(rows));

		List<Map<String, Object>> result = new ArrayList<>();

		if (!CollectionUtils.isEmpty(messageList)) {

			for (MessageAndNewsDo message : messageList) {
				Map<String, Object> map = new HashMap<>();
				map.put("content", message.getContent());
				result.add(map);
			}
		}

		return Result.successResult("查询成功", result);
	}

	@RequestMapping(value = "/sendMsg", method = { RequestMethod.POST, RequestMethod.GET })
	public Result<?> sendMsg() {
		logger.info("发送消息....");
		// 消息内容
		String content = getString("content");
		// 消息接收人(如果是发私信)
		String receive = getString("receive");
		if (StringUtils.isBlank(content)) {
			return Result.failureResult("消息内容不能为空");
		}

		Integer userId = getUserId();
		MessageAndNewsDo msg = new MessageAndNewsDo();
		msg.setContent(content);
		msg.setFrom_uid(userId);

		if (StringUtils.isNotBlank(receive)) {
			UserDo user = userService.userName(StringUtils.trim(receive));
			if (user == null) {
				return Result.failureResult("接收人不存在!");
			} else {
				msg.setTo_uid(user.getId());
				msg.setType(MessageType.TYPE_SXNEWS.getType());
			}
		} else {
			msg.setType(MessageType.FEED_BACK.getType());
		}

		Result<?> result = messageService.addMessage(msg);
		logger.info("发送消息结果:" + JSON.toJSONString(result));
		return result;
	}
}
