package com.dce.business.entity.goods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dce.business.common.enums.AccountType;

/**
 * 商品
 * @author zhangcymf
 *
 */
public class CTGoodsDo implements Serializable {

	  private static final long serialVersionUID = -9153547858826001223L;
	  //商品id
	  private Integer goodsId;
	  private String goodsSn ;
	  //商品名称
	  private String title ;
	  //商品图片地址
	  private String goodsImg ;
	  private Long goodsThums ;
	  private Long brandId ;
	  private Long shopId ;
	  private BigDecimal marketPrice ;
	  //商品的价格
	  private BigDecimal shopPrice ;
	  private Long goodsStock ;
	  private Long saleCount ;
	  private Integer isBook ;
	  private Long bookQuantity ;
	  private Long warnStock ;
	  private String goodsUnit ;
	  private String goodsSpec ;
	  private Integer isSale ;
	  private Integer isBest ;
	  private Integer isHot ;
	  private Integer isRecomm ;
	  private Integer isNew ;
	  private Integer isAdminBest ;
	  private Integer isAdminRecom ;
	  private String recommDesc ;
	  private Long cid1 ;
	  private Long cid2 ;
	  private Long cid3 ;
	  private Long shopCatId1 ; // 1： 爆款商品， 2： 常规商品
	  private Long shopCatId2 ;
	  // 商品的详情
	  private String goodsDesc ;
	  private Integer isShopRecomm ;
	  private Integer isIndexRecomm ;
	  private Integer isActivityRecomm ;
	  private Integer isInnerRecomm ;
	  //商品状态 0为上架 1 为未上架
	  private Integer status ;
	  //商品上架时间
	  private Date saleTime ;
	  private Long attrCatId ;
	  private String goodsKeywords ;
	  private Integer goodsFlag ;  //商品类别 1： 旅游卡， 2： 积分商品， 3： 会员商品  4： 旅游路线  5:代理商 6 用户升级
	  private String statusRemarks ; //商品状态：0为删除1为未删除（逻辑删除）
	  //商品创建时间
	  private Date createTime ;
	  private String goodsDetailImg; //详情图片
	  private String goodsBanner; //详情页面的banner图片
	  
	  private String detailLink ; //商品详情的连接
	  private BigDecimal profit; //商品利润
	  private String  payType;    //支付方式设置
	  private Integer starLevel; //路线级别 星级
	  private Float score; //路线评分
	  private BigDecimal postage; //邮寄费
	  
	  private Integer memberLevel; //购买后会员等级
	  private String memberLevelName; //购买后会员等级
	  
	  private Integer sendCard; //赠送卡
	  private Integer jf; //赠送积分
	  
	  
	  
	public Integer getSendCard() {
		return sendCard;
	}


	public void setSendCard(Integer sendCard) {
		this.sendCard = sendCard;
	}


	public Integer getJf() {
		return jf;
	}


	public void setJf(Integer jf) {
		this.jf = jf;
	}


