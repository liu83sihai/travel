package com.dce.business.dao.etherenum;

import com.dce.business.entity.etherenum.EthAccountDetailDo;
import com.dce.business.entity.etherenum.EthAccountDetailDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EthAccountDetailDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_ethereum_account_detail
     *
     * @mbg.generated Sat Aug 11 12:12:00 CST 2018
     */
    long countByExample(EthAccountDetailDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_ethereum_account_detail
     *
     * @mbg.generated Sat Aug 11 12:12:00 CST 2018
     */
    int deleteByExample(EthAccountDetailDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_ethereum_account_detail
     *
     * @mbg.generated Sat Aug 11 12:12:00 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_ethereum_account_detail
     *
     * @mbg.generated Sat Aug 11 12:12:00 CST 2018
     */
    int insert(EthAccountDetailDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_ethereum_account_detail
     *
     * @mbg.generated Sat Aug 11 12:12:00 CST 2018
     */
    int insertSelective(EthAccountDetailDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_ethereum_account_detail
     *
     * @mbg.generated Sat Aug 11 12:12:00 CST 2018
     */
    List<EthAccountDetailDo> selectByExample(EthAccountDetailDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_ethereum_account_detail
     *
     * @mbg.generated Sat Aug 11 12:12:00 CST 2018
     */
    EthAccountDetailDo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_ethereum_account_detail
     *
     * @mbg.generated Sat Aug 11 12:12:00 CST 2018
     */
    int updateByExampleSelective(@Param("record") EthAccountDetailDo record, @Param("example") EthAccountDetailDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_ethereum_account_detail
     *
     * @mbg.generated Sat Aug 11 12:12:00 CST 2018
     */
    int updateByExample(@Param("record") EthAccountDetailDo record, @Param("example") EthAccountDetailDoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_ethereum_account_detail
     *
     * @mbg.generated Sat Aug 11 12:12:00 CST 2018
     */
    int updateByPrimaryKeySelective(EthAccountDetailDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_ethereum_account_detail
     *
     * @mbg.generated Sat Aug 11 12:12:00 CST 2018
     */
    int updateByPrimaryKey(EthAccountDetailDo record);
}