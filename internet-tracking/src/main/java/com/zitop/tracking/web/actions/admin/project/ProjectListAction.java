package com.zitop.tracking.web.actions.admin.project;

import javax.annotation.Resource;

import com.zitop.tracking.entity.Project;
import com.zitop.tracking.service.ProjectService;
import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBasePaginationAction;

public class ProjectListAction extends ServiceBasePaginationAction<Project, Long>
{
	private static final long serialVersionUID = 6063848729377430659L;
	@Resource
	private ProjectService projectService;
	
	@Override
	public IGenericService<Project, Long> getGenericService()
	{
		return projectService;
	}

	@Override
	public void preExecute()
	{
		
	}

}
