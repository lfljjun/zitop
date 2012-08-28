package com.zitop.tracking.web.actions.admin.project;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBaseManageAction;
import com.zitop.tracking.entity.Project;
import com.zitop.tracking.service.ProjectService;

public class ProjectAction extends ServiceBaseManageAction<Project,Long> implements ServletResponseAware
{
	private static final long serialVersionUID = -3256846717449039704L;
	@Resource
	private ProjectService projectService;
	private Project project;
	private HttpServletResponse response;
	@Override
	public IGenericService<Project, Long> getGenericService()
	{
		return projectService;
	}

	public Project getModel()
	{
		return project;
	}

	public void prepare() throws Exception
	{
		if (getRequestId() == null || getRequestId() == 0)
		{
			project = new Project();
		} else
		{
			project = projectService.getEntityById(getRequestId());
		}
	}
	
	public String insert() {
		super.insert();
		return show();
	}

	public String update() {
		super.update();
		return show();
	}

	public String checkSub() throws IOException{
		boolean flag=projectService.checkSub(project);
        response.setCharacterEncoding("UTF-8");       
        if(flag){
        	response.getWriter().write("false");
        }
        else{
        	response.getWriter().write("true");
        }
        return null;
	}
	@Override
	public void setServletResponse(HttpServletResponse response ) {
		this.response=response;
	}

}
