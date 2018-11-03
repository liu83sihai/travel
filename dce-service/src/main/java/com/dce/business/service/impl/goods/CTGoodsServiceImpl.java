package com.dce.business.service.impl.goods;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dce.business.common.util.Constants;
import com.dce.business.dao.goods.ICTGoodsDao;
import com.dce.business.dao.goods.ICTUserAddressDao;
import com.dce.business.dao.order.IOrderDao;
import com.dce.business.entity.goods.CTGoodsDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.account.IPayService;
import com.dce.business.service.goods.ICTGoodsService;

@Service("ctGoodsService")
public class CTGoodsServiceImpl implements ICTGoodsService {

	private final static Logger logger = Logger.getLogger(CTGoodsServiceImpl.class);

	@Resource
	private ICTGoodsDao ctGoodsDao;
	@Resource
	private ICTUserAddressDao ctUserAddressDao;
	@Resource
	private IOrderDao orderDao;

	@Resource
	private IAccountService accountService;
	@Resource
	private IPayService payService;

	@Override
	public List<CTGoodsDo> selectByPage(int pageNum, int pageCount) {
		Map<String, Object> params = new HashMap<String, Object>();
		pageNum = pageNum > 0 ? pageNum - 1 : pageNum;
		int offset = pageNum * pageCount;
		int rows = pageCount;
		params.put("offset", offset);
		params.put("rows", rows);
		return ctGoodsDao.selectByPage4App(params);
	}

	@Override
	public CTGoodsDo selectById(Long goodsId) {
		return ctGoodsDao.selectByPrimaryKey(goodsId);
	}


	@Override
	public int insertSelectiveService(CTGoodsDo goods) {
		goods.setCreateTime(new Date());
		return ctGoodsDao.insertSelective(goods) ;
		
	}

	@Override
	public int deleteGoodsService(Integer goodsid) {
		logger.info("----deleteGoodsService----");
		int result = 0;
		if(goodsid!=null&&goodsid!=0){
			 result =ctGoodsDao.deleteByPrimaryKey(goodsid);
		}
		
		return result;
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<CTGoodsDo> getGoodsPage(Map<String, Object> param, PageDo<CTGoodsDo> page){
		logger.info("----getGoodsPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<CTGoodsDo> list =  ctGoodsDao.queryListPage(param);
        page.setModelList(list);
        return page;
	}
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateGoodsById(CTGoodsDo newGoodsDo){
		if (newGoodsDo.getGoodsId() == null || newGoodsDo.getGoodsId().intValue() == 0) {
			return this.insertSelectiveService(newGoodsDo);
		}
		logger.debug("updateGoods(GoodsDo: "+newGoodsDo);
		return  ctGoodsDao.updateByPrimaryKeySelective(newGoodsDo);		
	}

}
