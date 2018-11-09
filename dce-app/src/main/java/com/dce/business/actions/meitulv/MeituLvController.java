package com.dce.business.actions.meitulv;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.common.result.Result;
import com.dce.business.common.util.HttpUtil;
import com.dce.business.common.wxPay.util.Sha1Util;
import com.dce.business.entity.aboutUs.AboutusDo;
import com.dce.business.service.aboutUs.IAboutusService;

@RestController
@RequestMapping("/meituLv")
public class MeituLvController {
	private final static Logger logger = Logger.getLogger(MeituLvController.class);
	
	final String MEITU_LOGIN="https://qiuapi.meitulv.com/virtual_open";
	final String MEITU_LIST="https://qiuapi.meitulv.com/services_list";
	final String QIUCODE="356a192b7913b04c54574d18c28d4111";
	
	final String PRIVATEKEY ="48e0e503e262e9b7416de7c308b17e";
	
	final String certFileName = "qiuapi.meitulv.com.cer"; 
	final String certFilePassword = "48e0e503e262e9b7416de7c308b17e"; 
	
	/** 
	 * @api {GET} /meituLv/lgoin.do meituLv登录
	 * @apiName lgoin
	 * @apiGroup meituLv 
	 * @apiVersion 1.0.0 
	 * @apiDescription meituLv登录
	 *  
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} model 返回成功信息
	 *  @apiSuccess {String}  url 介绍地址
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "result": {
	 *	"model": {
	 *		
	 * 	},
	 *	  "status": {
	 *	    "code": 200,
	 *	    "msg": "请求成功"
	 *	  }
	 *	}
	 */ 
	 @RequestMapping("/lgoin")
	 public Result<?> lgoin() {
		 logger.info("登录行旅通.....");
		 
		 String pnum = "13723412389";
		 String uname = "大陆";
//		 String unameCode= getUnicode(uname);
//		 String sings = Sha1Util.getSha1(PRIVATEKEY+uname+QIUCODE+pnum).substring(2, 34);
		 String sings = DigestUtils.shaHex(PRIVATEKEY+uname+QIUCODE+pnum).substring(2, 34);
//		 System.out.println(Sha1Util.getSha1(PRIVATEKEY+uname+QIUCODE+pnum));
//		 System.out.println(DigestUtils.shaHex(PRIVATEKEY+uname+QIUCODE+pnum));
		 Map<String,String> param = new HashMap<String,String>();
		 param.put("UID", "UID201811090006");
		 param.put("PNUM",pnum);
		 param.put("UNAME", uname);
		 param.put("QIUCODE", QIUCODE);
		 param.put("DAYS", "365");
		 param.put("SINGS", sings);
		 
		 try {
			 String result =  HttpUtil.httpCertPostNotSSl(MEITU_LOGIN, param);
			 System.out.println("result==========" + result);
			 
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Map<String, Object> map = new HashMap<>();
		 return Result.successResult("登录成功", map);
	 }
	 
	 @RequestMapping("/list")
	 public Result<?> list() {
		 logger.info("景区列表.....");
		 
		 String servicetype = "scenic";
//		 String unameCode= getUnicode(uname);
//		 String sings = Sha1Util.getSha1(PRIVATEKEY+uname+QIUCODE+pnum).substring(2, 34);
		 String sings = DigestUtils.shaHex(QIUCODE+PRIVATEKEY).substring(2, 34);
//		 System.out.println(Sha1Util.getSha1(PRIVATEKEY+uname+QIUCODE+pnum));
//		 System.out.println(DigestUtils.shaHex(PRIVATEKEY+uname+QIUCODE+pnum));
		 Map<String,String> param = new HashMap<String,String>();
		 param.put("servicetype", servicetype);
		 param.put("QIUCODE", QIUCODE);
		 param.put("SINGS", sings);
		 try {
			 String result =  HttpUtil.httpCertPostNotSSl(MEITU_LIST, param);
			 System.out.println("result==========" + result);
			 
		 } catch (Throwable e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		 Map<String, Object> map = new HashMap<>();
		 return Result.successResult("列表返回成功", map);
	 }
	 
	 public static void main(String[] args) {
		 MeituLvController  meitu = new MeituLvController();
//		 meitu.lgoin();
		 meitu.list();
	}
	 

static String getUnicode(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);
                 
            }
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
