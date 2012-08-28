package com.zitop.tracking.dao;

import java.util.List;

import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.infrastructure.dao.IGenericDAO;

public interface IIndexItemDAO extends IGenericDAO<IndexItem, Long> {
	/**
	 * 根据指定的id数组查询指标数组
	 * 
	 * @param ids
	 */
	public List<IndexItem> getIndexItemByIds(Long[] ids);
	/**
	 * 根据指定的指标id数据查询指标分类
	 * @param ids
	 */
	public List<IndexCategory> getIndexCategoryByIndexIds(Long[] ids);

	List<IndexItem> getEntitiesByCategory(IndexCategory indexCategory);

	Integer findCountByIndexCategoryId(Long indexCategoryId);

}
