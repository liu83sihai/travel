package com.dce.manager.action.notice;

import java.io.File;
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
import com.dce.business.common.util.ExeclTools;
import com.dce.business.entity.notice.NoticeDo;
import com.dce.business.entity.notice.NoticeDoExample;
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.service.message.INoticeService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;

/**
 * @author huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/ysnotice")
public class YsNoticeController extends BaseAction {
	// 默认多列排序,example: username desc,createTime asc
	// protected static final String DEFAULT_SORT_COLUMNS = null;
	@Resource
	private INoticeService noticeService;

	@Value("#{sysconfig['uploadPath']}")
	private String uploadPath;

	@Value("#{sysconfig['readImgUrl']}")
	private String readImgUrl;

	/**
	 * 去列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "notice/listYsNotice";
	}

	@RequestMapping("/listYsNotice")
	public void listYsNotice(NewPagination<NoticeDo> pagination, ModelMap model, HttpServletResponse response) {

		logger.info("----listYsNotice----");
		try {
			PageDo<NoticeDo> page = PageDoUtil.getPage(pagination);
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
			page = noticeService.getNoticePage(param, page);
			pagination = PageDoUtil.getPageValue(pagination, page);
			outPrint(response, JSONObject.toJSON(pagination));
		} catch (Exception e) {
			logger.error("查询清单异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}

	/**
	 * 新增页面
	 *
	 * @return
	 */
	@RequestMapping("/addYsNotice")
	public String addYsNotice(String id, ModelMap modelMap, HttpServletResponse response) {
		logger.info("----addYsNotice----");
		try {
			if (StringUtils.isNotBlank(id)) {
				NoticeDo noticeDo = noticeService.selectNoticeById(Integer.parseInt(id));
				if (null != noticeDo) {
					modelMap.addAttribute("ysnotice", noticeDo);
				}
			}
			return "notice/addYsNotice";
		} catch (Exception e) {
			logger.error("跳转到数据字典编辑页面异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}

	}

	/**
	 * 删除
	 */
	@RequestMapping("/deleteYsNotice")
	public void deleteYsNotice(String id, HttpServletRequest request, HttpServletResponse response) {
		logger.info("----deleteYsNotice----");
		try {
			if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
				ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
				return;
			}
			int ret = noticeService.deleteNoticeById(Integer.valueOf(id));
			ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
		} catch (Exception e) {
			logger.error("删除公告异常", e);
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
	@RequestMapping("/saveYsNotice")
	@ResponseBody
	public void saveYsNotice(NoticeDo noticeDo, @RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("----saveYsNotice------");

		if (file != null) {
			if (!file.isEmpty()) {
				try {
					// 文件保存路径
					String filePath = request.getSession().getServletContext().getRealPath("/") + "images/"
							+ file.getOriginalFilename();
					System.out.println(filePath);
					// 转存文件
					file.transferTo(new File(filePath));

					noticeDo.setImage(getReadImgUrl(filePath));

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		try {
			Integer id = noticeDo.getId();
			int i = 0;
			if (id != null && id.intValue() > 0) {
				noticeDo.setUpdateDate(new Date());
				i = noticeService.updateNoticeById(noticeDo);
			} else {
				noticeDo.setCreateDate(new Date());
				i = noticeService.addNotice(noticeDo);
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
		logger.info("----end saveYsNotice--------");
	}

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
		 * System.currentTimeMillis(); NoticeDoExample example = new
		 * NoticeDoExample(); String companyName =
		 * getString("searchPolicyName");
		 * 
		 * if(StringUtils.isNotBlank(companyName)){
		 * example.createCriteria().andPolicyNameLike(companyName); } String
		 * managerName = getString("searManagerName");
		 * if(StringUtils.isNotBlank(managerName)){
		 * example.createCriteria().andManagerNameEqualTo(managerName); }
		 * List<NoticeDo> ysnoticeLst = noticeService.selectYsNotice(example);
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
		 * excelHead, ysnoticeLst);
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
