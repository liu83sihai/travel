package com.dce.manager.action.news;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.dao.message.INewsDao;
import com.dce.business.entity.message.NewsDo;
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.service.message.INewsService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.file.IFileServerService;
import com.dce.manager.util.ResponseUtils;

@Controller
@RequestMapping("/ysnews")
public class YsNewsController extends BaseAction {
	@Resource
	private INewsService ysNewsService;
	
	@Value("#{sysconfig['readImgUrl']}")
	private String readImgUrl;
	
	@Autowired
	private IFileServerService fileServerService;

	/**
	 * 去列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "news/listYsNews";
	}

	@RequestMapping("/listYsNews")
	public void listYsNews(NewPagination<INewsDao> pagination, ModelMap model, HttpServletResponse response) {

		logger.info("----listYsNews----");
		try {
			PageDo<NewsDo> page = PageDoUtil.getPage(pagination);
			String companyName = getString("searchPolicyName");
			String title = getString("title");
			String startDate = getString("startDate");
			String endDate = getString("endDate");
			//String updateName = getString("updateName");
			//String createName = getString("createName");
			Map<String, Object> param = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(companyName)) {
				param.put("policyName", companyName);
				model.addAttribute("searchPolicyName", companyName);
			}
			String managerName = getString("searManagerName");
			if (StringUtils.isNotBlank(managerName)) {
				param.put("managerName", managerName);
				model.addAttribute("searManagerName", managerName);
			}

			if (StringUtils.isNotBlank(startDate)) {
				param.put("startDate", startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				param.put("endDate", endDate);
			}
			if (StringUtils.isNotBlank(title)) {
				param.put("title", title);
			}
			/*if (StringUtils.isNotBlank(updateName)) {
				param.put("updateName", updateName);
			}
			if (StringUtils.isNotBlank(createName)) {
				param.put("createName", createName);
			}*/

			page = ysNewsService.getYsNewsPage(param, page);
			List<NewsDo> list = page.getModelList();

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
	@RequestMapping("/addYsNews")
	public String addYsNews(String id, ModelMap modelMap, HttpServletResponse response) {
		logger.info("----addYsNews----");
		try {
			if (StringUtils.isNotBlank(id)) {
				NewsDo ysnewsDo = ysNewsService.selectNewsDetail(Integer.valueOf(id));

				if (null != ysnewsDo) {
					modelMap.addAttribute("ysnews", ysnewsDo);
				}
			}
			return "news/addYsNews";
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
	@RequestMapping("/saveYsNews")
	@ResponseBody
	public void saveYsNews(NewsDo ysnewsDo, @RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {

		logger.info("----saveYsNews---------" + file);
		String filePath="";
		if (file != null) {
			if (!file.isEmpty()) {
				try {
					// 文件保存路径
					String imgFilePath = fileServerService.saveFileNoThumb(file.getInputStream(), file.getOriginalFilename());
					// 存数据库
					ysnewsDo.setImage(this.getReadImgUrl(imgFilePath,readImgUrl));
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}

			}
		}
		try {
			Integer id = ysnewsDo.getId();
			//String img = ysnewsDo.getImage();

			Assert.hasText(ysnewsDo.getTitle(), "标题不能为空");
			Assert.hasText(ysnewsDo.getContent(), "地址不能为空");
			//Assert.hasText(ysnewsDo.getCreateName(), "创建人不能为空");

			System.err.println("id---->>" + id);
			int i = 0;
			if (id != null && id.intValue() > 0) {
				//ysnewsDo.setUpdateDate(new Date());
				//Assert.hasText(ysnewsDo.getUpdateName(), "修改人不能为空");
				i = ysNewsService.updateYsNewsById(ysnewsDo);
			} else {
				ysnewsDo.setCreateDate(new Date());
				i = ysNewsService.addYsNews(ysnewsDo);
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
		logger.info("----end saveYsNews--------");
	}
	
	/**
	 * IO流读取图片
	 * @param filePath
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/img", method = {RequestMethod.GET})
	
	public void saveGoods(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ServletOutputStream out = null;
		FileInputStream ips = null;
		try {
			String filePath = this.getString("filePath");
			ips = new FileInputStream(filePath);
			response.setContentType("image/png");
			out = response.getOutputStream();
			//读取文件流
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = ips.read(buffer)) != -1){
				out.write(buffer,0,len);
			}
			out.flush();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			out.close();
			ips.close();
		}
		 
	 }
	
	
	
	
/*	*//**
	 * 图片压缩
	 * @param oldpath
	 * @param newspath
	 * @param withd
	 * @param height
	 *//*
	public void Picture_Compression(String oldpath,String newspath,int withd,int height){
		
		try {
			
			Thumbnails.of(oldpath).size(withd,height).toFile(newspath);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	/**
	 * 删除
	 */
	@RequestMapping("/deleteYsNews")
	public void deleteYsNotice(String id, HttpServletRequest request, HttpServletResponse response) {
		logger.info("----deleteYsNews----");
		try {
			if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
				ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
				return;
			}
			int ret = ysNewsService.deleteById(Integer.valueOf(id));
			ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
		} catch (Exception e) {
			logger.error("删除新闻异常", e);
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
		}
	}

	
}
