package com.dce.business.entity.common;

import java.math.BigDecimal;

public class BonusConfigDDo {
    private Integer id;

    private String name;

    private String bonusType;

    private String configType;

    private String minLayer;

    private String maxLayer;

    private Boolean valueType;

    private Byte userLevel;

    private BigDecimal cap;

    private Byte capType;

    private Byte sort;

    private String ext;

    private String exts;

    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getMinLayer() {
        return minLayer;
    }

    public void setMinLayer(String minLayer) {
        this.minLayer = minLayer;
    }

    public String getMaxLayer() {
        return maxLayer;
    }

    public void setMaxLayer(String maxLayer) {
        this.maxLayer = maxLayer;
    }

    public Boolean getValueType() {
        return valueType;
    }

    public void setValueType(Boolean valueType) {
        this.valueType = valueType;
    }

    public Byte getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Byte userLevel) {
        this.userLevel = userLevel;
    }

    public BigDecimal getCap() {
        return cap;
    }

    public void setCap(BigDecimal cap) {
        this.cap = cap;
    }

    public Byte getCapType() {
        return capType;
    }

    public void setCapType(Byte capType) {
        this.capType = capType;
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getExts() {
        return exts;
    }

    public void setExts(String exts) {
        this.exts = exts;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}