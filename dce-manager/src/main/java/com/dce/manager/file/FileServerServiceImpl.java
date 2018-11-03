package com.dce.manager.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author: zhangyunhua
 * @date 2015年1月22日 上午10:11:18
 */
@Component("fileService")
public class FileServerServiceImpl implements IFileServerService {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Value("${uploadPath}")
	private String fileServerDir;
	private String signfileDir;
	@Value("${readImgUrl}")
	private String fileAccessUrl;
	private String creditfileDir;

	public static final String normal = "normal";
	public static final String sign = "sign";
	public static final String credit = "credit";

	private final String file_separator = "/";
	
	@Value("#{sysconfig['img.server.url']}")
	private String imgServerUrl;
	
	public String saveFile(File srcFile, String fileName, int[][] thumbSizes) {
		try {
			return saveFile(new FileInputStream(srcFile), fileName, thumbSizes);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public String saveFile(InputStream source, String fileName,
			int[][] thumbSizes) {
		String subDir = this.getSubDir();
		File dir = new File(getDir() + subDir);
		String newFileName = getNewFileName(fileName, dir);
		try {
			File destFile = new File(dir + file_separator + newFileName);
			FileUtils.copyInputStreamToFile(source, destFile);
			thumbSizes = ((thumbSizes == null || thumbSizes.length == 0) ? new int[][] { {
					400, 400 } }
					: thumbSizes);
			for (int i = 0; i < thumbSizes.length; i++) {
				int[] wh = thumbSizes[i];
				String thumbImgName = FileSavingUtils.getThumbImgName(
						destFile.getCanonicalPath(), wh[0], wh[1]);
				ImageThumbUtils
						.thumbImage(
								destFile.getCanonicalPath(),
								wh[0],
								wh[1],
								(dir.getCanonicalPath() + file_separator + thumbImgName));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return (subDir + file_separator + newFileName);
	}

	/**
	 * 没有缩略图的保存
	 * 
	 * @param source
	 *            上传文件的输入流
	 * @param fileName
	 *            上传的原文件名
	 * @return
	 */
	public String saveFileNoThumb(InputStream source, String fileName) {
		String subDir = this.getSubDir();
		File dir = new File(getDir() + subDir);
		String newFileName = getNewFileName(fileName, dir);
		try {
			File destFile = new File(dir + file_separator + newFileName);
			FileUtils.copyInputStreamToFile(source, destFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return (subDir + file_separator + newFileName);
	}

	/**
	 * 没有缩略图的保存，根据存储路径来存储图片
	 * 
	 * @param source
	 *            上传文件的输入流
	 * @param fileName
	 *            上传的原文件名
	 * @return
	 */
	public String saveFileNoThumbWithStoreDir(InputStream source,
			String fileName, String storeDir) {
		String subDir = file_separator
				+ DateFormatUtils.format(new Date(), "yyyyMM") + file_separator
				+ DateFormatUtils.format(new Date(), "dd");
		File dir = new File(storeDir + subDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String newFileName = FileSavingUtils.getNewFileName(fileName);
		try {
			File destFile = new File(dir + file_separator + newFileName);
			FileUtils.copyInputStreamToFile(source, destFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return (subDir + file_separator + newFileName);
	}

	/**
	 * @see com.ladybug.common.file.IFileServerService#saveFileNoThumbNew
	 */
	@Override
	public String saveFileNoThumbNew(InputStream source, String fileServerUrl,
			String startDir, String lastDir, String fileName) {
		String subDir = file_separator;
		if (StringUtils.isNotBlank(startDir)) {
			subDir += startDir;
		}
		subDir += DateFormatUtils.format(new Date(), "yyyyMM") + file_separator
				+ DateFormatUtils.format(new Date(), "dd");
		if (StringUtils.isNotBlank(lastDir)) {
			subDir += lastDir;
		}
		File dir = new File(fileServerDir + subDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String newFileName = FileSavingUtils.getNewFileName(fileName);
		try {
			File destFile = new File(dir + file_separator + newFileName);
			FileUtils.copyInputStreamToFile(source, destFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return (subDir + file_separator + newFileName);
	}

	public List<String> saveAppFile(InputStream source, String fileName,
			int[][] thumbSizes) {
		List<String> list = new ArrayList<String>();
		String subDir = this.getSubDir();
		File dir = new File(getDir() + subDir);
		String newFileName = getNewFileName(fileName, dir);
		try {
			/*File destFile = new File(dir + file_separator + newFileName);
			FileUtils.copyInputStreamToFile(source, destFile);
			list.add(subDir + file_separator + newFileName);
			thumbSizes = ((thumbSizes == null || thumbSizes.length == 0) ? new int[][] { {
					400, 400 } }
					: thumbSizes);
			for (int i = 0; i < thumbSizes.length; i++) {
				int[] wh = thumbSizes[i];
				String thumbImgName = FileSavingUtils.getThumbImgName(destFile.getCanonicalPath(), wh[0], wh[1]);
				ImageThumbUtils.thumbImage(destFile.getCanonicalPath(),	wh[0],wh[1],(dir.getCanonicalPath() + file_separator + thumbImgName));
				list.add(subDir + file_separator + thumbImgName);
			}*/
			
			//BASE64Decoder decoder = new BASE64Decoder();
			//String filePath = subDir + file_separator + newFileName;
			//字节转换
			//byte[] decodedBytes = decoder.decodeBuffer(base64);
			
			String url = imgServerUrl;
			CloseableHttpResponse httpRresponse = null;
			CloseableHttpClient httpclient = null;
			httpclient = HttpClients.createDefault();
			logger.info("调用文件服务器上传图片url：" + url);
			HttpPost post = new HttpPost(url);  
			//post.setEntity(MultipartEntityBuilder.create().addPart("file",new ByteArrayBody(decodedBytes,fileName)).build());
			post.setEntity(MultipartEntityBuilder.create().addPart("file", new InputStreamBody(source,fileName)).build());     
			logger.info("调用文件服务器上传图片post：" + post);
			httpRresponse = httpclient.execute(post);    
			HttpEntity entitys = httpRresponse.getEntity();
			String body = "";  
			if (entitys != null) {  
	            //按指定编码转换结果实体为String类型  
	            body = EntityUtils.toString(entitys, "utf-8");  
	        }  
	        EntityUtils.consume(entitys);  
	      //释放链接  
	        httpRresponse.close();
	        JSONObject  jsonObject = JSONObject.fromObject(body);
	        Map<String, Object> map = (Map)jsonObject;
	        logger.info(map.get("error").toString());
	        if(("0").equals(map.get("error").toString())){
	        	list.add(map.get("imgPath").toString());
	        }
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public boolean delFile(String filePath) {
		if (StringUtils.isNotBlank(filePath)) {
			File file = new File(fileServerDir + filePath);
			if (file.exists()) {
				try {
					boolean rs = file.delete();
					return rs;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	private String mkdirByDay() {
		String subDir = file_separator
				+ DateFormatUtils.format(new Date(), "yyyyMM") + file_separator
				+ DateFormatUtils.format(new Date(), "dd");
		File dir = new File(fileServerDir + subDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return subDir;
	}

	/**
	 * 上传zip格式的压缩图片
	 * 
	 * @param source
	 */
	public List<String> saveImgZip(InputStream source) {
		List<String> filePathList = new ArrayList<String>();
		try {
			ZipInputStream Zin = new ZipInputStream(source);// 输入源zip流
			BufferedInputStream Bin = new BufferedInputStream(Zin);
			File newImgFile = null;
			ZipEntry entry;
			// 按日期格式创建新目录
			String parentDir = mkdirByDay();

			// 解压zip逐个读出图片保存到server
			while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
				String orgFileName = entry.getName();
				// 服务器文件完整路径+新的文件名称
				String newFileName = FileSavingUtils.getNewFileName(parentDir
						+ file_separator + orgFileName);
				newImgFile = new File(newFileName);
				FileOutputStream out = new FileOutputStream(newImgFile);
				// 写文件流
				BufferedOutputStream Bout = new BufferedOutputStream(out);
				int b;
				while ((b = Bin.read()) != -1) {
					Bout.write(b);
				}
				Bout.close();
				out.close();
				filePathList.add(newFileName);
			}
			Bin.close();
			Zin.close();
		} catch (IOException e) {
			logger.info("文件解压失败");
			e.printStackTrace();
		}
		logger.info("文件解压完成");
		return filePathList;
	}

//	public List<CertificateDo> saveMaterialInfo(List<CertificateDo> fileList) {
//		logger.info("保存上传资料信息至服务器本地......");
//		try {
//			String subDir = file_separator
//					+ DateFormatUtils.format(new Date(), "yyyyMM")
//					+ file_separator + DateFormatUtils.format(new Date(), "dd");
//			File dir = new File(fileServerDir + subDir);
//			if (!dir.exists()) {
//				dir.mkdirs();
//			}
//			for (Iterator<CertificateDo> it = fileList.iterator(); it.hasNext();) {
//				CertificateDo certificateDo = it.next();
//				String base64 = certificateDo.getFileContent();
//				String fileName = certificateDo.getCertificateName();
//				String newFileName = FileSavingUtils.getNewFileName(fileName);
//				logger.info("newFileName="+newFileName);
//				BASE64Decoder decoder = new BASE64Decoder();
//				String filePath = subDir + file_separator + newFileName;
//				//字节转换
//				byte[] decodedBytes = decoder.decodeBuffer(base64);
//				String url = imgServerUrl;
//				CloseableHttpResponse httpRresponse = null;
//				CloseableHttpClient httpclient = null;
//				httpclient = HttpClients.createDefault();
//				logger.info("调用文件服务器上传图片url：" + url);
//				HttpPost post = new HttpPost(url);  
//				//post.setEntity(MultipartEntityBuilder.create().addPart("file",new InputStreamBody(new ByteArrayInputStream(decodedBytes), newFileName)).build() );    
//				post.setEntity(MultipartEntityBuilder.create().addPart("file",new ByteArrayBody(decodedBytes,fileName)).build());    
//				logger.info("调用文件服务器上传图片post：" + post);
//				httpRresponse = httpclient.execute(post);    
//				HttpEntity entitys = httpRresponse.getEntity();
//				String body = "";  
//				if (entitys != null) {  
//		            //按指定编码转换结果实体为String类型  
//		            body = EntityUtils.toString(entitys, "utf-8");  
//		        }  
//		        EntityUtils.consume(entitys);  
//			        //释放链接  
//		        httpRresponse.close();
//		        JSONObject  jsonObject = JSONObject.fromObject(body);
//		        Map<String, Object> map = (Map)jsonObject;
//		        System.out.println(map.get("error"));
//		        if(("0").equals(map.get("error").toString())){
//		        	certificateDo.setFilePath(map.get("imgPath").toString());
//					certificateDo.setDestFilePath(map.get("imgPath").toString());
//		        }
//				/*FileOutputStream out = new FileOutputStream(fileServerDir+ filePath);
//				out.write(decodedBytes);
//				logger.info("保存上传资料信息至服务器本地......保存文件路径：" + fileServerDir+ filePath);
//				out.flush();
//				out.close();*/
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("上传图片错误信息为：",e);
//		}
//		return fileList;
//	}

	// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	public String getImageStr(String imgFile) {
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String imgStr = null;
		if (data != null) {
			// 对字节数组Base64编码
			BASE64Encoder encoder = new BASE64Encoder();
			imgStr = encoder.encode(data);// 返回Base64编码过的字节数组字符串
			imgStr = imgStr.replaceAll("[\n\r\t]", "");
		}
		return imgStr;// 去掉换行符
	}
	
	
	// 将url图片文件转化为字节数组字符串，并对其进行Base64编码处理
	public String getImageFromUrl(String url) {
		InputStream in = null;
		byte[] imgBytes = null;
		CloseableHttpResponse httpRresponse = null;
		CloseableHttpClient httpclient = null;
		BufferedInputStream br = null;
		ByteArrayOutputStream baos = null;
		String imgStr = null;
		// 读取图片字节数组
		try {
			httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpRresponse = httpclient.execute(httpGet);
			in = httpRresponse.getEntity().getContent();
			br = new BufferedInputStream(in);
			baos = new ByteArrayOutputStream();
			
			//读到字节流
			byte[] bs = new byte[1024];
			int len = 0;
			while ((len = br.read(bs)) > 0) {
				baos.write(bs, 0, len);
			}
			//字节流转字节数组
			imgBytes = baos.toByteArray();
			
			//转base64
			if (imgBytes != null) {
				// 对字节数组Base64编码
				BASE64Encoder encoder = new BASE64Encoder();
				imgStr = encoder.encode(imgBytes);// 返回Base64编码过的字节数组字符串
				imgStr = imgStr.replaceAll("[\n\r\t]", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(in != null){
					in.close();
				}
				if(baos != null){
					baos.close();
				}
				if(br !=null){
					br.close();
				}
				if(httpclient != null){
					httpclient.close();
				}
				in =null;
				baos = null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return imgStr;// 去掉换行符
	}

	/*
	 * 保存文件
	 */
	public String saveStringAsFile(String fileName, String content,
			String fileType) {
		try {
			String subDir = this.getSubDir();
			File dir = new File(getDir(fileType) + subDir);
			String filePath = getNewFileName(fileName, dir);
			File destFile = new File(getDir(fileType) + subDir + file_separator
					+ filePath);
			OutputStreamWriter osw = new OutputStreamWriter(
					new FileOutputStream(destFile, true), "utf-8");
			osw.write(content);
			osw.flush();
			osw.close();
			return this.getRelationPath(destFile, fileType);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/*
	 * 主要保存征信文件
	 */
	public String saveCreditXmlFileToServer(String fileName, String content) {
		try {
			String subDir = file_separator
					+ DateFormatUtils.format(new Date(), "yyyyMM")
					+ file_separator + DateFormatUtils.format(new Date(), "dd");
			File dir = new File(creditfileDir + subDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String newFileName = FileSavingUtils.getNewFileName(fileName);
			String filePath = creditfileDir + subDir + file_separator
					+ newFileName;
			OutputStreamWriter osw = new OutputStreamWriter(
					new FileOutputStream(filePath, true), "GBK");
			osw.write(content);
			osw.close();
			return (subDir + file_separator + newFileName);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @Description 解压缩
	 * @author ladybug QQ: 3204644918
	 * @date 2016年1月4日 下午4:13:00
	 * @param fileStream
	 *            代解压缩String
	 */
	public String saveCreditFileToServer(String fileName, String content) {
		try {
			String subDir = file_separator
					+ DateFormatUtils.format(new Date(), "yyyyMM")
					+ file_separator + DateFormatUtils.format(new Date(), "dd");
			File file = new File(creditfileDir + subDir);
			if (!file.exists()) {
				file.mkdirs();
			}
			byte[] fileByte = new BASE64Decoder().decodeBuffer(content);
			String newFileName = FileSavingUtils.getNewFileName(fileName);
			String filePath = creditfileDir + subDir + file_separator
					+ newFileName;
			FileOutputStream fstream = new FileOutputStream(filePath);
			fstream.write(fileByte);
			fstream.flush();
			fstream.close();
			return (subDir + file_separator + newFileName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return fileServerDir
	 */
	public String getFileServerDir() {
		return fileServerDir;
	}

	public void setSignfileDir(String signfileDir) {
		this.signfileDir = signfileDir;
	}

	/**
	 * @param fileServerDir
	 *            the fileServerDir to set
	 */
	public void setFileServerDir(String fileServerDir) {
		this.fileServerDir = fileServerDir;
	}

	/**
	 * @return fileAccessUrl
	 */
	public String getFileAccessUrl() {
		return fileAccessUrl;
	}

	/**
	 * @param fileAccessUrl
	 *            the fileAccessUrl to set
	 */
	public void setFileAccessUrl(String fileAccessUrl) {
		this.fileAccessUrl = fileAccessUrl;
	}

	public String getFilePath() {
		return this.fileServerDir;
	}

	/**
	 * @return creditfileDir
	 */
	public String getCreditfileDir() {
		return creditfileDir;
	}

	/**
	 * @param creditfileDir
	 *            the creditfileDir to set
	 */
	public void setCreditfileDir(String creditfileDir) {
		this.creditfileDir = creditfileDir;
	}

	@Override
	public Map<String, String> saveCreditReport(byte[] compressed) {
		Map<String, String> filePathMap = new HashMap<String, String>();
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(compressed);
			ZipInputStream Zin = new ZipInputStream(in);// 输入源zip流
			BufferedInputStream Bin = new BufferedInputStream(Zin);
			File newImgFile = null;
			ZipEntry entry;
			// 按日期格式创建新目录
			String subDir = file_separator
					+ DateFormatUtils.format(new Date(), "yyyyMM")
					+ file_separator + DateFormatUtils.format(new Date(), "dd");
			File dir = new File(creditfileDir + subDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// 解压zip逐个读出图片保存到server
			while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
				String orgFileName = entry.getName();
				if (orgFileName.equals("reports.xml")) {// 保存xml
					// 服务器文件完整路径+新的文件名称
					String newFileName = FileSavingUtils
							.getNewFileName(orgFileName);
					String filePath = creditfileDir + subDir + file_separator
							+ newFileName;
					newImgFile = new File(filePath);
					FileOutputStream out = new FileOutputStream(newImgFile);
					// 写文件流
					BufferedOutputStream Bout = new BufferedOutputStream(out);
					int b;
					while ((b = Bin.read()) != -1) {
						Bout.write(b);
					}
					Bout.close();
					out.close();
					filePathMap.put("xmlFilePath", filePath);
					System.out.println("xmlFilePath:"
							+ filePathMap.get("xmlFilePath"));
				} else if (orgFileName.equals("pdfsingle.zip")) {// 解压pdf压缩包，只保存pdf
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int offset = -1;
					while ((offset = Bin.read(buffer)) != -1) {
						out.write(buffer, 0, offset);
					}
					byte[] content = out.toByteArray();
					ByteArrayInputStream inTemp = new ByteArrayInputStream(
							content);
					ZipInputStream ZinTemp = new ZipInputStream(inTemp);// 输入源zip流
					BufferedInputStream BinTemp = new BufferedInputStream(
							ZinTemp);
					ZipEntry entryTemp;
					while ((entryTemp = ZinTemp.getNextEntry()) != null
							&& !entryTemp.isDirectory()) {
						String orgFileNameTemp = entryTemp.getName();
						// 文件名后缀
						String hz = orgFileNameTemp != null ? orgFileNameTemp
								.substring(orgFileNameTemp.length() - 3) : "";
						if (hz.equals("pdf")) {
							// 服务器文件完整路径+新的文件名称
							String newFileName = FileSavingUtils
									.getNewFileName(orgFileNameTemp);
							String filePath = creditfileDir + subDir
									+ file_separator + newFileName;
							newImgFile = new File(filePath);
							FileOutputStream outTemp = new FileOutputStream(
									newImgFile);
							// 写文件流
							BufferedOutputStream Bout = new BufferedOutputStream(
									outTemp);
							int b;
							while ((b = BinTemp.read()) != -1) {
								Bout.write(b);
							}
							Bout.close();
							outTemp.close();
							filePathMap.put("pdfFilePath", filePath);
							System.out.println("pdfFilePath:"
									+ filePathMap.get("pdfFilePath"));
						}
					}
				}

			}
			Bin.close();
			Zin.close();
		} catch (IOException e) {
			System.out.println("文件解压失败");
			e.printStackTrace();
		}
		System.out.println("鹏元征信文件解压完成并保存");
		return filePathMap;
	}

	@Override
	public File createFile(String fileName, String fileType) {
		String subDir = getSubDir();
		File dir = new File(getDir(fileType) + subDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String newFileName = FileSavingUtils.getNewFileName(fileName);
		File destFile = new File(dir + file_separator + newFileName);
		return destFile;
	}

	private String getNewFileName(String fileName, File dir) {
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String newFileName = FileSavingUtils.getNewFileName(fileName);
		return newFileName;
	}

	private String getSubDir() {
		StringBuilder sb = new StringBuilder();
		sb.append(file_separator)
				.append(DateFormatUtils.format(new Date(), "yyyyMM"))
				.append(file_separator)
				.append(DateFormatUtils.format(new Date(), "dd"));
		return sb.toString();
	}

	private String getDir() {
		return this.getDir(normal);
	}

	public String getDir(String fileType) {
		if (normal.equals(fileType)) {
			return fileServerDir;
		}
		if (sign.equals(fileType)) {
			return signfileDir;
		}
		if (credit.equals(fileType)) {
			return creditfileDir;
		}
		return fileServerDir;
	}

	@Override
	public String getRelationPath(File f, String dir) {
		String fileDir =  this.getDir(dir);
		return f.getAbsolutePath().replace(fileDir, "");
	}
	
	public static void main(String[] args) {
		//System.out.println("d:/dd/sfsf".replace("d:",""));
		FileServerServiceImpl fs = new FileServerServiceImpl();
		String base64 = fs.getImageFromUrl("http://rec.51haols.com/res/images/app/uimg1.png");
		System.out.println(base64);
	}

}
