package com.zitop.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.DataItem;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.tracking.entity.Term;

public class SysDataUtil {
	public static int TYPE_INDEX_TO_CUSTOMER = 1;
	public static int TYPE_CUSTOMER_TO_INDEX = 2;

	/** 指标-客户群数据 */
	public static Map<Long,Map<Long, Map<Long, DataItem>>> getIndexAndCustomerMap(List<DataItem> dataItems, int type) {
		Map<Long,Map<Long, Map<Long, DataItem>>> map0 = new HashMap<Long,Map<Long, Map<Long, DataItem>>>();
		for (DataItem dataItem : dataItems) {
			Long key0 = dataItem.getTerm().getId();
			Map<Long,Map<Long,DataItem>> map1 = map0.get(key0);
			if(map1 == null){
				map1 = new HashMap<Long,Map<Long,DataItem>>();
				map0.put(key0, map1);
			}
			Long key1 = (type == TYPE_INDEX_TO_CUSTOMER) ? dataItem
					.getIndexItem().getId() : dataItem.getCustomerCategory()
					.getId();
			Map<Long, DataItem> map2 = map1.get(key1);
			if (map2 == null) {
				map2 = new HashMap<Long, DataItem>();
				map1.put(key1, map2);
			}
			Long key2 = (type == TYPE_INDEX_TO_CUSTOMER) ? dataItem
					.getCustomerCategory().getId() : dataItem.getIndexItem()
					.getId();
			map2.put(key2, dataItem);
		}
		return map0;
	}
	
	/**
	 * 客户群分类-客户群数据
	 */
	public static Map<Long,List<CustomerCategory>> getCustomerToCustomerMap(List<CustomerCategory> customerCategorys)
	{
		Map<Long,List<CustomerCategory>> map = new HashMap<Long,List<CustomerCategory>>();
		if(customerCategorys != null)
		{
			for(CustomerCategory customer : customerCategorys)
			{
				Long key = customer.getParentId();
				List<CustomerCategory> list = map.get(key);
				if(list == null)
				{
					list = new ArrayList<CustomerCategory>();
					map.put(key, list);
				}
				list.add(customer);
			}
		}
		return map;
	}
	/**
	 * 判断只有一期数据展示 
	 */
	public static boolean isSingleTerm(List<Term> terms) 
	{
		return terms != null && terms.size() == 1;
	}
	
	/**
	 * 判断只有一个指标分类 
	 */
	public static boolean isSingleIndexCategory(List<IndexItem> indexItems)
	{
		if(indexItems == null || indexItems.size() == 0)
			return false;
		Long categoryId = null;
		for(IndexItem indexItem : indexItems)
		{
			if(categoryId == null)
			{
				categoryId = indexItem.getIndexCategory().getId();
			}
			else
			{
				if(categoryId.longValue() != indexItem.getIndexCategory().getId().longValue())
					return false;
			}
		}
		return true;
	}
}
