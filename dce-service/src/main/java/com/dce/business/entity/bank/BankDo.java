package com.dce.business.entity.bank;

public class BankDo {
    private Integer id;

    private String bankName;

    private Byte bankType;

    private Byte bankFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Byte getBankType() {
        return bankType;
    }

    public void setBankType(Byte bankType) {
        this.bankType = bankType;
    }

    public Byte getBankFlag() {
        return bankFlag;
    }

    public void setBankFlag(Byte bankFlag) {
        this.bankFlag = bankFlag;
    }
}