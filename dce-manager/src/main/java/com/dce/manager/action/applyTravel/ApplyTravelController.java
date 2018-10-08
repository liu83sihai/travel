package com.dce.manager.action.applyTravel;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.ExeclTools;
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.entity.travel.TravelDo;
import com.dce.business.entity.travel.TravelDoExample;
import com.dce.business.entity.travel.TravelPathDo;
import com.dce.business.service.travel.ITravelApplyService;
import com.dce.business.service.travel.ITravelPathService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;

@Controller
@RequestMapping("/applytravel")
public class ApplyTravelController extends BaseAction {
	@Resource
	private ITravelApplyService travelApplyService;
	
	// 旅游路线
	@Resource
	private ITravelPathService travelPathService;

	/**
	 * 去列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "applytravel/listApplyTravel";
	}

	@RequestMapping("/listApplyTravel")
	public void listApplyTravel(NewPagination<TravelDo> pagination, ModelMap model, HttpServletResponse response) {

		logger.info("----listApplyTravel----");
		try {
			PageDo<TravelDo> page = PageDoUtil.getPage(pagination);
			String companyName = getString("searchPolicyName");
			Map<String, Object> param = new HashMap<String, Object>();

			String name = getString("name");
			System.out.println(name);
			if (StringUtils.isNotBlank(name)) {
				param.put("name", name);
				model.addAttribute("name", name);
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
			page = travelApplyService.getTravelapplyTravelPage(param, page);
			logger.info(page.getModelList().toString());
			pagination = PageDoUtil.getPageValue(pagination, page);
			outPrint(response, JSONObject.toJSON(pagination));
		} catch (Exception e) {
			logger.error("查询清单异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}

	/**
	 * 同意申请
	 */
	@RequestMapping("/agreeApply")
	public void agreeApply(String id, HttpServletRequest request, HttpServletResponse response) {
		logger.info("----agreeApply----");
		try {
			if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
				ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
				return;
			}
			
			int ret = travelApplyService.updateapplyStateById(Integer.valueOf(id));
			ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
		} catch (Exception e) {
			logger.error("审批申请异常", e);
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
		}
	}

	/**
	 * 编辑页面
	 *
	 * @return
	 */
	@RequestMapping("/addApplyTravel")
	public String addApplyTravel(String id, ModelMap modelMap, HttpServletResponse response) {
		logger.info("----addApplyTravel----");
		try {
			if (StringUtils.isNotBlank(id)) {
				TravelDo TravelDo = travelApplyService.selectByPrimaryKey(Integer.valueOf(id));
				if (null != TravelDo) {
					modelMap.addAttribute("applytravel", TravelDo);
				}
			}
			List<TravelPathDo> path = travelPathService.selectAll();
			if(path!=null){
				modelMap.addAttribute("path", path);
			}
			return "applytravel/addApplyTravel";
		} catch (Exception e) {
			logger.error("跳转到数据字典编辑页面异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}

	}

	/**
	 * 保存更新
	 *
	 * @return
	 * @author: huangzlmf
	 * @date: 2015年4月21日 12:49:05
	 */
	@RequestMapping("/saveApplyTravel")
	@ResponseBody
	public void saveApplyTravel(TravelDo TravelDo, HttpServletRequest request, HttpServletResponse response) {
		logger.info("----saveApplyTravel------");
		try {
			Integer id = TravelDo.getId();

			int i = 0;
			if (id != null && id.intValue() > 0) {
				i = travelApplyService.updateapplyTravelById(TravelDo);
			} else {
				TravelDo.setCreatetime(new Date());
				i = travelApplyService.addapplyTravel(TravelDo);
			}

			if (i <= 0) {
				outPrint(response, this.toJSONString(Result.failureResult("操作失败")));
				return;
			}
			outPrint(response, this.toJSONString(Result.successResult("操作成功")));
		} catch (Exception e) {
			logger.error("保存更新失败", e);
			outPrint(response, this.toJSONString(Result.failureResult("操作失败")));
		}
		logger.info("----end saveApplyTravel--------");
	}
	
	/**
	 * 删除单条
	 */
	@RequestMapping("/deleteapplyTravelById")
	public void deleteapplyTravelById(String id, HttpServletRequest request, HttpServletResponse response) {
		logger.info("----deleteapplyTravelById----");
		try {
			if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
				logger.info(id);
				ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
				return;
			}
			int ret = travelApplyService.deleteapplyTravelById(Integer.valueOf(id));
			ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
		} catch (Exception e) {
			logger.error("删除商品异常", e);
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
		}
	}
	/**
	 * 导出数据
	 */
	@RequestMapping("/export")
	public void export(HttpServletResponse response) throws IOException {
		try {
			Long time = System.currentTimeMillis();
			TravelDoExample example = new TravelDoExample();
			
			List<TravelDo> applytravelLst = travelApplyService.selectByExample(example);

			String excelHead = "数据导出";
			String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String fileName = URLEncoder.encode(excelHead + date + ".xls", "utf-8");
			List<String[]> excelheaderList = new ArrayList<String[]>();
			String[] excelheader = { "账号", "等级", "姓名", "性别", "民族", "身份证", "手机号码", "地址", "是否去过", "同行人数", "路线名称", "申请时间", "是否通过" };
			excelheaderList.add(0, excelheader);
			String[] excelData = { "mobile", "displayLevel", "name", "sex", "nation", "identity", "phone",
					"address", "isbeen", "people", "linename","createtime","state"};
			HSSFWorkbook wb = ExeclTools.execlExport(excelheaderList, excelData, excelHead, applytravelLst);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			wb.write(response.getOutputStream());
			time = System.currentTimeMillis() - time;
			logger.info("导出数据，导出耗时(ms)：" + time);
		} catch (Exception e) {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println("下载失败");
			logger.error("导出数据，Excel下载失败", e);
			logger.error("导出数据异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		} finally {
			response.flushBuffer();
		}

	}

}
