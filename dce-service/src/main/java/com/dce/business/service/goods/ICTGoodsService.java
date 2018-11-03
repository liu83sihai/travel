package com.dce.business.service.goods;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.goods.CTGoodsDo;
import com.dce.business.entity.page.PageDo;

public interface ICTGoodsService {

	public List<CTGoodsDo> selectByPage(int pageNum,int pageCount);
	
	
	public CTGoodsDo selectById(Long id);
	
	//选择性添加商品
	int insertSelectiveService(CTGoodsDo goods);
	
	//id条件删除商品
	int deleteGoodsService(Integer goodsid);
	
	/**
	 * 购买商品
	 * @param goodsId 商品id
	 * @param qyt 购买数量
	 * @return
	 */
	/*public Result<?> buyGoods(OrderDo order,Integer addressId);*/

	/**
	 * page 翻页查询
	 * 
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<CTGoodsDo> getGoodsPage(Map<String, Object> param, PageDo<CTGoodsDo> page);

	/**
	 * 更新
	 * @param cTGoodsDo
	 * @return
	 */
	public int updateGoodsById(CTGoodsDo cTGoodsDo);
}
