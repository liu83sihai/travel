/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.manager.action.goods;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.entity.goods.CTGoodsDo;
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.service.goods.ICTGoodsService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;

/**
 * @author huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseAction {
	// 默认多列排序,example: username desc,createTime asc
	// protected static final String DEFAULT_SORT_COLUMNS = null;
	@Resource
	private ICTGoodsService goodsService;

	@Value("#{sysconfig['uploadPath']}")
	private String uploadPath;
	
	@Value("#{sysconfig['readImgUrl']}")
	private String readImgUrl;
	@Value("#{sysconfig['goodsDetailUrl']}")
	private String goodsDetailUrl;
	
		
	/**
	 * 去列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "goods/listGoods";
	}

	@RequestMapping("/listGoods")
	public void listGoods(NewPagination<CTGoodsDo> pagination, ModelMap model, HttpServletResponse response) {

		logger.info("----listGoods----");
		try {
			PageDo<CTGoodsDo> page = PageDoUtil.getPage(pagination);
			String companyName = getString("searchPolicyName");
			Map<String, Object> param = new HashMap<String, Object>();

			String title = getString("title");
			if (StringUtils.isNotBlank(title)) {
				param.put("title", title);
				model.addAttribute("title", title);
			}
			String startDate = getString("startDate");
			if (StringUtils.isNotBlank(startDate)) {
				param.put("startDate", startDate);
				model.addAttribute("startDate", startDate);
			}

			String endDate = getString("endDate");
			if (StringUtils.isNotBlank(endDate)) {
				param.put("endDate", endDate);
				model.addAttribute("endDate", endDate);
			}
			page = goodsService.getGoodsPage(param, page);
			pagination = PageDoUtil.getPageValue(pagination, page);
			outPrint(response, JSONObject.toJSON(pagination));
		} catch (Exception e) {
			logger.error("查询清单异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}

	/**
	 * 编辑页面
	 *
	 * @return
	 */
	@RequestMapping("/addGoods")
	public String addGoods(String id, ModelMap modelMap, HttpServletResponse response) {
		logger.info("----addGoods----");
		try {
			if (StringUtils.isNotBlank(id)) {
				CTGoodsDo CTGoodsDo = goodsService.selectById(Long.valueOf(id));
				if (null != CTGoodsDo) {
					modelMap.addAttribute("goods", CTGoodsDo);
				}
			}
			return "goods/addGoods";
		} catch (Exception e) {
			logger.error("跳转到数据字典编辑页面异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}

	}

	/**
	 * 逻辑删除
	 */
	@RequestMapping("/deleteGoodsById")
	public void deleteGoodsById(String id, HttpServletRequest request, HttpServletResponse response) {
		logger.info("----deleteGoodsById----");
		try {
			if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
				logger.info("从前端获取的商品id=====》》》"+id);
				ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
				return;
			}
			//先查询出商品
			CTGoodsDo goods = goodsService.selectById(Long.valueOf(id));
			if(goods == null){
				logger.debug("查询出的商品=====》》》"+goods);
				ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
				return;
			}
			//更新商品状态，做逻辑删除
			goods.setStatusRemarks("0");
			int ret = goodsService.updateGoodsById(goods);
			ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
		} catch (Exception e) {
			logger.error("删除商品异常", e);
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
		}
	}

	/**
	 * 保存更新
	 *
	 * @return
	 * @author: huangzlmf
	 * @date: 2015年4月21日 12:49:05
	 */
	@RequestMapping("/saveGoods")
	@ResponseBody
	public void saveGoods(  CTGoodsDo goodsDo, 	
							HttpServletRequest request, 
							HttpServletResponse response) {
		
		logger.info("----saveGoods------");

		try {

			Integer id = goodsDo.getGoodsId();
			//上架时间
			if (goodsDo.getStatus().intValue() == 1) {
				goodsDo.setSaleTime(new Date());
			}
			
			String ff = goodsDo.getPayType();
			
			goodsDo.setDetailLink(goodsDo.getShowDetailUrl(goodsDetailUrl));
			int i = goodsService.updateGoodsById(goodsDo);
			if (i <= 0) {
				outPrint(response, this.toJSONString(Result.failureResult("操作失败")));
				return;
			}
			outPrint(response, this.toJSONString(Result.successResult("操作成功")));
		} catch (Exception e) {
			logger.error("保存更新失败", e);
			outPrint(response, this.toJSONString(Result.failureResult("操作失败")));
		}
		logger.info("----end saveGoods--------");
	}

	/**
	 * 读取图片的url	
	 * @param filePath
	 * @return
	 */
	private String getReadImgUrl(String filePath) {
		StringBuffer sb = new StringBuffer();
		sb.append(readImgUrl);
		sb.append(filePath);
		return sb.toString();
	}

	/**
	 * 导出数据
	 *//*
		 * @RequestMapping("/export") public void export(HttpServletResponse
		 * response) throws IOException { try { Long time =
		 * System.currentTimeMillis(); GoodsExample example = new
		 * GoodsExample(); String companyName = getString("searchPolicyName");
		 * 
		 * if(StringUtils.isNotBlank(companyName)){
		 * example.createCriteria().andPolicyNameLike(companyName); } String
		 * managerName = getString("searManagerName");
		 * if(StringUtils.isNotBlank(managerName)){
		 * example.createCriteria().andManagerNameEqualTo(managerName); }
		 * List<GoodsDo> goodsLst = goodsService.selectGoods(example);
		 * 
		 * String excelHead = "数据导出"; String date = new
		 * SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); String
		 * fileName = URLEncoder.encode(excelHead + date + ".xls", "utf-8");
		 * List<String[]> excelheaderList = new ArrayList<String[]>(); String[]
		 * excelheader = { "保险公司名称", "保险公司简称", "联系人姓名", "联系人手机号码", "跟进单员",
		 * "合作状态", "记录状态" }; excelheaderList.add(0, excelheader); String[]
		 * excelData = { "policyName", "shortName", "contactName",
		 * "contactPhone", "managerName", "partnerStatus", "status" };
		 * HSSFWorkbook wb = ExeclTools.execlExport(excelheaderList, excelData,
		 * excelHead, goodsLst);
		 * response.setContentType("application/vnd.ms-excel;charset=utf-8");
		 * response.setHeader("Content-Disposition", "attachment;filename=" +
		 * fileName); wb.write(response.getOutputStream()); time =
		 * System.currentTimeMillis() - time; logger.info("导出数据，导出耗时(ms)：" +
		 * time); } catch (Exception e) {
		 * response.setContentType("text/html;charset=utf-8");
		 * response.getWriter().println("下载失败"); logger.error("导出数据，Excel下载失败",
		 * e); logger.error("导出数据异常", e); throw new
		 * BusinessException("系统繁忙，请稍后再试"); } finally { response.flushBuffer();
		 * }
		 * 
		 * }
		 */

}