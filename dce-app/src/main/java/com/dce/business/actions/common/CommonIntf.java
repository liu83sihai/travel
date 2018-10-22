package com.dce.business.actions.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.dce.business.common.result.Result;


/**
 * 通用接口
 * 
 * @author baishui
 * QQ/微信：1052202173
 * 2017年6月22日 下午10:36:03
 *
 * com.yisi.business.common.CommonIntf.java
 */
@Controller
@RequestMapping("/commonIntf")
public class CommonIntf extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CommonIntf.class);

	 /** 
     *  
     * @api {get} /rest/commonIntf/sendMessage 获取短信验证码
     * @apiName sendMessage  
     * @apiGroup Common 
     * @apiVersion 1.0.0 
     * @apiDescription 根据手机号码返回短信验证码
     *  
     * @apiParam {String} mobile 手机号码  
     *  
     * @apiUse RETURN_MESSAGE
     * @apiSuccess {String} model 验证码
     * 
     * @apiSuccessExample Success-Response: 
     *  HTTP/1.1 200 OK 
     * {
     * 	"model": "760428",
     *	"success": true,
     *	"errorMessage": null,
     *	"resultCode": 200
	 * }
     *  
     *  @apiError 301 对应<code>301</code> 手机号码为空  
     *  @apiError 302 对应<code>302</code> 短信发送失败  
     *  @apiErrorExample {json} Error-Response: 
     *  手机号码为空
     * {
     * 	"model": null,
     *	"success": false,
     *	"errorMessage": 手机号码为空,
     *	"resultCode": 301
	 * }
	* @apiUse ERROR_405
     */  
	@RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
	@ResponseBody
	public Result<?> sendMessage(String mobile) {
		Assert.hasText(mobile, "请输入手机号");
		
		String content = "" + ((int) (Math.random() * 1000000));
		
		
		// 新的短信验证码发送
//		String[] sendMmessage = new String[] { content, "3" };
		SendSmsResponse response;
		try {
			response = AliyunSmsTool.sendSms(mobile,content);
			if (!response.getCode().equals("OK")) {
				return Result.failureResult("发送失败!");
			} else {
				return Result.successResult("发送成功", content);
			}
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			return  Result.failureResult("发送失败!");	
		}
		

	}
	

	/** 
	 * 
	 * @api {post} /rest/commonIntf/uploadImg 上传文件 
	 * @apiName uploadImg  
	 * @apiGroup Common 
	 * @apiVersion 1.0.0 
	 * @apiDescription 上传图片接口,返回绝对路径,都是指定以png后辍结尾
	 *  
	 * @apiParam {MultipartFile} fileName 文件数据流
	 *  
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} filePath 文件保存的绝对路径 
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *	"model": {
	 *		"filePath": "/upload/sc/images/20161227005210KOI5P4ew.png",
	 *		"viewPath": "http://127.0.0.1:90/upload/sc/images/20161227005210KOI5P4ew.png"
	 *	},
	 *	"success": true,
	 *	"errorMessage": null,
	 *	"resultCode": 200
	 *  }
	 *   
	 *  @apiError 305 对应<code>305</code> 图片保存失败  
	 *  @apiUse ERROR_405
	 */  
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public Result<?> uploadImg(@RequestParam MultipartFile  fileName,
			HttpServletRequest request){
	
		// 获取web应用根路径,也可以直接指定服务器任意盘符路径
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		
		//保存在项目的路径上
		String savePath = "/upload/cc/images/";
		
		File file1 = new File(rootPath + savePath);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		// 文件真实文件名
//		String fileName = fromFile.getFileName();
//		String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
		

		
		int fileSize = 5*1024*1204;
		// 我们一般会根据某种命名规则对其进行重命名
		String saveFileName = "";
//		String fileprefixName = FileUtils.getFilePrefix(fileName);
//		String noextfilename = DateUtils.getDataString() + StringUtil.random(8);// 自定义文件名称
		String noextfilename = "";// 自定义文件名称
		saveFileName = noextfilename + ".png";// 自定义文件名称
		
		File fileToCreate = new File(rootPath + savePath, saveFileName);

		// 检查同名文件是否存在,不存在则将文件流写入文件磁盘系统
		FileOutputStream os = null;
		try {
			
			
			os = new FileOutputStream(fileToCreate, false);
			os.write(fileName.getBytes());
//			os.write(fromFile.getFileData().getBytes());
			os.flush();
		} catch (Exception e) {
			logger.info("save file error,freespace=" +fileToCreate.getParentFile().getFreeSpace(),e);
			return Result.failureResult("发送失败!");
		}finally{
			IOUtils.closeQuietly(os);
		}

		String serviceUrl = "";
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("filePath", savePath + saveFileName);
		resultMap.put("viewPath", serviceUrl + savePath + saveFileName);
		return  Result.successResult("保存成功", resultMap);
	}

}
