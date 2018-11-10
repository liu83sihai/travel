package com.dce.business.actions.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.ImageUrlUtil;
import com.dce.business.dao.sms.ISmsDao;
import com.dce.business.entity.sms.SmsDo;

import net.sf.json.JSONSerializer;
import sun.misc.BASE64Decoder;


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

	@Value("#{sysconfig['uploadPath']}")
	private String uploadPath;
	
	@Value("#{sysconfig['readImgUrl']}")
	private String readImgUrl;
	@Resource 
	ISmsDao smsDao;
	 /** 
     *  
     * @api {get}  /commonIntf/sendMessage.do 获取短信验证码
     * @apiName sendMessage  
     * @apiGroup Common 
     * @apiVersion 1.0.0 
     * @apiDescription 根据手机号码返回短信验证码
     *  
     * @apiParam {String} mobile 手机号码  
     * @apiParam {String} page 发送类型,空为验证码。code:验证码，扫描支付： scan，  卖单：  sale，  美元转让：  tran
     *  
     * @apiUse RETURN_MESSAGE
     * @apiSuccess {String} model 验证码
     * 
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
	@RequestMapping(value = "/sendMessage", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Result<?> sendMessage(String mobile,String page) {
		Assert.hasText(mobile, "请输入手机号");
		if(StringUtils.isBlank(page)){
			page = "code";
		}
		
		String content = "" + ((int) (Math.random() * 1000000));
		
		//String content = "123456";
		// 新的短信验证码发送
		SendSmsResponse response;
		try {
			response = AliyunSmsTool.sendSms(mobile,content);
			if (!response.getCode().equals("OK")) {
				return Result.failureResult("发送失败!");
			} else {
				SmsDo record = new SmsDo();
				record.setRecievers(mobile);
				record.setMessage(content);
				record.setBusinessType(page);
				smsDao.insertSelective(record);
				return Result.successResult("发送成功", content);
			}
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			return  Result.failureResult("发送失败!");	
		}

	}
	

	/** 
	 * 
	 * @api {post}  /commonIntf/uploadImg.do 上传文件 
	 * @apiName uploadImg  
	 * @apiGroup Common 
	 * @apiVersion 1.0.0 
	 * @apiDescription 上传图片接口,返回绝对路径,都是指定以png后辍结尾
	 *  
	 * @apiParam {String} fileData 文件数据流Base64文件编码字符串
	 * @apiParam {String} fileName 文件名
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
	public Result<?> uploadImg(	HttpServletRequest request){
		String fileName = getString("fileName");
		String imgeFile = getString("fileData");
		
		FileOutputStream os = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
	        // 去掉头部
			System.out.println("imgeFile:" + imgeFile);
			int zmHeaderLength = imgeFile.indexOf(";base64,") + ";base64,".length();
	        String fileContext = imgeFile.substring(zmHeaderLength);
	        fileContext = fileContext.replace("&#43;", "+");
	        System.out.println("fileContext:" + fileContext);
	        BASE64Decoder decoder = new BASE64Decoder();
	
	        byte[] decodedBytes = decoder.decodeBuffer(fileContext);
	
	           
			//保存在项目的路径上
			String savePath =uploadPath +  "/app/images/";
			File file1 = new File(savePath);
			if (!file1.exists()) {
				file1.mkdirs();
			}
			
			// 文件真实文件名
			int fileSize = 5*1024*1204;
			// 我们一般会根据某种命名规则对其进行重命名
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			String date = df.format(new Date());
			String saveFileName = "";
			saveFileName = date + fileName;// 自定义文件名称
			
			File fileToCreate = new File( savePath, saveFileName);
	
			// 检查同名文件是否存在,不存在则将文件流写入文件磁盘系统
			os = new FileOutputStream(fileToCreate, false);
			os.write(decodedBytes);
			os.flush();
			
			
			resultMap.put("filePath", savePath + saveFileName);
			resultMap.put("viewPath", readImgUrl + savePath + saveFileName);
			
		} catch (Exception e) {
			logger.info("save file error " ,e);
			return Result.failureResult("发送失败!");
		}finally{
			if(os != null) {
			IOUtils.closeQuietly(os);
			}
		}
		
		return  Result.successResult("图片保存成功", resultMap);
	}

	/** 
	 * 
	 * @api {post} /commonIntf/uploadMultipartFile.do 上传MultipartFile文件 
	 * @apiName uploadMultipartFile  
	 * @apiGroup Common 
	 * @apiVersion 1.0.0 
	 * @apiDescription 上传图片接口,返回绝对路径
	 *  
	 * @apiParam {String} fileData 文件数据流
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
	@RequestMapping(value = "/uploadMultipartFile", method = RequestMethod.POST)
	@ResponseBody
	public Result<?> fileUpload( 
			HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest)request; 
		MultipartFile file = multipartRequest.getFile("fileData");//file是页面input的name名 
		
	
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext()); 
		
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		if (file != null && !file.isEmpty()) {
		if (resolver.isMultipart(request)) {
			String originalFilename = file.getOriginalFilename();
			logger.debug(originalFilename);

			// 保存在项目的路径上
			String savePath = uploadPath + "/app/images/";
			File file1 = new File(savePath);
			if (!file1.exists()) {
				file1.mkdirs();
			}

			// 文件真实文件名
			int fileSize = 5 * 1024 * 1204;
			// 我们一般会根据某种命名规则对其进行重命名
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			String date = df.format(new Date());
			String saveFileName = "";
			saveFileName = date + originalFilename;// 自定义文件名称

			// 保存的文件dest File
			File outFile = new File(saveFileName, file.getOriginalFilename());
			logger.debug(outFile.getAbsolutePath());
			try {
				// 从缓存文件复制到目标文件
				file.transferTo(outFile);

				// 保存文件信息，返回ID
				resultMap.put("filePath", savePath + saveFileName);
				resultMap.put("viewPath", readImgUrl + savePath + saveFileName);

			} catch (IOException e) {
				e.printStackTrace();
				logger.debug(e);
			}
		}

		return Result.successResult("图片保存成功", resultMap);
	}
	
	/** 
	 * 
	 * @api {post} /commonIntf/imgUpload.do 上传MultipartFile文件 
	 * @apiName imgUpload  
	 * @apiGroup Common 
	 * @apiVersion 1.0.0 
	 * @apiDescription 上传图片接口,返回绝对路径
	 *  
	 * @apiParam {String} fileData 文件数据流
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
	@RequestMapping(value = "/imgUpload")
	@ResponseBody
	public Result<?> imgUpload(HttpServletRequest request, HttpServletResponse response) {
		
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
		
			
//			String imgFilePath = fileServerService.saveFileNoThumb(imgFile.getInputStream(), imgFile.getOriginalFilename());
			String savePath = uploadPath + "/app/images/";
			// 文件保存服务器 和 文件保存数据库
			if (StringUtils.isNotBlank(savePath)) {
				resultMap.put("error", 0);
//				resultMap.put("imgPath", savePath);
//				resultMap.put("fileName", imgFile.getOriginalFilename());
				
				String saveFileName = imgFile.getOriginalFilename();
				File outFile = new File(savePath, imgFile.getOriginalFilename());
				logger.debug(outFile.getAbsolutePath());
				try {
					// 从缓存文件复制到目标文件
					imgFile.transferTo(outFile);

					// 保存文件信息，返回ID
					resultMap.put("filePath", savePath + saveFileName);
					resultMap.put("viewPath", readImgUrl + savePath + saveFileName);

				} catch (IOException e) {
					e.printStackTrace();
					logger.debug(e);
				}
				resultMap.put("message","上传图片成功");
//				log.info("保存结果成功.....");
				return Result.successResult("图片保存成功", resultMap);
			} else {
				return Result.successResult("图片保存成功", resultMap);
			}
		} catch (BusinessException e) {
			resultMap.put("message", e);
			return Result.failureResult("发送失败!");
		} catch (Exception e) {
			resultMap.put("message", e);
			return Result.failureResult("发送失败!");
		}
	}

}
