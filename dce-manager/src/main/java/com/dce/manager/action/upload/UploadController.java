package com.dce.manager.action.upload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dce.manager.action.BaseAction;

@RestController
@RequestMapping("/upload")
public class UploadController  extends BaseAction{
	
	public void upload(MultipartFile  file){
		//Todo 保持文件的路径
		
		String fileName = file.getName();
		//file.getInputStream();
		
		//写文件
		
		
		//return 文件路径;
		
	}

}
