package com.dce.business.actions.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.entity.user.UserAddressDo;
import com.dce.business.service.user.UserAdressService;

@RestController
@RequestMapping("address")
public class UserAddressController extends BaseController {

	private final static Logger logger = Logger.getLogger(UserAddressController.class);

	@Resource
	private UserAdressService addressService;

	/**
	 *  @apiDefine address
	 *	@apiSuccess {java.lang.Integer}  addressid 地址id
	 *	@apiSuccess {java.lang.Integer}  userId 用户ID
	 *	@apiSuccess {java.lang.String}  username 收货姓名
	 *	@apiSuccess {java.lang.String}  userphone 收货电话
	 *	@apiSuccess {java.lang.String}  address 收货地址（省市区）
	 *	@apiSuccess {java.lang.Integer}  addressDetails 收货地址详情
	 */
	
	/** 
	 * @api {POST} /address/addAddress.do 添加收货地址
	 * @apiName addAddress
	 * @apiGroup address 
	 * @apiVersion 1.0.0 
	 * @apiDescription 添加收货地址
	 *  
	 * @apiParam  {java.lang.Integer}  userId 当前用户ID,用户登录ID
	 * @apiParam  {java.lang.Integer}  addressId 收货地址id，id为空是新记录 新增， 非空是已经存在的记录调用修改
	 * @apiParam  {java.lang.String}  userName 收货人姓名
	 * @apiParam  {java.lang.String}  userPhone 收货人手机
	 * @apiParam  {java.lang.String}  address 收货人所在的：省-市-区
	 * @apiParam  {java.lang.String}  addressDetails 收货地址详情
	 *   
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {}
	 *	}
	 */ 
	@RequestMapping(value = "/addAddress", method = { RequestMethod.POST })
	public Result<?> addAddress() {
		Integer userId = getUserId();
		String addressId = getString("addressId");
		String username = getString("userName");
		String userphone = getString("userPhone");
		String address = getString("address");
		String addressDetails = getString("addressDetails");

		Assert.hasText(username, "收货人不能为空");
		Assert.hasText(userphone, "收货人电话不能为空");
		Assert.hasText(address, "收货地址区域不能为空");
		Assert.hasText(addressDetails, "收货地址详情不能为空");

		// 收货地址对像
		UserAddressDo addressadd = new UserAddressDo();
		addressadd.setUserid(userId);
		addressadd.setUsername(username);
		addressadd.setUserphone(userphone);
		addressadd.setAddress(address);
		addressadd.setAddressDetails(addressDetails);

		// id为空是新记录 新增， 非空是已经存在的记录调用修改
		if (StringUtils.isNotBlank(addressId)) {
			addressadd.setAddressid(Integer.parseInt(addressId));
			addressadd.setUpdateTime(new Date());
			addressService.updateByPrimaryKeySelective(addressadd);
			return Result.successResult("地址修改成功");
		} else {
			// 判断添加的是否是第一条数据，否则修改地址
			// if(){
			addressadd.setCreatetime(new Date());
			addressService.insertSelective(addressadd);
			return Result.successResult("地址添加成功");
		}

	}

	/** 
	 * @api {GET} /address/listAddress.do 用户收货地址列表
	 * @apiName listAddress
	 * @apiGroup address 
	 * @apiVersion 1.0.0 
	 * @apiDescription 用户收货地址列表
	 *  
	 * @apiParam  {java.lang.Integer}  userId 当前用户ID,用户登录ID
	 *   
	 * @apiUse address
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {}
	 *	}
	 */ 
	@RequestMapping(value = "/listAddress", method = { RequestMethod.GET })
	public Result<?> listAddress() {
		// 获取当前用户的id
		Integer userId = getUserId();
		List<UserAddressDo> addressList = addressService.selectByUserId(userId);

		return Result.successResult("获取用户地址成功", addressList);
	}

	/** 
	 * @api {POST} /address/delAddress.do 删除地址
	 * @apiName delAddress
	 * @apiGroup address 
	 * @apiVersion 1.0.0 
	 * @apiDescription 删除地址
	 *  
	 * @apiParam  {java.lang.Integer}  addressid 地址主键ID
	 *   
	 * @apiUse address
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {}
	 *	}
	 */ 
	@RequestMapping(value = "/delAddress", method = { RequestMethod.POST })
	public Result<?> delAddress() {

		String addressid = getString("addressid");
		// 获取当前用户的id
//		Integer userId = getUserId();

		UserAddressDo addressadd = new UserAddressDo();
		if (StringUtils.isNotBlank(addressid)) {
			addressadd.setAddressid(Integer.parseInt(addressid));
			addressService.deleteByPrimaryKeyInt(Integer.parseInt(addressid));
		}else{
			return Result.failureResult("地址主键ID为空");
		}
		return Result.successResult("删除地址成功");
	}
}
