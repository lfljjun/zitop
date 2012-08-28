package com.zitop.tracking.web.actions.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;
import com.zitop.tracking.entity.Project;
import com.zitop.tracking.service.ProjectService;


public class HomeAction extends ActionSupport {

	private static final long serialVersionUID = -5853263476680331978L;
	private final static Log log = LogFactory.getLog(HomeAction.class);

	@Resource
	private ProjectService projectService;

	private List<Project> projectList;


	private String projectId;


	public String execute() {
		projectList = projectService.getAllEntity();
		if (projectList.size() == 0) {
			return "home";
		}
		if (projectId == null || "".equals(projectId) || "null".equals(projectId)) {
			projectId = "" + projectList.get(0).getId();
		}
		Struts2Utils.getSession().setAttribute("projectId", projectId);
		log.info("项目id:" + projectId);
		return "home";
	}


	public List<Project> getProjectList() {
		return projectList;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	

	
}
