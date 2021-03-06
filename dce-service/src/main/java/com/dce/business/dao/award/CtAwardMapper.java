package com.dce.business.dao.award;

import com.dce.business.entity.award.CtAward;
import com.dce.business.entity.award.CtAwardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CtAwardMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_award
     *
     * @mbg.generated Tue Aug 07 11:38:22 CST 2018
     */
    long countByExample(CtAwardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_award
     *
     * @mbg.generated Tue Aug 07 11:38:22 CST 2018
     */
    int deleteByExample(CtAwardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_award
     *
     * @mbg.generated Tue Aug 07 11:38:22 CST 2018
     */
    int deleteByPrimaryKey(Integer awardid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_award
     *
     * @mbg.generated Tue Aug 07 11:38:22 CST 2018
     */
    int insert(CtAward record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_award
     *
     * @mbg.generated Tue Aug 07 11:38:22 CST 2018
     */
    int insertSelective(CtAward record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_award
     *
     * @mbg.generated Tue Aug 07 11:38:22 CST 2018
     */
    List<CtAward> selectByExample(CtAwardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_award
     *
     * @mbg.generated Tue Aug 07 11:38:22 CST 2018
     */
    CtAward selectByPrimaryKey(Integer awardid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_award
     *
     * @mbg.generated Tue Aug 07 11:38:22 CST 2018
     */
    int updateByExampleSelective(@Param("record") CtAward record, @Param("example") CtAwardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_award
     *
     * @mbg.generated Tue Aug 07 11:38:22 CST 2018
     */
    int updateByExample(@Param("record") CtAward record, @Param("example") CtAwardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_award
     *
     * @mbg.generated Tue Aug 07 11:38:22 CST 2018
     */
    int updateByPrimaryKeySelective(CtAward record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ct_award
     *
     * @mbg.generated Tue Aug 07 11:38:22 CST 2018
     */
    int updateByPrimaryKey(CtAward record);
}