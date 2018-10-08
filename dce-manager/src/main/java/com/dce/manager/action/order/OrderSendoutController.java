package com.dce.manager.action.order;

import java.util.Map;
import java.util.Date;
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
import com.dce.business.common.util.DateUtil;
import com.dce.business.entity.order.OrderSendOut;
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.service.order.IOrderSendoutService;
import com.dce.manager.action.BaseAction;



/**
 * @author  订单发货控制类
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/ordersendout")
public class OrderSendoutController extends BaseAction{
	
	@Resource
	private IOrderSendoutService orderSendoutService;

	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "ordersendout/listOrderSendout";
    }
	
    /**
     * 订单发货分页查询
     * @param pagination
     * @param model
     * @param response
     */
	@SuppressWarnings("unchecked")
	@RequestMapping("/listOrderSendout")
    public void listOrderSendout(NewPagination<OrderSendOut> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listOrderSendout----");
        try{
            PageDo<OrderSendOut> page = PageDoUtil.getPage(pagination);
            String startDate = getString("startDate");
            Map<String,Object> param = new HashMap<String,Object>();
            if(StringUtils.isNotBlank(startDate)){
                param.put("startDate",startDate);
                model.addAttribute("startDate",startDate);
            }
            String endDate = getString("endDate");
            if(StringUtils.isNotBlank(endDate)){
                param.put("endDate", endDate);
                model.addAttribute("endDate",endDate);
            }
            page = orderSendoutService.selectOrderSendByPage(param, page);
            pagination = PageDoUtil.getPageValue(pagination, page);
            
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
    @RequestMapping("/addOrderSendout")
    public String addOrderSendout(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----addOrderSendout----");
        try{
            if(StringUtils.isNotBlank(id)){
                OrderSendOut ordersendoutDo = orderSendoutService.getById(Long.valueOf(id));
                if(null != ordersendoutDo){
                    modelMap.addAttribute("ordersendout", ordersendoutDo);
                }
            }
            return "ordersendout/addOrderSendout";
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
    @RequestMapping("/saveOrderSendout")
    @ResponseBody
    public void saveOrderSendout(OrderSendOut ordersendout, 
    							  HttpServletRequest request, 
    							  HttpServletResponse response) {
        logger.info("----saveOrderSendout------");
        try{
            Long id = ordersendout.getId();
            Long userId = new Long(this.getUserId());
            
            int i = 0;
            if (id != null && id.intValue()>0) {
            	ordersendout.setOperatorId(userId);
            	ordersendout.setCreateTime(DateUtil.YYYY_MM_DD_MM_HH_SS.format(new Date()));
                i = orderSendoutService.updateOrderSendoutById(ordersendout);
            } else {
				ordersendout.setOperatorId(userId);
            	ordersendout.setCreateTime(DateUtil.YYYY_MM_DD_MM_HH_SS.format(new Date()));
            	
                i = orderSendoutService.addOrderSendout(ordersendout);
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
    }
	
}

