package com.dce.manager.action.zhidu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dce.manager.action.BaseAction;

@Controller
@RequestMapping("/zhidu")
public class ZhiDuController extends BaseAction{
	
	
	/**
               * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
    	return "sysseting/award";
    }
    
}
