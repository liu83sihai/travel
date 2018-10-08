package com.dce.business.service.trade;

import java.util.List;

import com.dce.business.common.enums.KLineTypeEnum;
import com.dce.business.entity.trade.KLineDo;

public interface IKLineService {
    
    List<KLineDo> getKLine(String type);
    
    List<KLineDo> calKLine(KLineTypeEnum kLineType);
    
    void updateKLine();
}
