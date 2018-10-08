/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.business.service.aboutUs;

/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import com.dce.business.entity.aboutUs.AboutusDo;
import com.dce.business.entity.page.PageDo;


public interface IAboutusService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public AboutusDo getById(Integer id);
	
	
	/**
	 * 查询所有
	 */
	public List<AboutusDo> getAllAboutUs();
	
	
	/**
	 * 更新
	 */
	public int updateAboutusById(AboutusDo newAboutusDo);
	
	/**
	 * 新增
	 */
	public int addAboutus(AboutusDo newAboutusDo);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<Map<String, Object>> getAboutusPage(Map<String, Object> param, PageDo<Map<String, Object>> page);
	
	
	/**
	 * 删除
	 */
	public int deleteById(Integer id);
}
