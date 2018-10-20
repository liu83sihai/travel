package com.dce.business.service.impl.banner;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.dce.business.common.util.Constants;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.banner.BannerDo;
import com.dce.business.service.banner.IBannerService;
import com.dce.business.dao.banner.IBannerDao;


@Service("bannerService")
public class BannerServiceImpl implements IBannerService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private IBannerDao  bannerDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public BannerDo getById(int id){
	  return bannerDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Override
	public List<BannerDo> selectBanner(BannerDo example){
		return bannerDao.selectBanner(example);
	}
	
	
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateBannerById(BannerDo newBannerDo){
		logger.debug("updateBanner(BannerDo: "+newBannerDo);
		return  bannerDao.updateBannerById(newBannerDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addBanner(BannerDo newBannerDo){
		logger.debug("addBanner: "+newBannerDo);
		return bannerDao.addBanner(newBannerDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<BannerDo> getBannerPage(Map<String, Object> param, PageDo<BannerDo> page){
		logger.info("----getBannerPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<BannerDo> list =  bannerDao.selectBannerByPage(param);
        page.setModelList(list);
        return page;
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(int id){
		logger.debug("deleteByIdBanner:"+id);
		return  bannerDao.deleteById(id);		
	}

}
