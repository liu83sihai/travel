package com.dce.manager.action.upload;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.util.ImageUrlUtil;
import com.dce.manager.action.BaseAction;
import com.dce.manager.file.IFileServerService;

/**
 * 后台UPLOAD  处理类
 * @author zhangyunhmf
 *
 */
@Controller
@RequestMapping("/upload/*")
public class FileUploadController extends BaseAction {
	private static Log log = LogFactory.getLog(FileUploadController.class);

	@Autowired
	private IFileServerService fileServerService;

	
	@Value("#{sysconfig['readImgUrl']}")
	private String imgAccessUrl ;

	

	/**
	 * @Description 富文本控件上传图片
	 * @date 2015年11月22日 下午3:10:28
	 * @param imgFile
	 * @param request
	 * @param response 返回： error 成功错误标记 0 成功， imgPath:文件磁盘保存路径, url 图片访问URL , fileName:上传的原文件名称
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/imgUpload")
	@ResponseBody
	public void imgUpload(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", 1);
		resultMap.put("message", "上传图片失败！");
		try {
			
			MultipartFile imgFile = null; 
			if (request instanceof MultipartHttpServletRequest) {
		            MultipartHttpServletRequest multipartHttpRequest = (MultipartHttpServletRequest) request;
		            Map<String, MultipartFile> multFileMap = multipartHttpRequest.getFileMap();
		            Iterator it = multFileMap.values().iterator();
		            imgFile = (MultipartFile)it.next();
			 }
		
			
			String imgFilePath = fileServerService.saveFileNoThumb(imgFile.getInputStream(), imgFile.getOriginalFilename());

			// 文件保存服务器 和 文件保存数据库

			if (StringUtils.isNotBlank(imgFilePath)) {
				resultMap.put("error", 0);
				resultMap.put("imgPath", imgFilePath);
				resultMap.put("fileName", imgFile.getOriginalFilename());
				resultMap.put("url", ImageUrlUtil.getImagePath(imgAccessUrl,imgFilePath));
				resultMap.put("message","上传图片成功");
				log.info("保存结果成功.....");
				outPrint(response, JSONSerializer.toJSON(resultMap));
			} else {
				log.info("保存结果不成功.....");
				outPrint(response, JSONSerializer.toJSON(resultMap));
			}
		} catch (BusinessException e) {
			log.error(e);
			resultMap.put("message", e);
			outPrint(response, JSONSerializer.toJSON(resultMap));
		} catch (Exception e) {
			log.error(e);
			resultMap.put("message", e);
			outPrint(response, JSONSerializer.toJSON(resultMap));
		}
	}
}
