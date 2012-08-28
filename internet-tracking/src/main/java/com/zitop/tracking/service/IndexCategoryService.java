package com.zitop.tracking.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zitop.infrastructure.service.impl.AbstractService;
import com.zitop.tracking.dao.IIndexCategoryDAO;
import com.zitop.tracking.dao.IIndexItemDAO;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.Project;
import com.zitop.util.SystemUtil;

@Service
public class IndexCategoryService extends AbstractService<IndexCategory, Long, IIndexCategoryDAO> {
	@Resource
	private IIndexCategoryDAO indexCategoryDAO;
	@Resource
	private IIndexItemDAO indexItemDAO;
	@Override
	public IIndexCategoryDAO getGenericDAO() {
		return indexCategoryDAO;
	}

	/**
	 * 获取除了EntityId外的指标分类,指定分类的子分类
	 * @param parentId
	 * @param entityId
	 * @return
	 */
	public List<IndexCategory> getEntitiesByParentId(Long parentId, Long entityId) {
		return indexCategoryDAO.getEntitiesByParentId(parentId, entityId);
	}
	/**
	 * 获取没有子分类 的所有指标分类
	 * @return
	 */
	public List<IndexCategory> getSubEntrities() {
		Project project=new Project();
		project.setId(Long.parseLong(SystemUtil.getParamProjectId()));
		List<IndexCategory> indexCategorys=indexCategoryDAO.getEntitiesByProject(project);
		List<IndexCategory> list=new ArrayList<IndexCategory>();
		for(IndexCategory indexCategory:indexCategorys){
			if(!indexCategory.getHasChilds()){
				list.add(indexCategory);
			}
		}
		return list;
	}
	/**
	 * 获取指定分类 的所有子分类
	 * @return
	 */
	public List<IndexCategory> getSubEntrities(IndexCategory indexCategory) {	
		return indexCategoryDAO.getSubEntrities(indexCategory);
	}

	public List<IndexCategory> getEntitiesByParentId(long parentId) {
		IndexCategory indexCategory=new IndexCategory();
		indexCategory.setId(parentId);
		return getSubEntrities(indexCategory);
	}
	
	/**
	 * 获取模块-顶级分类
	 * @return
	 */
	public List<IndexCategory> getRootEntrities() {	
		return indexCategoryDAO.getRootEntities();
	}
	/**
	 * 查看是否有下级数据
	 * @param indexCategory
	 * @return
	 */
	public boolean checkSub(IndexCategory indexCategory) {
		if(indexCategoryDAO.getSubEntrities(indexCategory).size()==0&&indexItemDAO.getEntitiesByCategory(indexCategory).size()==0){
			return false;
		}
		return true;
	}
	
	public String[] getAllCategoryIdsByProjectId(long projectId) {
		return indexCategoryDAO.getAllCategoryIdsByProjectId(projectId);
	}

	/**
	 *  根据指定的id数组查询指标分类	
	 * @param ids
	 */
	public List<IndexCategory> getIndexCategoryByIds(Long[] ids)
	{
		List<IndexCategory> list = null;
		if(ids != null && ids.length > 0)
		{
			list = indexCategoryDAO.getIndexCategoryByIds(ids);
		}
		return list;
	}
}
