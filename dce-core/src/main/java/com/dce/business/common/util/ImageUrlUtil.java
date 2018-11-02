package com.dce.business.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class ImageUrlUtil {
    private final static Logger logger = Logger.getLogger(ImageUrlUtil.class);
    private static final String KEY = "seeFile";
  
    
    public static String getImagePath(String fileServer, String imgPath, String fileType) {
    	String sign = DigestUtils.md5Hex(KEY+imgPath);//电子签名
    	StringBuilder sb = new StringBuilder();
    	sb.append(fileServer)
    	  .append("/file/showImg.do?imgPath=")
    	  .append(imgPath)
    	  .append("&sign=")
    	  .append(sign)
    	  .append("&fileType=")
    	  .append(fileType);
    	  
    	String imagePath = sb.toString();
    	logger.info("imagePath:"+imagePath);
    	return imagePath;
		
	}
    
    public static String getImagePath(String fileServer,String imgPath){
    	return getImagePath(fileServer,imgPath,"normal");
    }
    
    public static String getOnlinePdfPath(String fileServer,String pdfPath){
    	String sign = DigestUtils.md5Hex(KEY+pdfPath);//电子签名
    	String path = fileServer+"/file/readPdf.do?sign="+sign+"&filePath="+pdfPath+"&isOnLine=true";
    	logger.info("pdfPath:"+path);
    	return path;
    }
    
    public static String getDownloadPdfPath(String fileServer,String pdfPath){
    	String sign = DigestUtils.md5Hex(KEY+pdfPath);//电子签名
    	String path = fileServer+"/file/readPdf.do?sign="+sign+"&filePath="+pdfPath+"&isOnLine=false";
    	logger.info("pdfPath:"+path);
    	return path;
    }
    
    public static String getLouPanImagePath(String fileServer,String imgPath){
    	String sign = DigestUtils.md5Hex(KEY+imgPath);//电子签名
    	String imagePath = fileServer+"/file/showLouPanImg.do?imgPath="+imgPath+"&sign="+sign;
    	logger.info("imagePath:"+imagePath);
    	return imagePath;
    }
    
    public static void main(String[] args) throws Exception{
    	String sign = DigestUtils.md5Hex(KEY+"/LianJia/huXing/201512/07/北京/00BDBAD9261EBBF9B0FE3833D3FF72E2.jpg");//电子签名
    	System.out.println(sign);
    }

}
