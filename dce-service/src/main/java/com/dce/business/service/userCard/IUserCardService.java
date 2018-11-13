package com.dce.business.service.userCard;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.userCard.UserCardDo;

public interface IUserCardService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public UserCardDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<UserCardDo> selectUserCard(UserCardDo example);
	
	/**
	 * 更新
	 */
	public int  updateUserCardById(UserCardDo newUserCardDo);
	
	/**
	 * 激活用户卡号
	 * @param userCardDo
	 * @return  1：激活成功
	 *          2：当前订单已激活
	 *          3:第三方订单激活卡失败
	 *          4:必要参数不存在
	 */
	public int activeUserCard(UserCardDo userCardDo);
	/**
	 * 新增
	 */
	public int addUserCard(UserCardDo newUserCardDo);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<UserCardDo> getUserCardPage(Map<String, Object> param, PageDo<UserCardDo> page);
	
	
	/**
	 * 删除
	 */
	public int deleteById(int id);
}
