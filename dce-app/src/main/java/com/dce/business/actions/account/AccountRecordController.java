package com.dce.business.actions.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.dce.business.actions.common.BaseController;
import com.dce.business.common.util.DateUtil;
import com.dce.business.entity.account.UserAccountDetailDo;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.account.IAccountService;

/**
 * 交易流水控制类
 * @author Administrator
 *
 */
@RestController
@RequestMapping("accountRecord")
public class AccountRecordController extends BaseController {

	private final static Logger logger = Logger.getLogger(AccountController.class);
	
	@Resource
	private IAccountService accountService;
	
	/**
	 * 获取用户交易流水记录
	 * @return
	 */
	/** 
	 * @api {POST} /accountRecord/selectAccountRecord.do 券账户详情
	 * @apiName accountRecord
	 * @apiGroup accountRecord 
	 * @apiVersion 1.0.0 
	 * @apiDescription 根据券账户类别查询详情
	 * 
	 * @apiParam {String} userId 用户id
	 * @apiParam {String} accountType  券账户类别 “wallet_money”：”现金券账户” “wallet_travel”： “换购积分券账户” “wallet_goods”： “抵用券账户”
	 * 
	 * @apiSuccess {int}id	int	主键
	*  @apiSuccess {int}userId	int	用户id
	*  @apiSuccess {Decimal}balance	账户余额
	*  @apiSuccess {Decimal}amount	交易金额
	*  @apiSuccess {int}moreOrLess	int	增加/减少
	*  @apiSuccess {int}userId	int	用户id
	*  @apiSuccess {Decimal}balanceAmount	余额
	*  @apiSuccess {String}remark	String	交易操作（提现、商城消费）
	*  @apiSuccess {String}createTime	String	交易时间
	*  @apiSuccess {String}accountType	String	交易账户类型：“wallet_money”：”现金券账户” “wallet_travel”： “换购积分券账户” “wallet_goods”： “抵用券账户”
	*  
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 * HTTP/1.1 200 OK 
	 * * {
	*    "msg": "获取交易流水记录成功",
	*    "code": "0",
	*    "balance": 50000,
	*    "data": [
	*        {
	*            "amount": 200,
	*            "createTime": "2018-08-13",
	*            "moreOrLess": "+",
	*            "remark": "提现",
	*            "id": 1657,
	*            "balanceAmount": 44000,
	*            "userId": 37,
	*            "seqId": "205d9eb1-ab94-4abc-abc4-451841690fbd"
	*        },
	*        {
	*            "amount": 45000,
	*            "createTime": "2018-08-03",
	*            "moreOrLess": "+",
	*            "remark": "商城消费",
	*            "id": 1631,
	*            "balanceAmount": 660000,
	*            "userId": 37,
	*            "accountType": "wallet_money"
	*        }
	*    ]
	*  }
	**/
	@RequestMapping(value = "/selectAccountRecord", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> selectAccountRecord(){
		
		Integer userId = getUserId();
		String accountType = getString("accountType"); 
		logger.info("流水查询 获取当前用户的id："+userId);
		
		
		//当前账户总额度
		Map<String,Object> map = new HashMap<String,Object>();
		UserAccountDo currentUserAccountDo = accountService.selectUserAccount(userId, accountType==null?"wallet_money":accountType);
		map.put("balance", currentUserAccountDo.getAmount()); //当前用户的账户余额
		
		//查询账户流水
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("userId", userId);
		paraMap.put("accountType", accountType);
		/*
		 * 一次查询出来，页面记录数据太大， 死掉
		List<UserAccountDetailDo> list = accountService.selectUserAccountDetailByUserId(paraMap);
		*/
		PageDo<UserAccountDetailDo> paraPage  = new  PageDo<UserAccountDetailDo> (); 
		paraPage.setCurrentPage(1L);
		paraPage.setPageSize(100L);
		PageDo<UserAccountDetailDo> page= accountService.selectUserAccountDetailByPage(paraPage,paraMap);
		
		List<UserAccountDetailDo> list  = page.getModelList();
		
		
		if(list.isEmpty() || list.size() ==0){
			map.put("code", "0");
			map.put("msg", "用户交易流水记录为空");
			map.put("data", new JSONArray());
			return map;
		}
		
		
		map.put("data", list);
		
		return map;
	}
	
}
