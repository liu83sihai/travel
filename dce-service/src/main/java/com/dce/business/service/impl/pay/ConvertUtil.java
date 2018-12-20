package com.dce.business.service.impl.pay;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kjtpay.gateway.common.domain.base.RequestBase;

public class ConvertUtil {
	
	
	/**
	 * 转换参数
	 * @param service
	 * @param reqParm
	 * @return
	 */
	public static  JSONObject convertParm(String service, String reqParm){
		
		if("instant_trade".equals(service)){
			return convertInstantTradeParm(reqParm);
			
		}else if("ensure_trade".equals(service)){
			return convertInstantTradeParm(reqParm);
			
		}else if("trade_settle".equals(service)){
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("royalty_info");
			return convertWithSpecialParm(reqParm, fieldNameList);
			
		}else if("entry_account_offline".equals(service)){
			return convertWithSpecialParm(reqParm, null);
			
		}else if("batch_bank_witholding".equals(service)){
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("withholding_list");
			return convertWithSpecialParm(reqParm, fieldNameList);
			
		}else if("trade_bank_witholding".equals(service)){
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("royalty_info");
			return convertWithSpecialParm(reqParm, fieldNameList);
			
		}else if("trade_close".equals(service)){
			return convertWithSpecialParm(reqParm, null);
			
		}else if("trade_query".equals(service)){
			return convertWithSpecialParm(reqParm, null);
			
		}else if("trade_refund".equals(service)){
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("royalty_info");
			return convertWithSpecialParm(reqParm, fieldNameList);
			
		}else if("batch_transfer_account".equals(service)){
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("transfer_list");
			return convertWithSpecialParm(reqParm, fieldNameList);
			
		}else if("transfer_to_account".equals(service)){
			return convertWithSpecialParm(reqParm, null);
			
		}else if("transfer_to_card".equals(service)){
			return convertWithSpecialParm(reqParm, null);
			
		}else if("batch_transfer_card".equals(service)){
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("transfer_list");
			return convertWithSpecialParm(reqParm, fieldNameList);
		}
		
		return null;
		
	}
	
	
	/**
	 * 转换给定参数名的参数，将给定参数的JSON字符串格式转换成JSON对象数组格式
	 * @param reqParm
	 * @param fieldNameList
	 * @return
	 */
	public static JSONObject convertWithSpecialParm(String reqParm, List<String> fieldNameList){
		
		JSONObject reqJson = JSONObject.parseObject(reqParm);
		
		if(!CollectionUtils.isEmpty(fieldNameList)){
			
			for(String fieldName : fieldNameList){
				String fieldValue = reqJson.getString(fieldName);
				if(StringUtils.isNoneBlank(fieldValue)){
					JSONArray fieldValueJSONArray = JSONArray.parseArray(fieldValue);
					reqJson.put(fieldName, fieldValueJSONArray);
				}
			}
			
		}
		
		return reqJson;
	}
	
	
	/**
	 * 转换即时到账参数
	 * @param req
	 * @return
	 */
	public static JSONObject convertInstantTradeParm(String req){
		JSONObject tradeReq = JSONObject.parseObject(req);
		//设置复杂属性
		//转换支付方式pay_method
		String pay_method = tradeReq.getString("pay_method");
		JSONObject payMethod = JSONObject.parseObject(pay_method);
		tradeReq.put("pay_method", payMethod);
		
		//转换终端信息域terminal_info
		String terminal_info = tradeReq.getString("terminal_info");
		JSONObject terminalInfo = JSONObject.parseObject(terminal_info);
		tradeReq.put("terminal_info", terminalInfo);
		//转换商户自定义域merchant_custom
		String merchant_custom = tradeReq.getString("merchant_custom");
		JSONObject merchantCustom = JSONObject.parseObject(merchant_custom);
		tradeReq.put("merchant_custom", merchantCustom);
		
		//转换交易信息trade_info
		String trade_info = tradeReq.getString("trade_info");
		
		if(StringUtils.isNoneBlank(trade_info)){
			JSONObject tradeInfo = JSONObject.parseObject(trade_info);
			
			
//			//转换交易扩展参数trade_ext
//			String trade_ext = tradeInfo.getString("trade_ext");
//			if(StringUtils.isNoneBlank(trade_ext)){
//				JSONObject tradeExt = JSONObject.parseObject(trade_ext);
//				tradeInfo.put("trade_ext", tradeExt);
//			}
			
			//转换分账列表royalty_info
			String royalty_info = tradeInfo.getString("royalty_info");
			if(StringUtils.isNoneBlank(royalty_info)){
				JSONArray royaltyInfos = JSONArray.parseArray(royalty_info);
				tradeInfo.put("royalty_info", royaltyInfos);
			}
			
			tradeReq.put("trade_info", tradeInfo);
			
			return tradeReq;
		}
		
		return null;
	}
	
	
	/**
	 * 转换公共请求参数
	 * @param req
	 * @return
	 */
	public static RequestBase convertRequestBaseParm(Map<String,String> req){
		if(!CollectionUtils.isEmpty(req)){
			RequestBase requestBase = new RequestBase();
			requestBase.setRequestNo(req.get("request_no"));
			requestBase.setService(req.get("service"));
			requestBase.setVersion(req.get("version"));
			requestBase.setPartnerId(req.get("partner_id"));
			requestBase.setCharset(req.get("charset"));
			requestBase.setSignType(req.get("sign_type"));
			requestBase.setSign(req.get("sign"));
			requestBase.setTimestamp(req.get("timestamp"));
			requestBase.setFormat(req.get("format"));
			requestBase.setBizContent(req.get("biz_content"));
			
			return requestBase;
		}
		
		return null;
	}

}
