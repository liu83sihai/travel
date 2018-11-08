package com.dce.business.service.choose;

import java.util.Map;

import com.dce.business.entity.page.PageDo;

public interface ICommonChooseService {

	PageDo<Map<String, Object>> getChoosePage(Map<String, Object> param,
			PageDo<Map<String, Object>> page);

}
