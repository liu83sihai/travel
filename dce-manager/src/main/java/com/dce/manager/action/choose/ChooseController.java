package com.dce.manager.action.choose;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.choose.ICommonChooseService;
import com.dce.manager.action.BaseAction;


/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/choose")
public class ChooseController extends BaseAction{
	
	@Resource
	private ICommonChooseService chooseService;
	
	
	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
    	String chooseType= this.getString("chooseType");
    	String retId = this.getString("retId");
    	String retText = this.getString("retText");
    	String dialogDivId = this.getString("dialogDivId");
    	
    	model.addAttribute("chooseType", chooseType);
    	model.addAttribute("retId", retId);
    	model.addAttribute("retText", retText);
    	model.addAttribute("dialogDivId", dialogDivId);
        return "choose/listChoose";
    }
	
	/**
     * 公共产品选择页面
     *
     * @return
     */
    @RequestMapping("/doChoose")
    public void doChoose(PageDo<Map<String,Object>> page,
    		                    ModelMap modelMap, 
    							HttpServletResponse response) {
        logger.info("----dochoose----");
        try{
        	String chooseType = this.getString("chooseType");
        	Map<String,Object> param = new HashMap<String,Object>();
        	param.put("chooseType", chooseType);

            String searchName = getString("searchName");
            if(StringUtils.isNotBlank(searchName)){
                param.put("searchName",searchName);
                modelMap.addAttribute("searchName",searchName);
            }
            String searchCode = getString("searchCode");
            if(StringUtils.isNotBlank(searchCode)){
                param.put("searchCode", searchCode);
                modelMap.addAttribute("searchCode",searchCode);
            }
            if(page.getCurrentPage() == null){
            	page.setCurrentPage(1L);
            }
            if(page.getPageSize()==null){
            	page.setPageSize(20L);
            }
            page = chooseService.getChoosePage(param, page);
            outPrint(response, JSONObject.toJSON(page));
        }catch(Exception e){
            logger.error("页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }
    

}
