package com.dce.business.entity.dict;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LoanDictDo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String code;

    private String name;

    private String status;

    private String remark;

    private Long createUserId;

    private Long updateUserId;

    private Date createTime;

    private Date updateTime;
    
    private List<LoanDictDtlDo> dtlList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public List<LoanDictDtlDo> getDtlList() {
		return dtlList;
	}

	public void setDtlList(List<LoanDictDtlDo> dtlList) {
		this.dtlList = dtlList;
	}
    
}