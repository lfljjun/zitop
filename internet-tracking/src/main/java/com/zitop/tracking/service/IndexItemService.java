package com.zitop.tracking.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zitop.infrastructure.service.impl.AbstractService;
import com.zitop.tracking.dao.IDataItemDAO;
import com.zitop.tracking.dao.IIndexCategoryDAO;
import com.zitop.tracking.dao.IIndexItemDAO;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.tracking.entity.Project;
import com.zitop.util.DataItemUtil;
import com.zitop.util.SystemUtil;


@Service
public class IndexItemService extends AbstractService<IndexItem, Long, IIndexItemDAO>
{
	@Resource
	private IIndexItemDAO indexItemDAO;
	@Resource
	private IIndexCategoryDAO indexCategoryDAO;
	@Resource
	private IDataItemDAO dataItemDAO;
	@Override
	public IIndexItemDAO getGenericDAO()
	{
		return indexItemDAO;
	}
	@Override
	public void delete(IndexItem entity) {
		entity.setDeleted(true);
		super.update(entity);
	}
	
	/**
	 * 根据指定的id数组查询指标数组
	 * @param ids
	 */
	public List<IndexItem> getIndexItemByIds(Long[] ids)
	{
		List<IndexItem> list = null;
		if (ids != null && ids.length > 0) 
		{
			list = indexItemDAO.getIndexItemByIds(ids);
		}
		return list;
	}
	
	/**
	 * 根据指定的指标id数据查询指标分类
	 * @param ids
	 */
	public List<IndexCategory> getIndexCategoryByIndexIds(Long[] ids)
	{
		List<IndexCategory> list = null;
		if(ids != null && ids.length > 0)
		{
			list = indexItemDAO.getIndexCategoryByIndexIds(ids);
		}
		return list;
	}
	public List<IndexItem> getEntitiesByCategory(IndexCategory indexCategory) {
		return indexItemDAO.getEntitiesByCategory(indexCategory);
	}
	
	@SuppressWarnings("rawtypes")
	public void importExcelEntities( String filePath, Project project ){
		List<List> result = null;
		try {
			result = DataItemUtil.read(filePath);
		} catch (BiffException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
		long categoryId=0;
		long parentId=0;
		for(List list:result){
			for(int i=0;i<list.size();i++){
				if(i==0){
					String str=(String) list.get(i);
					if(StringUtils.isNotEmpty(str)){
						IndexCategory category=new IndexCategory();
						category.setName(str);
						category.setHasChilds(true);
						category.setParentId((long) 0);
						category.setProject(project);
						category.setCreator(SystemUtil.getSysUserName());
						parentId=indexCategoryDAO.insert(category).getId();	
					}
				}
				if(i==1){
					String str=(String) list.get(i);
					if(StringUtils.isNotEmpty(str)){
						IndexCategory category=new IndexCategory();
						category.setName(str);
						category.setHasChilds(false);
						category.setParentId(parentId);
						category.setProject(project);
						category.setCreator(SystemUtil.getSysUserName());
						categoryId=indexCategoryDAO.insert(category).getId();
					}
				}
				if(i==2){
					String str=(String) list.get(i);
					if(StringUtils.isNotEmpty(str)){
						IndexItem indexItem=new IndexItem();
						indexItem.setName(str);
						IndexCategory category=new IndexCategory();
						category.setId(categoryId);
						indexItem.setIndexCategory(category);
						indexItem.setCreator(SystemUtil.getSysUserName());
						indexItemDAO.insert(indexItem);
					}
				}
			}
		}
	}
	/**
	 * 查看是否有下级数据
	 * @param indexItem
	 * @return
	 */
	public boolean checkSub(IndexItem indexItem) {
		if(dataItemDAO.getEntitiesByIndexItemId(indexItem.getId()).size()==0){
			return false;
		}
		return true;
	}
}