	public String getPayTypeName() {
		if(StringUtils.isBlank(payType)) {
			return "";
		}
		StringBuffer pTName = new StringBuffer();
		String[] payArr = payType.split(",");
		for(String pay : payArr) {
			AccountType payTp = AccountType.getAccountType(pay);
			if(null != payTp) {
				pTName.append(payTp.getRemark()).append("/");
			}
		}
		return pTName.toString();
	}
	  
	  
	public BigDecimal getPostage() {
		return postage;
	}
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	public Integer getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getDetailLink() {
		return detailLink;
	}
	public void setDetailLink(String detailLink) {
		this.detailLink = detailLink;
	}
	public BigDecimal getProfit() {
		return profit;
	}
	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	public String getGoodsBanner() {
		return goodsBanner;
	}
	public void setGoodsBanner(String goodsBanner) {
		this.goodsBanner = goodsBanner;
	}
	public String getGoodsDetailImg() {
		return goodsDetailImg;
	}
	public void setGoodsDetailImg(String goodsDetailImg) {
		this.goodsDetailImg = goodsDetailImg;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGoodsImg() {
		return goodsImg;
	}
	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}
	public Long getGoodsThums() {
		return goodsThums;
	}
	public void setGoodsThums(Long goodsThums) {
		this.goodsThums = goodsThums;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(BigDecimal shopPrice) {
		this.shopPrice = shopPrice;
	}
	public Long getGoodsStock() {
		return goodsStock;
	}
	public void setGoodsStock(Long goodsStock) {
		this.goodsStock = goodsStock;
	}
	public Long getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(Long saleCount) {
		this.saleCount = saleCount;
	}
	public Integer getIsBook() {
		return isBook;
	}
	public void setIsBook(Integer isBook) {
		this.isBook = isBook;
	}
	public Long getBookQuantity() {
		return bookQuantity;
	}
	public void setBookQuantity(Long bookQuantity) {
		this.bookQuantity = bookQuantity;
	}
	public Long getWarnStock() {
		return warnStock;
	}
	public void setWarnStock(Long warnStock) {
		this.warnStock = warnStock;
	}
	public String getGoodsUnit() {
		return goodsUnit;
	}
	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}
	public String getGoodsSpec() {
		return goodsSpec;
	}
	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}
	public Integer getIsSale() {
		return isSale;
	}
	public void setIsSale(Integer isSale) {
		this.isSale = isSale;
	}
	public Integer getIsBest() {
		return isBest;
	}
	public void setIsBest(Integer isBest) {
		this.isBest = isBest;
	}
	public Integer getIsHot() {
		return isHot;
	}
	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}
	public Integer getIsRecomm() {
		return isRecomm;
	}
	public void setIsRecomm(Integer isRecomm) {
		this.isRecomm = isRecomm;
	}
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	public Integer getIsAdminBest() {
		return isAdminBest;
	}
	public void setIsAdminBest(Integer isAdminBest) {
		this.isAdminBest = isAdminBest;
	}
	public Integer getIsAdminRecom() {
		return isAdminRecom;
	}
	public void setIsAdminRecom(Integer isAdminRecom) {
		this.isAdminRecom = isAdminRecom;
	}
	public String getRecommDesc() {
		return recommDesc;
	}
	public void setRecommDesc(String recommDesc) {
		this.recommDesc = recommDesc;
	}
	public Long getCid1() {
		return cid1;
	}
	public void setCid1(Long cid1) {
		this.cid1 = cid1;
	}
	public Long getCid2() {
		return cid2;
	}
	public void setCid2(Long cid2) {
		this.cid2 = cid2;
	}
	public Long getCid3() {
		return cid3;
	}
	public void setCid3(Long cid3) {
		this.cid3 = cid3;
	}
	public Long getShopCatId1() {
		return shopCatId1;
	}
	public void setShopCatId1(Long shopCatId1) {
		this.shopCatId1 = shopCatId1;
	}
	public Long getShopCatId2() {
		return shopCatId2;
	}
	public void setShopCatId2(Long shopCatId2) {
		this.shopCatId2 = shopCatId2;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public Integer getIsShopRecomm() {
		return isShopRecomm;
	}
	public void setIsShopRecomm(Integer isShopRecomm) {
		this.isShopRecomm = isShopRecomm;
	}
	public Integer getIsIndexRecomm() {
		return isIndexRecomm;
	}
	public void setIsIndexRecomm(Integer isIndexRecomm) {
		this.isIndexRecomm = isIndexRecomm;
	}
	public Integer getIsActivityRecomm() {
		return isActivityRecomm;
	}
	public void setIsActivityRecomm(Integer isActivityRecomm) {
		this.isActivityRecomm = isActivityRecomm;
	}
	public Integer getIsInnerRecomm() {
		return isInnerRecomm;
	}
	public void setIsInnerRecomm(Integer isInnerRecomm) {
		this.isInnerRecomm = isInnerRecomm;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}
	public Long getAttrCatId() {
		return attrCatId;
	}
	public void setAttrCatId(Long attrCatId) {
		this.attrCatId = attrCatId;
	}
	public String getGoodsKeywords() {
		return goodsKeywords;
	}
	public void setGoodsKeywords(String goodsKeywords) {
		this.goodsKeywords = goodsKeywords;
	}
	public Integer getGoodsFlag() {
		return goodsFlag;
	}
	public void setGoodsFlag(Integer goodsFlag) {
		this.goodsFlag = goodsFlag;
	}
	public String getStatusRemarks() {
		return statusRemarks;
	}
	public void setStatusRemarks(String statusRemarks) {
		this.statusRemarks = statusRemarks;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	public Integer getMemberLevel() {
		return memberLevel;
	}


	public void setMemberLevel(Integer memberLevel) {
		this.memberLevel = memberLevel;
	}




	public String getMemberLevelName() {
		return memberLevelName;
	}


	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}


	public String getShowDetailUrl(String goodsDetailUrl) {
		StringBuffer sb  = new StringBuffer();
		sb.append(goodsDetailUrl).append("?goodsId=").append(this.getGoodsId()).append("&page=goods");
		return sb.toString();
	}
	  
	  
}
