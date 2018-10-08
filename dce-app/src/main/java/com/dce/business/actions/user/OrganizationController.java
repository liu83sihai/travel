package com.dce.business.actions.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.entity.user.UserParentDo;
import com.dce.business.service.user.IUserService;

@RestController
@RequestMapping("/organiza")
public class OrganizationController extends BaseController {
	
	//注入
	@Resource
	private IUserService userService;
	 private final static Logger logger = Logger.getLogger(OrganizationController.class);

	@RequestMapping(value = "/ztree", method = {RequestMethod.GET,RequestMethod.POST})
	public  List<UserParentDo>  ztree(){
		
		/*String userId = getString("userId");
		String ts = getString("ts");
		String sign = getString("sign");
		getRequest().getSession().setAttribute("hadReq", "1");
		ModelAndView mav = new ModelAndView("ztree");
		mav.addObject("userId", userId);
		mav.addObject("ts",ts);
		mav.addObject("sign",sign);
		return mav;*/
		
		String userId=getString("userId");
		
		Map<String, Object>map=new HashMap<String,Object>();
		
		map.put("userId", userId);

		 List<UserParentDo>  userRefereeDo=userService.getMyMember(map);
		 
		 System.err.println("数据----"+userRefereeDo);
		
		logger.info("团员信息-----》》》"+userRefereeDo);
		 
		 return userRefereeDo;
	}

}
