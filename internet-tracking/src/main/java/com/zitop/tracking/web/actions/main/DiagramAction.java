package com.zitop.tracking.web.actions.main;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.zitop.infrastructure.struts2.interceptor.PagerAware;
import com.zitop.infrastructure.util.Pager;
import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.DataItem;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.tracking.entity.Term;
import com.zitop.tracking.service.CustomerCategoryService;
import com.zitop.tracking.service.DataItemService;
import com.zitop.tracking.service.IndexCategoryService;
import com.zitop.tracking.service.IndexItemService;
import com.zitop.tracking.service.TermService;
import com.zitop.util.SysDataUtil;
import com.zitop.util.SystemUtil;

/**
 * 图表展示
 */
@SuppressWarnings("unused")
public class DiagramAction extends ActionSupport implements PagerAware<DataItem>,Preparable{
	private static final long serialVersionUID = -3530223386545169973L;
	
	@Resource
	private DataItemService dataItemService;
	@Resource
	private IndexItemService indexItemService;
	@Resource
	private IndexCategoryService indexCategoryService;
	@Resource
	private TermService termService;
	@Resource
	private CustomerCategoryService customerCategoryService;
	
//	输入参数
	/** 指标id字符串 */
	private String zhibiaos;
	/** 客户群id字符串 */
	private String kehus;
	/** 期数id字符串 */
	private String qishus;
	
	/** 指标id数组 */
	private Long[] indexItemIds;
	/** 指标分类id数组 */
	private Long[] indexCategoryIds;
	/** 期数id数组 */
	private Long[] termIds;
	/** 客户群id数组 */
	private Long[] customerCategoryIds;
	/** X方向 */
	private Integer xaxis = 1;
	private Long toBePie;
	/** 参数 */
	private Pager<DataItem> pager;
//	页面展示
	/** 选中指标 */
	private List<IndexItem> indexItems;
	/** 选中指标分类 */
	private List<IndexCategory> indexCategorys;
	/** 选中期数 */
	private List<Term>	terms;
	/** 选中客户群 */
	private List<CustomerCategory> customerCategorys;
	/** 符合查询条件的数据 */
	private List<DataItem> dataItems;
	/** 指标-客户群数据 */
	private Map<Long,Map<Long,Map<Long,DataItem>>> indexToCustomerMap;
	/** 客户群-指标数据 */
	private Map<Long,Map<Long,Map<Long,DataItem>>> customerToIndexMap; 
	/** 客户群分类-客户群数据 */
	private Map<Long,List<CustomerCategory>> customerToCustomerMap;
	/**  */
	private boolean isSingleTerm;
	private boolean isSingleIndexCategory;
	
	public String execute()
	{
		
//		指标
//		indexItems = indexItemService.getIndexItemByIds(indexItemIds);
//		指标分类
//		indexCategorys = indexItemService.getIndexCategoryByIndexIds(indexItemIds);
		indexCategorys = indexCategoryService.getIndexCategoryByIds(indexCategoryIds);
//		期数
		terms = termService.getTermByIds(termIds);
//		客户群
		customerCategorys =customerCategoryService.getSubCustomerCategoryByIds(customerCategoryIds);
//		数据
		dataItems = dataItemService.getDataItemByCondition(pager.getParamCondition());
		
		indexToCustomerMap = SysDataUtil.getIndexAndCustomerMap(dataItems, SysDataUtil.TYPE_INDEX_TO_CUSTOMER);
		customerToIndexMap = SysDataUtil.getIndexAndCustomerMap(dataItems, SysDataUtil.TYPE_CUSTOMER_TO_INDEX);
		customerToCustomerMap = SysDataUtil.getCustomerToCustomerMap(customerCategorys);
		
		isSingleTerm = SysDataUtil.isSingleTerm(terms);
		isSingleIndexCategory = SysDataUtil.isSingleIndexCategory(indexItems);

		return SUCCESS;
	}

	private void setDefPie() {
		if(toBePie == null)
		{
			if(customerCategorys != null)
			{
				for(CustomerCategory cust : customerCategorys)
				{
					if(customerCategoryService.getCountByParentId(cust.getParentId()) == 1)
					{
						toBePie = cust.getId();
						break;
					}
				}
			}
		}
	}
	
	@Override
	public void prepare() throws Exception {
		if(StringUtils.isNotBlank(zhibiaos))
			indexCategoryIds = SystemUtil.stringIdToLongArray(zhibiaos);
		if(StringUtils.isNotBlank(kehus))
			customerCategoryIds = SystemUtil.stringIdToLongArray(kehus);
		if(StringUtils.isNotBlank(qishus))
			termIds = SystemUtil.stringIdToLongArray(qishus);
	}

	
	public String getParams(String[] kehus) {
		String params="";
		for(String str:kehus){
			params+=str+"|";
		}
		return params;
	}

	public List<IndexItem> getIndexItems() {
		return indexItems;
	}


	public List<Term> getTerms() {
		return terms;
	}


	public List<CustomerCategory> getCustomerCategorys() {
		return customerCategorys;
	}


	public List<DataItem> getDataItems() {
		return dataItems;
	}


	public Long[] getIndexItemIds() {
		return indexItemIds;
	}


	public Long[] getTermIds() {
		return termIds;
	}


	public Long[] getCustomerCategoryIds() {
		return customerCategoryIds;
	}


	public Map<Long, Map<Long, Map<Long, DataItem>>> getIndexToCustomerMap() {
		return indexToCustomerMap;
	}

	public Map<Long, Map<Long, Map<Long, DataItem>>> getCustomerToIndexMap() {
		return customerToIndexMap;
	}

	public Long[] getIndexCategoryIds() {
		return indexCategoryIds;
	}

	public void setIndexCategoryIds(Long[] indexCategoryIds) {
		this.indexCategoryIds = indexCategoryIds;
	}

	@Override
	public void setPager(Pager<DataItem> pager) {
		this.pager = pager;
	}




	public Integer getXaxis() {
		return xaxis;
	}


	public void setXaxis(Integer xaxis) {
		this.xaxis = xaxis;
	}


	public void setToBePie(Long toBePie) {
		this.toBePie = toBePie;
	}


	public Long getToBePie() {
		return toBePie;
	}


	public void setZhibiaos(String zhibiaos) {
		this.zhibiaos = zhibiaos;
	}


	public List<IndexCategory> getIndexCategorys() {
		return indexCategorys;
	}

	public void setKehus(String kehus) {
		this.kehus = kehus;
	}


	public void setQishus(String qishus) {
		this.qishus = qishus;
	}

	public String getZhibiaos() {
		return zhibiaos;
	}

	public String getKehus() {
		return kehus;
	}

	public String getQishus() {
		return qishus;
	}

	public boolean getIsSingleTerm() {
		return isSingleTerm;
	}

	public boolean getIsSingleIndexCategory() {
		return isSingleIndexCategory;
	}

	public Map<Long, List<CustomerCategory>> getCustomerToCustomerMap() {
		return customerToCustomerMap;
	}




}
