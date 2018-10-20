
package com.dce.business.dao.banner;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.supplier.SupplierDo;

import com.dce.business.entity.banner.BannerDo;



public interface IBannerDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public BannerDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<BannerDo> selectBanner(BannerDo example);
	/**
	 * 分面查询
	 * @param param
	 * @return
	 */
	public List<BannerDo> selectBannerByPage(Map<String, Object> param);
	
	/**
	 * 更新
	 */
	public int  updateBannerById(BannerDo newBannerDo);
	
	/**
	 * 新增
	 */
	public int addBanner(BannerDo newBannerDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
