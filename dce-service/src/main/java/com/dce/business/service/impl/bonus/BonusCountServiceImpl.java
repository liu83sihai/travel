package com.dce.business.service.impl.bonus;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.common.util.PageUtil;
import com.dce.business.dao.bonus.IBonusCountDao;
import com.dce.business.service.bonus.IBonusCountService;


@Service("bonusCountService")
public class BonusCountServiceImpl implements IBonusCountService {

	@Resource
	private IBonusCountDao bonusCountDao;
	
	@Override
	public List<Map<String, Object>> selectBonus(Map<String, Object> paraMap,int startPage, int pageSize) {
		int startRow = PageUtil.getEndRow(startPage, pageSize);
		return bonusCountDao.selectBonus(paraMap,startRow,pageSize);
	}

}
