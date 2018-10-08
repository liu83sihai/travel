package com.dce.business.service.impl.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.result.Result;
import com.dce.business.dao.message.IMessageDao;
import com.dce.business.entity.message.MessageAndNewsDo;
import com.dce.business.service.message.IMessageService;

@Service("messageService")
public class MessageServiceImpl implements IMessageService {

	private static Logger logger = Logger.getLogger(MessageServiceImpl.class); 
	
	@Resource
	private IMessageDao messageDao;
	
	@Override
	public List<MessageAndNewsDo> selectMessageByUseId(Integer userId,int pageNum,int rows) {
		if(userId == null){
			logger.error("根据用户ID查询用户消息传入的用户id为空!");
			return null;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("to_uid", userId);
		int offset = (pageNum - 1) * rows;
		params.put("offset", offset);
		params.put("rows", rows);
		return messageDao.select(params);
	}

	

	@Override
	public MessageAndNewsDo selectLatestMessageByType(Integer type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("news_type", "1");
		params.put("offset", 0);
		params.put("rows", 1);
		List<MessageAndNewsDo> list = messageDao.select(params);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

	@Override
	public Result<?> addMessage(MessageAndNewsDo msg) {
		int flag = messageDao.insert(msg);
		if(flag > 0){
			return Result.successResult("意见发送成功!");
		}else{
			return Result.failureResult("意见发送失败!");
		}
	}

}
