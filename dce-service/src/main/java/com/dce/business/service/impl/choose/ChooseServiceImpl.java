package com.dce.business.service.impl.choose;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.common.util.Constants;
import com.dce.business.dao.choose.ICommonChooseDao;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.choose.ICommonChooseService;

@Service("commonChooseService")
public class ChooseServiceImpl implements ICommonChooseService {
	
	@Resource
	private ICommonChooseDao commonChooseDao;

	@Override
	public PageDo<Map<String, Object>> getChoosePage(Map<String, Object> param,
			PageDo<Map<String, Object>> page) {
		
		param.put(Constants.MYBATIS_PAGE, page);
		
		String chooseType = (String)param.get("chooseType");
		if("GOODS".equalsIgnoreCase(chooseType)){
			List<Map<String, Object>> datas = commonChooseDao.getChooseGoodsPage(param);
			page.setModelList(datas);
		}
		return page;
	}

	
	
}
