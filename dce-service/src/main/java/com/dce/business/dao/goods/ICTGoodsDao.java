package com.dce.business.dao.goods;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.goods.CTGoodsDo;

public interface ICTGoodsDao {

	//查询商品
	List<CTGoodsDo> selectByPage4App(Map<String,Object> params);
	
	//添加商品
	CTGoodsDo selectByPrimaryKey(Long goodsId);
	
	//选择性修改商品
	int updateByPrimaryKeySelective(CTGoodsDo goods);
	
	//选择性添加商品
	int insertSelective(CTGoodsDo goods);
	
	//id条件删除商品
	int  deleteByPrimaryKey(Integer goodsid);

	/**
	 * 修改订购数量
	 * @param _goods
	 */
   int updateBookQty(CTGoodsDo _goods);

   /**
    * page  query
    * @param param
    * @return
    */
	List<CTGoodsDo> queryListPage(Map<String, Object> param);
}
