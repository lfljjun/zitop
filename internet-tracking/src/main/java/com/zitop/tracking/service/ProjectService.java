package com.zitop.tracking.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zitop.infrastructure.service.impl.AbstractService;
import com.zitop.tracking.dao.ICustomerCategoryDAO;
import com.zitop.tracking.dao.IIndexCategoryDAO;
import com.zitop.tracking.dao.IProjectDAO;
import com.zitop.tracking.dao.ITermDAO;
import com.zitop.tracking.entity.Project;

@Service
public class ProjectService extends AbstractService<Project, Long, IProjectDAO>
{
	@Resource
	private IProjectDAO projectDAO;
	@Resource
	private IIndexCategoryDAO indexCategoryDAO;
	@Resource
	private ITermDAO termDAO;
	@Resource
	private ICustomerCategoryDAO customerCategoryDAO;
	
	@Override
	public IProjectDAO getGenericDAO()
	{
		return projectDAO;
	}
	/**
	 * 查看是否有下级数据
	 * @param project
	 * @return
	 */
	public boolean checkSub(Project project) {
		if(indexCategoryDAO.getEntitiesByProject(project).size()==0&&termDAO.getEntitiesByProject(project).size()==0
				&&customerCategoryDAO.getEntitiesByProject(project).size()==0){
			return false;
		}
		return true;
	}
	@Override
	public void delete(Project entity) {
		entity.setDeleted(true);
		update(entity);
	}
}
