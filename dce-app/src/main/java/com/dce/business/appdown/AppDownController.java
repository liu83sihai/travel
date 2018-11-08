package com.dce.business.appdown;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dce.business.actions.common.BaseController;


/**
 * 账户处理器，注册、登录等
 * 
 * @author parudy
 * @date 2018年3月24日
 * @version v1.0
 */
@RestController
@RequestMapping("/appdown")
public class AppDownController extends BaseController {
	

	@RequestMapping(value = "/down", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView down() {
        ModelAndView mav = new ModelAndView("appdown/appdown");
        return mav;
    }

}

