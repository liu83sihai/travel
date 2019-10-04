package com.dce.business.actions.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
 * 定时任务
 * @author parudy
 * @date 2018年3月24日 
 * @version v1.0
 */
@RestController
@RequestMapping("/job")
public class JobController {

//	@Resource
//	private IAwardJobService awardJobService;
//	@Resource
//	private IEthereumJobService ethereumJobService;


	@RequestMapping("/interest")
	public void jobInterest() {
		//awardJobService.calInterest();
		//zhiTuiAwardService.calAward(9, new BigDecimal("10000"), null);
		//awardJobService.calShared();
		//release();
	}

	/**   
	 * 持币生息，每天凌晨0点
	 */
	//@Scheduled(cron = "0 0 0 * * *")
	public void calInterest() {
		//awardJobService.calInterest();
	}

	/**   
	 * 每5分钟查询以太坊，看用户数据是否已确认
	 */
	//@Scheduled(cron = "0 0/1 * * * *")
	public void comfirmEthTransResult() {
		//ethereumJobService.comfirmEthTransResult();
	}

	/**   
	 * 钱包释放，每天凌晨2点释放
	 */
	//@Scheduled(cron = "0 0 2 * * *")
	public void release() {
		//1、原始仓钱包释放，按照原来金额的百分之四
		//awardJobService.calRelease();

		//2、日息钱包
		//awardJobService.calRelease(AccountType.wallet_interest, AccountType.wallet_original_release);

		//3、奖金钱包
		//awardJobService.calRelease(AccountType.wallet_bonus, AccountType.wallet_original_release);

		//4、二次释放
		//awardJobService.calRelease(AccountType.wallet_original_release, AccountType.wallet_release_release);
	}

	/**   
	 * 计算分享奖，每天凌晨一点
	 */
	//@Scheduled(cron = "0 0 1 * * *")
	public void calShared() {
		//awardJobService.calShared();
	}
}
