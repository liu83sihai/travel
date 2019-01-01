package com.dce.business.common.enums;

public enum DictCode {
    /**
     * 报单会员等级
     */
    BaoDanFei("BaoDanFei"),
    /**
     * 人民币N:现金币 1
     */
    Point2RMB("Point2RMB"),
    /**
     * 人民币转美元点比例
     */
    RMB2Point("RMB2Point"),
    /**
     * 当日DEC对人民币价格
     */
    DEC2RMB("DEC2RMB"),
    /**
     * 交易费率
     */
    TRANS_RATE("TRANS_RATE"),
    /**
     * 美元兑人民币汇率
     */
    ExRate("ExRate"),
    /**
     * 客户级别
     */
    KHJB("zhidu_ref_level_cfg"),
    /**
     * 以太坊手续费
     */
    Gas("Gas"),
    /**
     * 美元点N:现持仓 1
     */
    MYDBXCC("MYDBXCC"),
    /**
     * 以太坊手续费最大限额
     */
    GasLimit("GasLimit"),
    BaoDanZengSong("BaoDanZengSong"), LiangPeng("LiangPeng"), LiangPengFengDing("LiangPengFengDing"), LiangPengRate("LiangPengRate"), ZhiTui(
            "ZhiTui"), HuZhuJiaQuan("HuZhuJiaQuan"), LingDao("LingDao"), JiaJin("JiaJin"),
    
    /**
     * 持币生息
     */
    ProfitRate("ProfitRate"), 
    /**
     * 小区分享奖
     */
    ShareRate("ShareRate"), 
    /**
     * 直推有效时间
     */
    ZhiTuiTime("ZhiTuiTime"),
    /**
     * 封顶
     */
    FengDin("FengDin"),
    /**
     * 原始仓钱包释放
     */
    OrigShiFangRate("OrigShiFangRate"),
    /**
     * 日息钱包释放
     */
    DaysShiFangRate("DaysShiFangRate"),
    /**
     * 奖金钱包释放
     */
    AwardShiFangRate("AwardShiFangRate"),
    /**
     * 二次释放比例
     */
    SecondShiFangRate("SecondShiFangRate"),
    /**
     * 提现手续费比例
     */
    WithDrawFee("WithDrawFee"),
    /**
     * 流通币释放比例
     */
    ScoreShiFangRate("ScoreShiFangRate"),
    /**
     * 现金币与IBAC比例  1现金币=N IBAC币
     */
    CashToIBAC("CashToIBAC"),
    /**
     * 流通币与IBAC比例  1流通币=N IBAC币
     */
    ScoreToIBAC("ScoreToIBAC")
    ;

    private String code;

    DictCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
