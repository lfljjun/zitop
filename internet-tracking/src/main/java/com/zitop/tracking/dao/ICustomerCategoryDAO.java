package com.zitop.tracking.dao;

import java.util.List;

import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.Project;
import com.zitop.infrastructure.dao.IGenericDAO;

public interface ICustomerCategoryDAO extends IGenericDAO<CustomerCategory, Long> {

	List<CustomerCategory> getSubEntities(CustomerCategory customerCategory);

	/**
	 * 根据指定的id数组查询客户群数组
	 * 
	 * @param ids
	 */
	public List<CustomerCategory> getSubCustomerCategoryByIds(Long[] ids);

	/**
	 * 获取子分类的个数
	 * 
	 * @param indexCategoryId
	 *            分类id
	 */
	public int getCountByParentId(Long parentId);

	List<CustomerCategory> getEntitiesByProject(Project project);

	/**
	 * 获取以最底层子节点
	 * 
	 * @return
	 */
	List<CustomerCategory> getChildEntities();
}
