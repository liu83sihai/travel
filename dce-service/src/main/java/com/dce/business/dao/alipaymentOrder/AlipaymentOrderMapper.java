package com.dce.business.dao.alipaymentOrder;

import com.dce.business.entity.alipaymentOrder.AlipaymentOrder;
import com.dce.business.entity.alipaymentOrder.AlipaymentOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AlipaymentOrderMapper {

	long countByExample(AlipaymentOrderExample example);

	int deleteByExample(AlipaymentOrderExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(AlipaymentOrder record);

	int insertSelective(AlipaymentOrder record);

	List<AlipaymentOrder> selectByExample(AlipaymentOrderExample example);

	AlipaymentOrder selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") AlipaymentOrder record,
			@Param("example") AlipaymentOrderExample example);

	int updateByExample(@Param("record") AlipaymentOrder record, @Param("example") AlipaymentOrderExample example);

	int updateByPrimaryKeySelective(AlipaymentOrder record);

	int updateByPrimaryKey(AlipaymentOrder record);

	AlipaymentOrder selectByOrderCode(String orderCode);

}