package com.dce.business.entity.page;


import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.Pagination;

@SuppressWarnings({"rawtypes", "unchecked"})
public class PageDoUtil {
	
	/**
	 * 渠道转过来的分页请求信息放到查询分页中
	 * @param pagination
	 * @return
	 */
	public static PageDo getPage(NewPagination pagination){
		PageDo page = new PageDo();
		page.setCurrentPage((long)pagination.getPage());
		page.setPageSize((long)pagination.getRows());
		return page;
	}
	
	/**
	 * 转移分页数据
	 * @param page
	 * @param pagination
	 * @return pagination
	 */
	public static NewPagination getPageValue(NewPagination pagination,PageDo page){
		pagination.setPage(Integer.parseInt(page.getCurrentPage()+""));
		pagination.setRows(Integer.parseInt(page.getPageSize()+""));
		pagination.setDatas(page.getModelList());
		int total = Integer.parseInt(page.getTotalCount()+"");
		pagination.setTotal(total);
		return pagination;
	}
	
	/**
	 * 渠道转过来的分页请求信息放到查询分页中
	 * @auther liminglmf,huangzl
	 * @date 2015年5月7日
	 * @param pagination
	 * @return
	 */
	public static PageDo getPage(Pagination pagination){
		PageDo page = new PageDo();
		page.setCurrentPage((long)pagination.getPage());
		page.setPageSize((long)pagination.getPageSize());
		return page;
	}

	/**
	 * 转移分页数据
	 * @auther liminglmf,huangzl
	 * @date 2015年5月7日
	 * @param page
	 * @param pagination
	 * @return pagination
	 */
	public static Pagination getPageValue(Pagination pagination,PageDo page){
		pagination.setPage(Integer.parseInt(page.getCurrentPage()+""));
		pagination.setPageSize(page.getPageSize().intValue());
		pagination.setRows(page.getModelList());
		int total = Integer.parseInt(page.getTotalCount()+"");
		pagination.setTotal(total);
		return pagination;
	}

}
