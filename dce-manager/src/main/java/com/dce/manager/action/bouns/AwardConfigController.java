package com.dce.manager.action.bouns;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dce.business.common.enums.DictCode;
import com.dce.business.entity.dict.LoanDictDtlDo;
import com.dce.business.service.dict.ILoanDictService;


@RestController
@RequestMapping("/awardConfig")
public class AwardConfigController {

	@Resource
	ILoanDictService loanDictService;
	
	@RequestMapping(value = "/query", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView query(ModelMap modelMap){
		ModelAndView mav = new ModelAndView("bouns/award");
		
		List<LoanDictDtlDo> BaoDanFei = loanDictService.queryDictDtlListByDictCode(DictCode.BaoDanFei.getCode());
		modelMap.addAttribute("BaoDanFei", BaoDanFei);
		
		List<LoanDictDtlDo> ProfitRate = loanDictService.queryDictDtlListByDictCode(DictCode.ProfitRate.getCode());
		for(LoanDictDtlDo dtl : ProfitRate){
			dtl = convertBL(dtl);
		}
		modelMap.addAttribute("ProfitRate", ProfitRate);
		
		List<LoanDictDtlDo> ShareRate = loanDictService.queryDictDtlListByDictCode(DictCode.ShareRate.getCode());
		for(LoanDictDtlDo dtl : ShareRate){
			dtl = convertBL(dtl);
		}
		modelMap.addAttribute("ShareRate", ShareRate);
		
		List<LoanDictDtlDo> FengDin = loanDictService.queryDictDtlListByDictCode(DictCode.FengDin.getCode());
		modelMap.addAttribute("FengDin", FengDin);
		
		List<LoanDictDtlDo> ZhiTui = loanDictService.queryDictDtlListByDictCode(DictCode.ZhiTui.getCode());
		for(LoanDictDtlDo dtl : ZhiTui){
			dtl = convertBL(dtl);
		}
		modelMap.addAttribute("ZhiTui", ZhiTui);
		
		List<LoanDictDtlDo> OrigShiFangRate = loanDictService.queryDictDtlListByDictCode(DictCode.OrigShiFangRate.getCode());
		for(LoanDictDtlDo dtl : OrigShiFangRate){
			dtl = convertBL(dtl);
		}
		modelMap.addAttribute("OrigShiFangRate", OrigShiFangRate);
		
		List<LoanDictDtlDo> DaysShiFangRate = loanDictService.queryDictDtlListByDictCode(DictCode.DaysShiFangRate.getCode());
		for(LoanDictDtlDo dtl : DaysShiFangRate){
			dtl = convertBL(dtl);
		}
		modelMap.addAttribute("DaysShiFangRate", DaysShiFangRate);
		
		List<LoanDictDtlDo> AwardShiFangRate = loanDictService.queryDictDtlListByDictCode(DictCode.AwardShiFangRate.getCode());
		for(LoanDictDtlDo dtl : AwardShiFangRate){
			dtl = convertBL(dtl);
		}
		modelMap.addAttribute("AwardShiFangRate", AwardShiFangRate);
		
		LoanDictDtlDo ZhiTuiTime = loanDictService.getLoanDictDtl("ZhiTuiTime");
		modelMap.addAttribute("ZhiTuiTime", ZhiTuiTime);
		
		LoanDictDtlDo Point2RMB = loanDictService.getLoanDictDtl("Point2RMB");
		modelMap.addAttribute("Point2RMB", Point2RMB);
		
		LoanDictDtlDo WithDrawFee = loanDictService.getLoanDictDtl("WithDrawFee");
		WithDrawFee = convertBL100(WithDrawFee);
		modelMap.addAttribute("WithDrawFee", WithDrawFee);
		
		List<LoanDictDtlDo> ScoreShiFangRate = loanDictService.queryDictDtlListByDictCode(DictCode.ScoreShiFangRate.getCode());
		for(LoanDictDtlDo dtl : ScoreShiFangRate){
			dtl = convertBL100(dtl);
		}
		modelMap.addAttribute("ScoreShiFangRate", ScoreShiFangRate);
		
		LoanDictDtlDo CashToIBAC = loanDictService.getLoanDictDtl("CashToIBAC");
		modelMap.addAttribute("CashToIBAC", CashToIBAC);
		
		LoanDictDtlDo ScoreToIBAC = loanDictService.getLoanDictDtl("ScoreToIBAC");
		modelMap.addAttribute("ScoreToIBAC", ScoreToIBAC);
		
		LoanDictDtlDo SecondShiFangRate = loanDictService.getLoanDictDtl("SecondShiFangRate");
		SecondShiFangRate = convertBL(SecondShiFangRate);
		modelMap.addAttribute("SecondShiFangRate", SecondShiFangRate);
		
		return mav;
	}
	
	private LoanDictDtlDo convertBL(LoanDictDtlDo dtl){
		BigDecimal remark = new BigDecimal(dtl.getRemark()).multiply(new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP);
		dtl.setRemark(subZeroAndDot(remark.toString()));
		return dtl;
	}
	
	private LoanDictDtlDo convertBL100(LoanDictDtlDo dtl){
		BigDecimal remark = new BigDecimal(dtl.getRemark()).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
		dtl.setRemark(subZeroAndDot(remark.toString()));
		return dtl;
	}
	
	  /** 
     * 使用java正则表达式去掉多余的.与0 
     * @param s 
     * @return  
     */  
    private String subZeroAndDot(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
    }
    
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView save(HttpServletRequest request){
		
		Enumeration<String> paraNames=request.getParameterNames();
		
		List<LoanDictDtlDo> listDicts = new ArrayList<LoanDictDtlDo>();
		
		for(Enumeration<String> e=paraNames;e.hasMoreElements();){
		      String thisName=e.nextElement().toString();
		      String thisValue=request.getParameter(thisName);
		      String[] names = thisName.split("-");
		      
		      
		      if(thisName.startsWith("dictDtlId")){
		    	  LoanDictDtlDo dtl = new LoanDictDtlDo();
			      dtl.setId(Long.parseLong(names[2]));
			      
			      //rate结尾的要换算成小数
			      if(thisName.endsWith("-rate")){
			    	  BigDecimal _t = new BigDecimal(thisValue).divide(new BigDecimal(1000), 4, RoundingMode.HALF_UP);
			    	  dtl.setRemark(_t.toString());
			      }else if(thisName.endsWith("-rate100")){
			    	  BigDecimal _t = new BigDecimal(thisValue).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
			    	  dtl.setRemark(_t.toString());
			      }else{
			    	  
			    	  dtl.setRemark(thisValue);
			      }
			      
			      listDicts.add(dtl);
		      }
		      System.out.println("params:thisName=" + thisName + ",thisValue=" + thisValue);
		}
		
		loanDictService.updateDictDtlRemark(listDicts);
		
		return new ModelAndView("redirect:/awardConfig/query.html");
	}
}
