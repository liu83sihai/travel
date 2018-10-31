package com.dce.business.action.travel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.entity.travel.TravelDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.travel.ICheckTravelService;
import com.dce.business.service.travel.ITravelApplyService;
import com.dce.business.service.travel.ITravelPathService;
import com.dce.business.service.user.IUserService;

@RestController
@RequestMapping("/travelApply")
public class TravelApplyController extends BaseController {
	private final static Logger logger = Logger.getLogger(TravelApplyController.class);

	// 旅游申请
	@Resource
	private ITravelApplyService travelApplyService;

	// 查看活动
	@Resource
	private ICheckTravelService checkTravelService;

	// 旅游路线
	@Resource
	private ITravelPathService travelPathService;

	// 用户信息
	@Resource
	private IUserService userService;

	/**
	 * 旅游申请
	 * 
	 * @return
	 */
	/** 
	 * @api {POST} /travelApply/addApply.do 旅游申请
	 * @apiName addApply
	 * @apiGroup travel 
	 * @apiVersion 1.0.0 
	 * @apiDescription 旅游申请
	 * 
	 * @apiSuccess {int}  id	int(11)	是		申请信息id
	*  @apiSuccess {int}  userId	int	是		申请人id
	*  @apiSuccess {String}  name	varcahr(50)	是		申请人姓名
	*  @apiSuccess {int}  sex	int	是		申请人性别(0/1 男/女)
	*  @apiSuccess {String}  nation	Varchar(50)	是		民族
	*  @apiSuccess {String}  identity	Varchar(50)	是		身份证
	*  @apiSuccess {String}  phone	Varchar(50)	是		手机号码
	*  @apiSuccess {String}  address	Varchar(200)	是		地址
	*  @apiSuccess {int}  IsBenn	Int	是		是否去过该路线0是/1否
	*  @apiSuccess {int}  people	int	是		同行人数
	*  @apiSuccess {int}  pathid	int	是		路线id
	*  
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 * HTTP/1.1 200 OK 
	 * * {
	*    "msg": "获取交易流水记录成功",
	*    "code": "0",
	*    "balance": 50000,
	*    "data": 
	*        {
	*           
	*        }        
	*  }
	**/
	@RequestMapping(value = "/addApply", method = RequestMethod.POST)
	public Result<?> addApply() {
		logger.info("申请旅游....");

		TravelDo travel = new TravelDo();

		// 获取用户id
		Integer userId = getUserId();
		UserDo user = new UserDo();
		user = userService.getUser(userId);
		if (user == null) {
			return Result.failureResult("用户不存在,请重新登陆");
		} else {
			// 获取前台传过来的用户信息
			String name = getString("name") == null ? "" : getString("name");
			String sex = getString("sex") == null ? "" : getString("sex");
			String nation = getString("nation") == null ? "" : getString("nation");
			String identity = getString("identity") == null ? "" : getString("identity");
			String phone = getString("phone") == null ? "" : getString("phone");
			String address = getString("address") == null ? "" : getString("address");
			String pathid = getString("pathId") == null ? "" : getString("pathId");
			String isBenn = getString("IsBenn") == null ? "" : getString("IsBenn");
			String people = getString("people") == null ? "" : getString("people");
			if (name == null || name == "" || sex == null || sex == "" || nation == null || nation == ""
					|| identity == null || identity == "" || phone == null || phone == "" || address == null
					|| address == "" || pathid == null || pathid == "" || isBenn == null || isBenn == ""
					|| people == null || people == "") {
				return Result.failureResult("参数不能为空");
			}
			travel.setUserid(userId);
			travel.setName(name);
			travel.setSex(sex);// 性别(0/1 男/女)
			travel.setNation(nation);
			travel.setIdentity(identity);
			travel.setPhone(phone);
			travel.setAddress(address);
			travel.setPathid(Integer.parseInt(pathid));
			travel.setIsbeen(isBenn); // 是否去过该路线 0是/1否
			travel.setPeople(Integer.parseInt(people));
			travel.setState("1");

			Result<?> result = travelApplyService.travelApply(travel);

			return result;
		}
	}

	@RequestMapping(value = "/checkTravel", method = RequestMethod.GET)
	public Result<?> checkTravel() {
		logger.info("查看活动....");

		// 获取用户id
		Integer userId = getUserId();

		List<TravelDo> travelList = checkTravelService.userApplyTravelList(userId);
		List<Map<String, Object>> result = new ArrayList<>();
		if (!CollectionUtils.isEmpty(travelList)) {
			for (TravelDo message : travelList) {

				Map<String, Object> map = new HashMap<>();
				map.put("id", message.getId());
				// 获取到路线id
				int pathid = message.getPathid();
				// 调用travelPathService查询出对应路线id的对象 并取出路线名称
				String lineName = (travelPathService.selectByPrimaryKey(pathid)).getLinename();
				map.put("id", message.getId());
				map.put("lineName", lineName);
				map.put("createTime", message.getCreatetime());
				map.put("state", message.getState()); // 状态 (已开发0/马上推出1/正在开发2)
				result.add(map);
			}
		}

		return Result.successResult("查询成功", result);

	}

	@RequestMapping(value = "/ravelRevoke", method = RequestMethod.POST)
	public Result<?> rravelRevoke(String id) {
		logger.info("撤销申请....");
		logger.debug(id);
		Result<?> result = travelApplyService.ravelRevokeById(Integer.parseInt(id));
		return result;

	}

}
