package com.dce.business.service.impl.dict;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.enums.DictCode;
import com.dce.business.dao.dict.ILoanDictDao;
import com.dce.business.dao.dict.ILoanDictDtlDao;
import com.dce.business.entity.dict.LoanDictDo;
import com.dce.business.entity.dict.LoanDictDtlDo;
import com.dce.business.service.dict.ILoanDictService;

@Service("loanDictService")
public class LoanDictServiceImpl implements ILoanDictService {
    private Logger logger = Logger.getLogger(LoanDictServiceImpl.class);

    @Resource
    private ILoanDictDao loanDictDao;
    @Resource
    private ILoanDictDtlDao loanDictDtlDao;

    @Override
    public LoanDictDtlDo getLoanDictDtl(String dictCode, String dictDtlCode) {
        Assert.hasText(dictCode, "数据字典编码不能为空");
        Assert.hasText(dictDtlCode, "数据字典明细编码不能为空");

        LoanDictDo loanDictDo = getLoanDict(dictCode);
        if (loanDictDo == null) {
            logger.info("查询不到对应的数据字典，编码：" + dictCode);
            return null;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dictId", loanDictDo.getId());
        params.put("code", dictDtlCode);
        params.put("status", "T");// 有效
        List<LoanDictDtlDo> list = loanDictDtlDao.selectLoanDictDtl(params);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public LoanDictDo getLoanDict(String code) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        params.put("status", "T");// 有效

        List<LoanDictDo> list = loanDictDao.selectLoanDict(params);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public List<LoanDictDtlDo> queryDictDtlListByDictCode(String code) {
        return loanDictDtlDao.queryDictDtlList(code);
    }

    @Override
    public int addDict(LoanDictDo loanDictDo) {
        loanDictDo.setUpdateTime(new Date());
        loanDictDo.setCreateTime(new Date());
        return loanDictDao.insertSelective(loanDictDo);
    }

    @Override
    public int updateDict(LoanDictDo loanDictDo) {
        return loanDictDao.updateByPrimaryKeySelective(loanDictDo);
    }

    @Override
    public int updateDictDtl(LoanDictDtlDo dictDtlDo) {
        return loanDictDtlDao.updateByPrimaryKeySelective(dictDtlDo);
    }

    @Override
    public int addDictDtl(LoanDictDtlDo dictDtlDo) {
        return loanDictDtlDao.insertSelective(dictDtlDo);
    }

    @Override
    public LoanDictDtlDo getLoanDictDtl(Long dictId, String dictDtlCode) {
        Assert.notNull(dictId, "数据字典主ID不能为空");
        Assert.hasText(dictDtlCode, "数据字典明细编码不能为空");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dictId", dictId);
        params.put("code", dictDtlCode);
        params.put("status", "T");// 有效
        List<LoanDictDtlDo> list = loanDictDtlDao.selectLoanDictDtl(params);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public LoanDictDo getDictById(long dictId) {
        Assert.notNull(dictId, "数据字典主dictId不能为空");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", dictId);
        params.put("status", "T");// 有效

        List<LoanDictDo> list = loanDictDao.selectLoanDict(params);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LoanDictDtlDo> getDictDetailByDictId(Long dictId) {

        Assert.notNull(dictId, "数据字典主dictId不能为空");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dictId", dictId);
        return loanDictDtlDao.selectLoanDictDtl(params);
    }

    public List<LoanDictDtlDo> getDictDetail(Map<String, Object> param) {
        return loanDictDtlDao.selectLoanDictDtl(param);
    }


    @Override
    public LoanDictDtlDo getLoanDictDtlByDictDtlCode(String dictDtlCode) {
        Assert.hasText(dictDtlCode, "数据字典明细编码不能为空");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", dictDtlCode);
        params.put("status", "T");// 有效
        List<LoanDictDtlDo> loanDictDtlDos = loanDictDtlDao.selectLoanDictDtl(params);
        if (CollectionUtils.isEmpty(loanDictDtlDos)) {
            return null;
        }
        return loanDictDtlDos.get(0);
    }

	@Override
	public LoanDictDtlDo getLoanDictDtl(String dictCode) {
		Assert.hasText(dictCode, "数据字典编码不能为空");
		List<LoanDictDtlDo> list = queryDictDtlListByDictCode(dictCode);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateDictDtlRemark(List<LoanDictDtlDo> list) {
		if(!CollectionUtils.isEmpty(list)){
//			for(LoanDictDtlDo dtl : list){
//				loanDictDtlDao.updateRemarkByDictIdAndCode(dtl);
//			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("list", list);
			loanDictDtlDao.batchUpdateRemark(params);
		}
	}

	@Override
	public List<LoanDictDo> selectLoanDictLinkDtl() {
		return loanDictDao.selectLoanDictLinkDtl();
	}

	@Override
	public BigDecimal rmb2Dce(BigDecimal rmb) {
		LoanDictDtlDo MYDBXCC = this.getLoanDictDtl(DictCode.Point2RMB.getCode());
        BigDecimal decp = new BigDecimal(MYDBXCC.getRemark());
        //计算需要的美元点
//        BigDecimal pointAmt = rmb.divide(new BigDecimal(6), 6, RoundingMode.HALF_UP);
        
        return rmb.divide(decp,6,RoundingMode.HALF_UP);
	}
}
