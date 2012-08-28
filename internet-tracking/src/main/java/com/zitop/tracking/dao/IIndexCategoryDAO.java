package com.zitop.tracking.dao;

import java.util.List;

import com.zitop.infrastructure.dao.IGenericDAO;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.Project;

public interface IIndexCategoryDAO extends IGenericDAO<IndexCategory, Long> {

	List<IndexCategory> getEntitiesByParentId(Long parentId, Long entityId);

	List<IndexCategory> getSubEntrities(IndexCategory indexCategory);

	List<IndexCategory> getRootEntities();

	List<IndexCategory> getEntitiesByProject(Project project);
	
	/**
	 *  根据指定的id数组查询指标分类	
	 * @param ids
	 */
	public List<IndexCategory> getIndexCategoryByIds(Long[] ids);

	String[] getAllCategoryIdsByProjectId(long projectId);

}
