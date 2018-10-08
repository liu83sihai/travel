package com.dce.business.common.util;

public interface IHSSFHeaderCell {
    /**
    * @return 表头名字
    */
    public String getName();

    /**
     * @return表头宽度
     */
    public Double getWidth();

    /**
     * 多行合一的单元格个数
     * @return
     */
    public Integer getColNum();

}
