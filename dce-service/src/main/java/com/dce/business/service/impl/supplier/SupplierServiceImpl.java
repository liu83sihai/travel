package com.dce.business.service.impl.supplier;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.dce.business.common.util.Constants;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.supplier.SupplierDo;
import com.dce.business.service.supplier.ISupplierService;
import com.dce.business.dao.supplier.ISupplierDao;


@Service("supplierService")
public class SupplierServiceImpl implements ISupplierService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private ISupplierDao  supplierDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public SupplierDo getById(int id){
	  return supplierDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Override
	public List<SupplierDo> selectSupplier(SupplierDo example){
		return supplierDao.selectSupplier(example);
	}
	
	
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateSupplierById(SupplierDo newSupplierDo){
		logger.debug("updateSupplier(SupplierDo: "+newSupplierDo);
		return  supplierDao.updateSupplierById(newSupplierDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addSupplier(SupplierDo newSupplierDo){
		logger.debug("addSupplier: "+newSupplierDo);
		return supplierDao.addSupplier(newSupplierDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<SupplierDo> getSupplierPage(Map<String, Object> param, PageDo<SupplierDo> page){
		logger.info("----getSupplierPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<SupplierDo> list =  supplierDao.selectSupplierByPage(param);
        page.setModelList(list);
        return page;
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(int id){
		logger.debug("deleteByIdSupplier:"+id);
		return  supplierDao.deleteById(id);		
	}

}
