
package com.dce.business.dao.userCard;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.supplier.SupplierDo;

import com.dce.business.entity.userCard.UserCardDo;



public interface IUserCardDao {

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
	 * 分面查询
	 * @param param
	 * @return
	 */
	public List<UserCardDo> selectUserCardByPage(Map<String, Object> param);
	
	/**
	 * 更新
	 */
	public int  updateUserCardById(UserCardDo newUserCardDo);
	
	/**
	 * 新增
	 */
	public int addUserCard(UserCardDo newUserCardDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
