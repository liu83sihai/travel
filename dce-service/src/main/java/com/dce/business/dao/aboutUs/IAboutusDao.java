/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.business.dao.aboutUs;

/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;


import com.dce.business.entity.aboutUs.AboutusDo;

public interface IAboutusDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public AboutusDo getById(Integer id);
	
	/**
	 *根据条件查询列表
	 */
	List<Map<String, Object>> selectAboutUsByPage(Map<String, Object> paraMap);
	
	/**
	 * 更新
	 */
	public int  updateAboutusById(AboutusDo newAboutusDo);
	
	/**
	 * 新增
	 */
	public int addAboutus(AboutusDo newAboutusDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);
	
	
	public List<AboutusDo> getAllAboutUs();

}
