package com.dce.business.service.impl.third;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.common.util.Constants;
import com.dce.business.dao.etherenum.IEthereumTransInfoDao;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.third.IEthereumTransInfoService;

@Service("ethereumTransInfoService")
public class EthereumTransInfoServiceImpl implements IEthereumTransInfoService {

	@Resource
	private IEthereumTransInfoDao  ethTransDao;
	
	/**
	 * 查询以太坊流水
	 * @param params
	 * @return
	 */
	@Override
	public List<Map<String,Object>> queryEthTrans(Map<String,Object> params){
		return ethTransDao.queryEthTrans(params);
	}

	@Override
	public int queryEthTransCount(Map<String, Object> params) {
		return ethTransDao.queryEthTransCount(params);
	}
	
	@Override
	public PageDo<Map<String, Object>> selectEthTransByPage(
			PageDo<Map<String, Object>> page, Map<String, Object> params) {
		
		if(params == null){
			params = new HashMap<String, Object>();
		}
        params.put(Constants.MYBATIS_PAGE, page);
	    List<Map<String,Object>> list = ethTransDao.selectEthTransByPage(params);
	    page.setModelList(list);
		return page;
	}
}
