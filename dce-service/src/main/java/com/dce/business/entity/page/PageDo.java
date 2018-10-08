package com.dce.business.entity.page;

import java.io.Serializable;
import java.util.List;

public class PageDo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long currentPage; // 当前页
    private Long pageSize; // 每页记录数
    private Long totalCount; // 总记录数
    private Long totalPage; // 总页数
    private List<T> modelList; // 实体对象列表

    public PageDo() {

    }

    public PageDo(Long currentPage, Long pageSize, Long totalCount, List<T> modelList) {
        this.setCurrentPage(currentPage);
        this.setPageSize(pageSize);
        this.setTotalCount(totalCount);
        long totalPage = (getTotalCount() / getPageSize()) + ((getTotalCount() % getPageSize()) > 0 ? 1 : 0);
        this.setTotalPage(totalPage);
        this.setModelList(modelList);
    }

    /**
     * 当前页
     * 
     * @return currentPage
     */
    public Long getCurrentPage() {
        return currentPage;
    }

    /**
     * 当前页
     * 
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(Long currentPage) {
        if (currentPage == null || currentPage.intValue() <= 0) {
            currentPage = 1l;
        }
        this.currentPage = currentPage;
    }

    /**
     * 每页记录数
     * 
     * @return pageSize
     */
    public Long getPageSize() {
        return pageSize;
    }

    /**
     * 每页记录数
     * 
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Long pageSize) {
        if (pageSize == null || pageSize.intValue() <= 0) {
            pageSize = 10l;
        }
        this.pageSize = pageSize;
    }

    /**
     * 总记录数
     * 
     * @return totalCount
     */
    public Long getTotalCount() {
        return totalCount;
    }

    /**
     * 总记录数
     * 
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(Long totalCount) {
        if (totalCount == null) {
            totalCount = 0l;
        }
        this.totalCount = totalCount;
    }

    /**
     * 总页数
     * 
     * @return totalPage
     */
    public Long getTotalPage() {
        return totalPage;
    }

    /**
     * 总页数
     * 
     * @param totalPage the totalPage to set
     */
    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 实体对象
     * 
     * @return modelList
     */
    public List<T> getModelList() {
        return modelList;
    }

    /**
     * 实体对象
     * 
     * @param modelList the modelList to set
     */
    public void setModelList(List<T> modelList) {
        this.modelList = modelList;
    }

    /**
     * 返回limit起始位移量 
     * @return  
     */
    public Long getOffset() {
        long current = getCurrentPage() == null ? 1L : getCurrentPage();
        long size = getPageSize() == null ? 0L : getPageSize();
        return (current - 1) * size;
    }

    /**   
     * 计算并设置分页总页数
     */
    public void calTotalPage() {
        int totalPage = (int) (totalCount / getPageSize() + ((totalCount % getPageSize() == 0) ? 0 : 1));
        setTotalPage((long) totalPage);
    }
}
