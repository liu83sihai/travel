package com.dce.business.dao.message;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.message.MessageAndNewsDo;

public interface IMessageDao {

	int insert(MessageAndNewsDo message);
	
	int update(MessageAndNewsDo message);
	
	List<MessageAndNewsDo> select(Map<String,Object> params);
	
	int delete(Integer messageId);
	
	MessageAndNewsDo selectByPrimaryKey(Integer id);
}
