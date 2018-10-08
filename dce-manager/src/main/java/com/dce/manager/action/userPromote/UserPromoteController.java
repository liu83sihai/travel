/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.manager.action.userPromote;

import java.util.Map;

import java.util.HashMap;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.entity.user.UserPromoteDo;
import com.dce.business.service.user.IUserPromoteService;
import com.dce.manager.action.BaseAction;



/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/userpromote")
public class UserPromoteController extends BaseAction{
	//默认多列排序,example: username desc,createTime asc
	//protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Resource
	private IUserPromoteService userPromoteService;

	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "userpromote/listUserPromote";
    }
	
	@RequestMapping("/listUserPromote")
    public void listUserPromote(NewPagination<UserPromoteDo> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listUserPromote----");
        try{
            PageDo<UserPromoteDo> page = PageDoUtil.getPage(pagination);
            String companyName = getString("searchPolicyName");
            Map<String,Object> param = new HashMap<String,Object>();
            if(StringUtils.isNotBlank(companyName)){
                param.put("policyName",companyName);
                model.addAttribute("searchPolicyName",companyName);
            }
            String managerName = getString("searManagerName");
            if(StringUtils.isNotBlank(managerName)){
                param.put("managerName", managerName);
                model.addAttribute("searManagerName",managerName);
            }
           page = userPromoteService.getUserPromotePage(param, page);
           /*  List<CommonComboxConstants> statusList = CommonComboxConstants.getStatusList();
            model.addAttribute("statusList", statusList);*/
            pagination = PageDoUtil.getPageValue(pagination, page);
           
            for(UserPromoteDo promote:pagination.getDatas()){
            	if(promote.getUserLevel().equals("0")){
            		promote.setUserLevel("普通用戶");
            	}else if(promote.getUserLevel().equals("1")){
            		promote.setUserLevel("会员用戶");
            	}else if (promote.getUserLevel().equals("2")){
            		promote.setUserLevel("VIP用戶");
            	}else if (promote.getUserLevel().equals("3")){
            		promote.setUserLevel("城市合伙人用戶");
            	}else if (promote.getUserLevel().equals("4")){
            		promote.setUserLevel("股东");
            	}
            	
            	if(promote.getPromoteLevel().equals("0")){
            		promote.setPromoteLevel("普通用戶");
            	}else if(promote.getPromoteLevel().equals("1")){
            		promote.setPromoteLevel("会员用戶");
            	}else if (promote.getPromoteLevel().equals("2")){
            		promote.setPromoteLevel("VIP用戶");
            	}else if (promote.getPromoteLevel().equals("3")){
            		promote.setPromoteLevel("城市合伙人用戶");
            	}else if (promote.getPromoteLevel().equals("4")){
            		promote.setPromoteLevel("股东");
            	}
            }
            System.err.println("数据---"+JSONObject.toJSON(pagination));
            outPrint(response, JSONObject.toJSON(pagination));
        }catch(Exception e){
            logger.error("查询清单异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }
    }
	
	
	  
    /**
     * 编辑页面
     *
     * @return
     */
    @RequestMapping("/addUserPromote")
    public String addUserPromote(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----addUserPromote----");
        try{
            if(StringUtils.isNotBlank(id)){
                UserPromoteDo userpromoteDo = userPromoteService.getById(Integer.valueOf(id));
                if(userpromoteDo.getUserLevel().equals("0")){
                	userpromoteDo.setUserLevel("普通用戶");
            	}else if(userpromoteDo.getUserLevel().equals("1")){
            		userpromoteDo.setUserLevel("会员用戶");
            	}else if (userpromoteDo.getUserLevel().equals("2")){
            		userpromoteDo.setUserLevel("VIP用戶");
            	}else if (userpromoteDo.getUserLevel().equals("3")){
            		userpromoteDo.setUserLevel("城市合伙人用戶");
            	}else if (userpromoteDo.getUserLevel().equals("4")){
            		userpromoteDo.setUserLevel("股东");
            	}
            	
            	if(userpromoteDo.getPromoteLevel().equals("0")){
            		userpromoteDo.setPromoteLevel("普通用戶");
            	}else if(userpromoteDo.getPromoteLevel().equals("1")){
            		userpromoteDo.setPromoteLevel("会员用戶");
            	}else if (userpromoteDo.getPromoteLevel().equals("2")){
            		userpromoteDo.setPromoteLevel("VIP用戶");
            	}else if (userpromoteDo.getPromoteLevel().equals("3")){
            		userpromoteDo.setPromoteLevel("城市合伙人用戶");
            	}else if (userpromoteDo.getPromoteLevel().equals("4")){
            		userpromoteDo.setPromoteLevel("股东");
            	}
                
                
                if(null != userpromoteDo){
                    modelMap.addAttribute("userpromote", userpromoteDo);
                }
            }
            return "userpromote/addUserPromote";
        }catch(Exception e){
            logger.error("跳转到数据字典编辑页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    /**
     * 保存更新
     *
     * @return
     * @author: huangzlmf
     * @date: 2015年4月21日 12:49:05
     */
    @RequestMapping("/saveUserPromote")
    @ResponseBody
    public void saveUserPromote(UserPromoteDo userpromoteDo, 
    							  HttpServletRequest request, 
    							  HttpServletResponse response) {
        logger.info("----saveUserPromote------");
        try{
            Integer id = userpromoteDo.getPromoteId();
            int i = 0;
            if (id != null && id.intValue()>0) {
            	userpromoteDo.setPromoteId(id);
            //	userpromoteDo.setUpdateTime(new Date());
                i = userPromoteService.updateUserPromoteById(userpromoteDo);
            } else {
            	userpromoteDo.setPromoteId(id);
            //	userpromoteDo.setCreateTime(new Date());
            	
                i = userPromoteService.addUserPromote(userpromoteDo);
            }

            if (i <= 0) {
                outPrint(response,this.toJSONString(Result.failureResult("操作失败")));
                return;
            }
            outPrint(response, this.toJSONString(Result.successResult("操作成功")));
        }catch(Exception e){
            logger.error("保存更新失败",e);
            outPrint(response, this.toJSONString(Result.failureResult("操作失败")));
        }
        logger.info("----end saveUserPromote--------");
    }
  /*  
	*//**
     * 导出数据
     *//*
    @RequestMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        try {
            Long time = System.currentTimeMillis();
            UserPromoteExample example  = new UserPromoteExample();
            String companyName = getString("searchPolicyName");
          
            if(StringUtils.isNotBlank(companyName)){
                example.createCriteria().andPolicyNameLike(companyName);
            }
            String managerName = getString("searManagerName");
            if(StringUtils.isNotBlank(managerName)){
            	example.createCriteria().andManagerNameEqualTo(managerName);
            }
            List<UserPromoteDo> userpromoteLst = userpromoteService.selectUserPromote(example);
            
            String excelHead = "数据导出";
            String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String fileName = URLEncoder.encode(excelHead + date + ".xls", "utf-8");
            List<String[]> excelheaderList = new ArrayList<String[]>();
            String[] excelheader = { "保险公司名称", "保险公司简称", "联系人姓名", "联系人手机号码", "跟进单员", "合作状态", "记录状态" };
            excelheaderList.add(0, excelheader);
            String[] excelData = { "policyName", "shortName", "contactName", "contactPhone", "managerName", "partnerStatus", "status" };
            HSSFWorkbook wb = ExeclTools.execlExport(excelheaderList, excelData, excelHead, userpromoteLst);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            wb.write(response.getOutputStream());
            time = System.currentTimeMillis() - time;
            logger.info("导出数据，导出耗时(ms)：" + time);
        } catch (Exception e) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("下载失败");
            logger.error("导出数据，Excel下载失败", e);
            logger.error("导出数据异常", e);
            throw new BusinessException("系统繁忙，请稍后再试");
        } finally {
            response.flushBuffer();
        }

    }
	*/
}

