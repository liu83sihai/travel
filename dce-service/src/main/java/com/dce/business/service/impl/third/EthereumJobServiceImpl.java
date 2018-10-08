package com.dce.business.service.impl.third;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.result.Result;
import com.dce.business.dao.etherenum.IEthereumTransInfoDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.etherenum.EthereumTransInfoDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.third.IEthereumJobService;
import com.dce.business.service.third.IEthereumService;

@Service("ethereumJobService")
public class EthereumJobServiceImpl implements IEthereumJobService {
	@Resource
	private IEthereumTransInfoDao ethereumTransInfoDao;
	@Resource
	private IEthereumService ethereumService;
	@Resource
	private IAccountService accountService;

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void comfirmEthTransResult() {
		Integer rows = 200; //每次做200条
		Integer offset = 0;
		while (true) {
			Map<String, Object> params = new HashMap<>();
			params.put("status", "true");// 有效
			params.put("confirmed", "false"); //未确认
			params.put("offset", offset);
			params.put("rows", rows);
			List<EthereumTransInfoDo> list = ethereumTransInfoDao.selectParam(params);
			if (CollectionUtils.isEmpty(list)) {
				break;
			}

			for (EthereumTransInfoDo transInfo : list) {
				Result<Map<String, Object>> result = ethereumService.getTransResult(transInfo.getHash());
				if (!result.isSuccess()) {//结果尚未确认
					continue;
				}

				Map<String, Object> map = result.getData();

				EthereumTransInfoDo newTransInfo = new EthereumTransInfoDo();
				newTransInfo.setId(transInfo.getId());
				newTransInfo.setActualamount(map.get("value").toString());
				newTransInfo.setActualgas(map.get("gas").toString());
				newTransInfo.setConfirmed("true");
				newTransInfo.setUpdatetime(new Date());
				ethereumTransInfoDao.updateByPrimaryKeySelective(newTransInfo);

				//如果是充值，成功以后增加美元点账户
				if (1 == transInfo.getType()) {
					UserAccountDo updateAccount = new UserAccountDo();
					updateAccount.setAccountType(AccountType.wallet_money.getAccountType());
					updateAccount.setAmount(new BigDecimal(transInfo.getPointamount()));
					updateAccount.setUserId(transInfo.getUserid());
					accountService.updateUserAmountById(updateAccount, IncomeType.TYPE_RECHARGE);
				}
			}

			offset += rows;
		}
	}

}
