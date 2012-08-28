package com.zitop.tracking.web.actions.admin.project;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.zitop.tracking.entity.Project;
import com.zitop.tracking.service.ProjectService;

@Result(name="show",location="/admin/project/project-show.jsp")
public class ProjectShowAction implements Preparable,ModelDriven<Project>
{
	private static final long serialVersionUID = -3256846717449039704L;
	@Resource
	private ProjectService projectService;
	private Project project;
	private Long projectId;

	public Project getModel()
	{
		return project;
	}

	public String execute()
	{
		
		return "show";
	}

	@Override
	public void prepare() throws Exception {
		if (projectId != null && projectId != 0)
		{
			project = projectService.getEntityById(projectId);
		}
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	
	

}
