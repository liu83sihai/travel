package com.dce.business.actions.bonus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dce.business.actions.common.BaseController;

@RequestMapping("/bonus")
public class BonusController extends BaseController {
	
	
	/** 
     * 查询奖金
     * @return  
     */
    @RequestMapping(value = "/listBonus", method = RequestMethod.POST)
    public String listBonus() {
    	
    	return "/bonus";
    	
    }
}
