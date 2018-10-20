package com.dce.business.service.banner;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.banner.BannerDo;

public interface IBannerService{

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
	 * 更新
	 */
	public int  updateBannerById(BannerDo newBannerDo);
	
	/**
	 * 新增
	 */
	public int addBanner(BannerDo newBannerDo);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<BannerDo> getBannerPage(Map<String, Object> param, PageDo<BannerDo> page);
	
	
	/**
	 * 删除
	 */
	public int deleteById(int id);
}
