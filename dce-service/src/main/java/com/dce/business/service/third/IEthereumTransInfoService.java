package com.dce.business.service.third;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.page.PageDo;

public interface IEthereumTransInfoService {

	List<Map<String,Object>> queryEthTrans(Map<String,Object> params);
	
	int queryEthTransCount(Map<String, Object> params);
	
	PageDo<Map<String, Object>> selectEthTransByPage(PageDo<Map<String, Object>> page, Map<String, Object> params);
}
