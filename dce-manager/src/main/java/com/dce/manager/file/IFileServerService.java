package com.dce.manager.file;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: zhangyunhua
 * @date 2015年1月22日 上午10:03:50
 */
public interface IFileServerService {

	/**
	 * 保存文件到文件服务器,没有缩略图的保存
	 * 
	 * @param source
	 *            上传文件的输入流
	 * @param fileName
	 *            上传的原文件名
	 * @return
	 */
	public String saveFileNoThumb(InputStream source, String fileName);

	/**
	 * 保存文件到文件服务器,没有缩略图的保存
	 * 
	 * @param source
	 *            上传文件的输入流
	 * @param fileServerUrl
	 *            上传文件的路径
	 * @param startDir
	 *            上传文件子路径的开头结尾
	 * @param lastDir
	 *            上传文件子路径的路径结尾
	 * @param fileName
	 *            上传的原文件名
	 * @return
	 */
	public String saveFileNoThumbNew(InputStream source, String fileServerUrl,
			String startDir, String lastDir, String fileName);

	/**
	 * 保存文件到文件服务器
	 * 
	 * @param srcFile
	 *            源文件
	 * @param fileName
	 *            文件名称
	 * @param thumbSizes
	 *            缩放比例（如果为空，则则不生产缩放图片）
	 * @return
	 * @author: zhangyunhua
	 * @date: 2015年1月22日上午10:04:34
	 */
	String saveFile(File srcFile, String fileName, int[][] thumbSizes);

	/**
	 * 保存文件到文件服务器
	 * 
	 * @param source
	 *            文件输入流
	 * @param fileName
	 *            文件名称
	 * @param thumbSizes
	 *            缩放比例（如果为空，则则不生产缩放图片）
	 * @return
	 * @author: zhangyunhua
	 * @date: 2015年1月22日上午10:05:03
	 */
	String saveFile(InputStream source, String fileName, int[][] thumbSizes);

	/**
	 * 保存手机上传的文件
	 * 
	 * @param source
	 * @param fileName
	 * @param thumbSizes
	 * @return 原始图片路径、压缩后的图片路径
	 * @author zhengyfmf
	 */
	List<String> saveAppFile(InputStream source, String fileName,
			int[][] thumbSizes);

	String getImageStr(String imgFile);

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @return
	 * @author zhengyfmf
	 */
	boolean delFile(String filePath);

	/**
	 * 获取资源访问URL
	 * 
	 * @return
	 * @author: zhangyunhua
	 * @date: 2015年1月29日上午9:43:09
	 */
	String getFileAccessUrl();

	String getFilePath();

	/**
	 * 存储zip格式的处理压缩图片
	 * 
	 * @param source
	 *            zip 文档的input stream
	 * @return 返回服务存储的文件路径
	 */
	List<String> saveImgZip(InputStream source);

	/**
	 * 主要保存征信文件
	 */
	public String saveCreditXmlFileToServer(String fileName, String content);

	/**
	 * @Description 主要保存征信文件
	 * @author ladybug QQ: 3204644918
	 * @date 2016年1月4日 下午6:12:46
	 * @param fileName
	 * @param content
	 * @return
	 */
	public String saveCreditFileToServer(String fileName, String content);

	/**
	 * @Description 保存鹏元征信报告
	 * @author ladybug QQ: 3204644918
	 * @date 2016年1月5日 下午5:16:44
	 * @param compressed
	 * @return
	 */
	public Map<String, String> saveCreditReport(byte[] compressed);

	/**
	 * 文件存储到指定的目录
	 * 
	 * @param source
	 * @param fileName
	 * @param storeDir
	 * @return
	 */
	public String saveFileNoThumbWithStoreDir(InputStream source,
			String fileName, String storeDir);

	/*
	 * 保存字符串到文件
	 * @param fileType 查看实现类
	 */
	public String saveStringAsFile(String fileName, String content,String fileType);

	/**
	 * 创建一个文件
	 * @param fileName 文件名称
	 * @param fileType 文件类型 查看 实现类
	 * @return
	 */
	public File createFile(String fileName,String fileType);

	/**
	 * 取相对路径
	 * @param f
	 * @param dir
	 * @return
	 */
	public String getRelationPath(File f, String dir);
	
	/**
	 * 获取配置的磁盘路径
	 * @param fileType 查看实现类
	 * @return
	 */
	public String getDir(String fileType);
	
	// 将url图片文件转化为字节数组字符串，并对其进行Base64编码处理
	public String getImageFromUrl(String url);
}
