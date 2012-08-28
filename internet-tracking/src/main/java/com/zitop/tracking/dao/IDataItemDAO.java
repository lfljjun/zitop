package com.zitop.tracking.dao;

import java.util.List;

import com.zitop.tracking.entity.DataItem;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.infrastructure.dao.IGenericDAO;

public interface IDataItemDAO extends IGenericDAO<DataItem, Long>
{

	List<DataItem> getEntitiesByTermId(Long termId);

	DataItem getEntityByIds(Long termId, Long customerCategoryId, Long indexItemId);

	List<IndexItem> getEntitiesByIndexItemId(Long indexItemId);
	List<IndexItem> getEntitiesByCustomerId(Long customerId);
	public void deleteAll(Long termId);

}
