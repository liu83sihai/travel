package com.dce.business.actions.supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.common.result.Result;
import com.dce.business.entity.supplier.SupplierDo;
import com.dce.business.service.supplier.ISupplierService;

/**
 * 代理商信息管理授权业务实现类
 *
 * @author  Fantasy
 * @date    generate
 * @version 1.0.0
 * @pageage com.dce.business.actions.SupplierController.java
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {
	private final static Logger logger = Logger.getLogger(SupplierController.class);
	@Resource
	private ISupplierService supplierService;
	
	 @RequestMapping("/index")
	 public Result<?> list() {
		 logger.info("查询关于湘信.....");
	
		 //List<NewsDo> newsList = newsService.selectNewsList(Integer.parseInt(pageNum), Integer.parseInt(rows));
//		 List<SupplierDo> supplier = supplierService.getSupplierPage(param, page);
		 Map<String, Object> map = new HashMap<>();
//		 map.put("url",supplier.get(0).getUrl());
		 return Result.successResult("查询成功", map);
	 }

}
