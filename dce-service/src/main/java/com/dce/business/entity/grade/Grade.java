package com.dce.business.entity.grade;

public class Grade {
 
    private Integer gradeId;

    
    private String gradeName;

  
    private Integer gradeMark;

  
    private String remark;

    
    public Integer getGradeId() {
        return gradeId;
    }

  
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    
    public String getGradeName() {
        return gradeName;
    }

    
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    
    public Integer getGradeMark() {
        return gradeMark;
    }

   
    public void setGradeMark(Integer gradeMark) {
        this.gradeMark = gradeMark;
    }

  
    public String getRemark() {
        return remark;
    }

   
    public void setRemark(String remark) {
        this.remark = remark;
    }
}