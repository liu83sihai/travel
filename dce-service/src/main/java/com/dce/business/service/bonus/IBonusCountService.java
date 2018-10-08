package com.dce.business.service.bonus;

import java.util.List;
import java.util.Map;

public interface IBonusCountService {

	/**
	 * 查询奖金发放情况
	 * @param paraMap
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	List<Map<String, Object>> selectBonus(Map<String, Object> paraMap,int startPage, int pageSize);

}
