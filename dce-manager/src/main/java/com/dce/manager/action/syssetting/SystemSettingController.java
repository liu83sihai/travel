package com.dce.manager.action.syssetting;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.enums.CurrencyType;
import com.dce.business.common.result.Result;
import com.dce.business.entity.dict.CtCurrencyDo;
import com.dce.business.service.dict.ICtCurrencyService;
import com.dce.manager.action.BaseAction;

@RestController
@RequestMapping("/sysSet")
public class SystemSettingController extends BaseAction {

	
	@Autowired
	private ICtCurrencyService ctservice ;
	
	@RequestMapping(value = "/query", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView query(ModelMap modelMap){
		ModelAndView mav = new ModelAndView("syssetting/syssetting");
		
		CtCurrencyDo ct = ctservice.selectByName(CurrencyType.IBAC.name());
		modelMap.addAttribute("ct",ct);
		return mav;
	}
	
	@RequestMapping(value = "/save", method = {RequestMethod.GET,RequestMethod.POST})
	public void save(CtCurrencyDo ct,HttpServletRequest request,HttpServletResponse response){
		
		try{
			Result<?> result = ctservice.update(ct);
			outPrint(response, JSONObject.toJSON(result));
		}catch(Exception e){
			logger.error("修改失败:",e);
			outPrint(response, JSONObject.toJSON(Result.failureResult("修改报错!")));
		}
		
//		return new ModelAndView("redirect:/sysSet/query.html");
	}
}
