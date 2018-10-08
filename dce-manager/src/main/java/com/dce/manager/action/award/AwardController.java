package com.dce.manager.action.award;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.entity.award.AwardConfig;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.award.AwardConfigService;
import com.dce.business.service.award.AwardLaterService;
import com.dce.business.service.impl.award.AwardConfigServiceImpl;
import com.dce.business.service.user.IUserService;
import com.dce.manager.action.BaseAction;

@RestController
@RequestMapping("/award")
public class AwardController extends BaseAction {
	@Resource
	private AwardConfigService awardConfigService;
	@Resource
	private IUserService userService;
	@Resource
	private AwardLaterService awardLaterService;
	
	@RequestMapping(value = "/selectAwardConfig", method = { RequestMethod.GET, RequestMethod.POST })
	public List<Map<String, Object>> selectAwardConfig() {

		List<AwardConfig> list = awardConfigService.selectAward();

		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		if (!CollectionUtils.isEmpty(list)) {
			for (AwardConfig award : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("awardconfigId", award.getId());
				map.put("awardTypeName", award.getAwardtypename());
				listmap.add(map);
			}
		}
		return listmap;

	}

	@RequestMapping(value = "/addAwardConfig", method = { RequestMethod.GET, RequestMethod.POST })
	public void addAwardConfig() {
		String awardTypeName = getString("awardTypeName");
		AwardConfig record = new AwardConfig();
		record.setAwardtypename(awardTypeName);
		if (awardTypeName != null) {
			if (awardConfigService.insertSelective(record)) {
				logger.error("添加等级信息成功");
			} else {
				logger.error("添加等级信息失败");
			}
		} else {
			logger.error("awardTypeName为空");
		}

	}

	@RequestMapping(value = "/deleteAwardConfig", method = { RequestMethod.GET, RequestMethod.POST })
	public void deleteAwardConfig() {
		String id = getString("awardTypeid");
		AwardConfig record = new AwardConfig();
		Long awardTypeid = (long) Integer.parseInt(id);
		record.setId(awardTypeid);
		if (awardTypeid != null && awardTypeid != 0) {
			if (awardConfigService.deleteByPrimaryKey(awardTypeid)) {
				logger.error("删除等级信息成功");
			} else {
				logger.error("删除等级信息失败");
			}
		} else {
			logger.error("awardTypeid为空");
		}
	}

	@RequestMapping(value = "/updataAwardConfig", method = { RequestMethod.GET, RequestMethod.POST })
	public void updataAwardConfig() {
		String id = getString("awardTypeid");
		Long awardTypeid = (long) Integer.parseInt(id);
		String awardTypeName = getString("awardTypeName");
		AwardConfig record = new AwardConfig();
		record.setId(awardTypeid);
		record.setAwardtypename(awardTypeName);

		if (record != null && record.getAwardtypename() != null && record.getId() != null && record.getId() != 0) {
			if (awardConfigService.updateByPrimaryKeySelective(record)) {
				logger.error("修改等级信息成功");
			} else {
				logger.error("修改等级信息失败");
			}
		} else {
			logger.error("获取信息空值");
		}

	}

/*	@RequestMapping(value = "/selectAward", method = { RequestMethod.GET, RequestMethod.POST })
	private List<Map<String, Object>> selectAward() {

		String userid = getString("userid");

		return null;

	}

	@RequestMapping(value="/deleteAward",method={RequestMethod.GET,
	  RequestMethod.POST}) public void deleteAward(){
	  
	  String id=getString("awardid"); Integer awardid= Integer.parseInt(id);
	  if(id!=null){ //调用方法 if(awardLaterService.deleteByPrimaryKey(awardid)){
	  
	  logger.error("删除奖励记录成功"); }else{ logger.error("删除奖励记录失败"); } }else

	{
		logger.error("获取awardid为空");
	}
	}

	@RequestMapping(value = "/addAward", method = { RequestMethod.GET, RequestMethod.POST })
	public void addAward() {
		int count = Integer.parseInt(getString("count"));
		String id = getString("userId");
		Integer userId = Integer.parseInt(id); // 获取用户信息
		UserDo userdo = userService.getUser(userId); // 获取推荐人信息 UserDo
		userReferrer = userService.getUser(userdo.getRefereeid()); // 获取用户等级 int
		memberUser = userdo.getUserLevel(); // 获取推荐人等级 int
		memberReferrer = userReferrer.getUserLevel();
		// 判断用户是否有推荐人，没有推荐人为false,和推荐人等级，为普通用户为false
		if (userdo.getRefereeid() != null && memberReferrer != 0) {

		} else { // 第一个参数，用户的级别，第二个参数购买的数量 userupgrade(1,count); }

		}

		// 判断用户是否可以升级方法 public String userupgrade(int rank,int count){
		// //判断用户等级优惠
		if (rank == 0) {// 普通用户 //普通用户升级

		} else if (rank == 1 && count >= 5) {// 会员用户 //会员升级

		} else if (rank == 2 && count >= 50) {// vip用户 //vip升级

		} else if (rank == 3) {// 城市合伙人用户 //调用用户查询方法，查看用户推荐了几个城市合伙人，是否有5个

		} else {

		}

		return null;

	}

	// 判断用户推荐奖金多少,第一个参数奖励人的等级，购买的数量 public boolean bonus(UserDo userdo,int
	count,

	UserDo referrerdao)
	{ //判断推荐人是否是会员
	  if(referrerdao.getUserLevel()==1){ //查询会员是否享用过，调用查询奖励记录来判断
	  if(awardLaterService.selectByPrimaryKey(referrerdao.getId())==null){
	  
	  } }else{
	  
	  //判断用户等级,是否是会员 if(referrerdao.getUserLevel()==1){ if(count>=5){
	  //写会员升级vip 分销会员的奖励，还要判断数量是否大于5，5盒按分销会员奖励，其他的按300每盒处理 count=count-5; }
	  
	  
	  }else{ //不是会员，则按每盒300奖励
	  
	  
	  
	  } }

	return false;

}*/

}
