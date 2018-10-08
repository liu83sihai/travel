package com.dce.business.service.award;

import java.util.List;

import com.dce.business.entity.award.AwardConfig;

public interface AwardConfigService {
	

	/**
	 * 条件删除某个等级
	 * @param id
	 * @return
	 */
    boolean deleteByPrimaryKey(Long id);

    /**
     * 添加一个等级
     * @param record
     * @return
     */
    boolean insert(AwardConfig record);

    /**
     * 条件添加等级
     * @param record
     * @return
     */
    boolean insertSelective(AwardConfig record);

    /**
     * 根据id条件查询等级
     * @param id
     * @return
     */
    AwardConfig selectByPrimaryKey(Long id);
    
    /**
     * 查询全部等级信息
     */
    List<AwardConfig>   selectAward();

    /**
     * id条件修改等级部分地方
     * @param record
     * @return
     */
    boolean updateByPrimaryKeySelective(AwardConfig record);

    /**
     * id条件修改等级全部地方
     * @param record
     * @return
     */
    boolean updateByPrimaryKey(AwardConfig record);


    /**
     * 用户升级方法
     * userid  购买者id
     * count 数量
     */
    
    Integer userUpgrade(Integer userid,int count);
    
    /**
     * 判断用户是否是否有资格升级为股东
     * userid用户id（成为城市合伙人的推荐人id）
     */
    
    boolean  upgradePartner(Integer userid);
    
    /**
     * 发放总奖励方法接口
     * userid  购买者id
     * count   数量
     * addressid  地址id
     */
    boolean  grantAward(int userid,int count,Integer addressid);
    /**
     * 商品区域奖方法
     * addressid 地址id
     * count 数量
     * 
     */
    boolean areaAward(int addressid,int count);
    
    
    
}
