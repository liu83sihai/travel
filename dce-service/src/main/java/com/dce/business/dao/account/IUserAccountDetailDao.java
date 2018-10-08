																																																						
package com.dce.business.dao.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dce.business.common.enums.IncomeType;
import com.dce.business.entity.account.UserAccountDetailDo;

@Repository
public interface IUserAccountDetailDao {

    /**
     * 根据ID 查询
     * @parameter id
     */
    public UserAccountDetailDo getById(int id);

    /**
     *根据条件查询列表
     */
    public List<UserAccountDetailDo> selectUserAccountDetail(Map<String, Object> parameterMap);
    
    /**
     * 根据用户id查询当前用户的流水
     * @param userId
     * @return
     */
    public List<UserAccountDetailDo> selectUserAccountDetailByUserId(Map map);

    /**
     * 更新
     */
    public int updateUserAccountDetailById(UserAccountDetailDo newUserAccountDetailDo);

    /**
     * 新增
     */
    public int addUserAccountDetail(UserAccountDetailDo newUserAccountDetailDo);

    /**
     * 删除
     */
    public int deleteById(int id);

    /**
     * 取最后一次转换记录
     * @param userId
     * @return
     */
    public Map<String, Object> selectLastTransPoint(Long userId);

    /**
     * 查询可转换金额
     * @param paraMap
     * @return
     */
    public BigDecimal selectUserTransPoint(Map<String, Object> paraMap);

    /**
     * 积分释放记录
     * @param jifengAmt
     * @param pointAmt
     * @param userId
     * @param endDate 转换截止日期
     * @return
     */
    public int insertPointTranRec(@Param("tranPoint") BigDecimal jifengAmt, @Param("totalPoint") BigDecimal pointAmt, @Param("userId") Long userId,
            @Param("endDate") Date endDate);

    /**
     * 奖金豆转换记录
     * @param jifengAmt
     * @param pointAmt
     * @param userId
     * @param optType 
     * @return
     */
    public int insertPointTranRecJjd(@Param("tranPoint") BigDecimal jifengAmt, @Param("totalPoint") BigDecimal pointAmt, @Param("userId") Long userId,
            @Param("optType") String optType);

    /**
     * 转让记录
     * @param sourceUserId
     * @param targetUserId
     * @param amount
     * @param fromAccount
     * @param toAccount
     * @param sourceMsg
     */
	public void addAccountTranLog(@Param("seqId")String seqId,
								  @Param("sourceUserId")Integer sourceUserId, 
			                      @Param("targetUserId")Integer targetUserId,
			                      @Param("amount")BigDecimal amount,  
			                      @Param("fromAccount")String fromAccount, 
			                      @Param("toAccount")String toAccount,
			                      @Param("sourceMsg")IncomeType sourceMsg);
	
	/**
	 * 分页查询用户账户详细信息
	 * @param parameterMap
	 * @return
	 */
	public List<UserAccountDetailDo> selectUserAccountDetailByPage(Map<String, Object> parameterMap);

}
