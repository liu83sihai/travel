package com.dce.business.dao.choose;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ICommonChooseDao {

	/**
	 * 选择商品框
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getChooseGoodsPage(Map<String, Object> param);
	

}
