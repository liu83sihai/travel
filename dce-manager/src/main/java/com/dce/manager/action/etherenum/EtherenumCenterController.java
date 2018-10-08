package com.dce.manager.action.etherenum;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.entity.etherenum.EthAccountPlatformDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.entity.page.Pagination;
import com.dce.business.service.third.IEthereumPlatformService;
import com.dce.business.service.third.IEthereumService;
import com.dce.manager.action.BaseAction;

@Controller
@RequestMapping("/ethcenter")
public class EtherenumCenterController extends BaseAction{

	@Resource
	private IEthereumPlatformService platformService;
	@Resource
	private IEthereumService ethereumService;
	 
	@RequestMapping("/index")
	public String index(){
		return "/etherenum/center_index";
	}
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/list")
	public void list(Pagination<EthAccountPlatformDo> pagination,
			HttpServletRequest request, HttpServletResponse response) {
		
		try {
			PageDo<EthAccountPlatformDo> page = PageDoUtil.getPage(pagination);
			
			PageDo<EthAccountPlatformDo> result = platformService.selectEthAccountPlatByPage(page, null);
			
			pagination = PageDoUtil.getPageValue(pagination, result);
			outPrint(response, JSON.toJSONString(pagination));
			
		}catch (Exception e) {
			logger.error("显示用户数据异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}
	
	@RequestMapping(value = "/default", method = { RequestMethod.GET, RequestMethod.POST })
    public void setDefault(HttpServletRequest request, HttpServletResponse response) {
		try{
	        String accountNo = getString("accountNo");
	        logger.info("设置默认账号，accountNO:" + accountNo);
	        Result<?> result = platformService.setDefault(accountNo);
	        outPrint(response,JSON.toJSONString(result));
		}catch(Exception e){
			outPrint(response,JSON.toJSONString(Result.failureResult("设置默认账号出错")));
			logger.error("设置默认出错....",e);
		}
    }
	
	@RequestMapping(value = "/queryPlatFormEth", method = {RequestMethod.GET,RequestMethod.POST})
	public String edit(ModelMap model){
		String accountNo = getString("accountNo");
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(accountNo)) {
			params.put("no", accountNo);
		}
		
		EthAccountPlatformDo platFormAcct = platformService.queryPlatformAccount(params);
	    Map<String, String> retAcc = ethereumService.getBalance(platFormAcct.getAccount());
		
		model.put("balance", retAcc.get("balance"));
		if (retAcc != null) {
			model.put("balance", retAcc.get("balance"));
        }
        if (null != platFormAcct) {
        	model.put("accountNo", platFormAcct.getAccount());
        }
        
		return "/etherenum/center_edit";

	}
	
	/**
     * 平台以太坊帐户 转出
     * @return
     */
    @RequestMapping(value = "/tranPlatFormEth", method = { RequestMethod.GET, RequestMethod.POST })
    public void tranPlatFormEth(HttpServletResponse response) {
    	
    	try{
    		
    		String accountNo = getString("centerAccount");
    		String receiveAddress = getString("receiveAddress");
    		String amount = getString("amount");
    		String pwd = getString("password");
    		Result<?> result =  platformService.sendPlatform(accountNo, receiveAddress, amount, pwd);
    		outPrint(response,JSON.toJSONString(result));
    	}catch(Exception e){
    		logger.error("以太坊转出出错",e);
    		outPrint(response,JSON.toJSONString(Result.failureResult("以太坊转出出错")));
    	}
    }
    
    @RequestMapping(value = "/addPlatFormEth", method = {RequestMethod.GET,RequestMethod.POST})
    public String addPlatFormEth(){
    	
    	return "/etherenum/center_add";
    }
    
    @RequestMapping(value = "/setCenterAcc", method = { RequestMethod.GET, RequestMethod.POST })
    public void setCenterAcc(HttpServletResponse response) {
        String centerAccount = getString("centerAccount");
        String password = getString("password");
        try {
        	Result<?> result = platformService.createPlatformAccount(centerAccount, password);
        	outPrint(response, JSON.toJSONString(result));
        } catch (Exception e) {
        	logger.error("添加中心账号出错",e);
        	outPrint(response, Result.failureResult("添加中心账号出错"));
        }
    }
}
