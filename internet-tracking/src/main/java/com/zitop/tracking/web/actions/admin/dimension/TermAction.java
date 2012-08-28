package com.zitop.tracking.web.actions.admin.dimension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBaseManageAction;
import com.zitop.tracking.entity.Project;
import com.zitop.tracking.entity.Term;
import com.zitop.tracking.service.ProjectService;
import com.zitop.tracking.service.TermService;
import com.zitop.util.SystemUtil;

public class TermAction extends ServiceBaseManageAction<Term, Long>implements ServletResponseAware{
	private static final long serialVersionUID = -3851509906657885404L;

	@Resource
	private TermService termService;
	@Resource
	private ProjectService projectService;
	private Project thisProject;
	private Term term;
	private Long projectId;
	private List<Project> projects = new ArrayList<Project>();
	private HttpServletResponse response;
	@Override
	public IGenericService<Term, Long> getGenericService() {
		return termService;
	}

	public Term getModel() {
		return term;
	}

	public void prepare() throws Exception {
		settingThisProject();
		if (getRequestId() == null || getRequestId() == 0) {
			term = new Term();
		} else {
			term = termService.getEntityById(getRequestId());
		}
	}
	
	/**
	 * 设置当前所选的项目
	 */
	private void settingThisProject() {
		String thisProjectId=SystemUtil.getParamProjectId();
		if(StringUtils.isNotBlank(thisProjectId)){
			thisProject=projectService.getEntityById(Long.parseLong(thisProjectId));
		}
	}
	public String input() {
		projects = projectService.getAllEntity();
		return super.input();
	}

	public String edit() {
		projects = projectService.getAllEntity();
		return super.edit();
	}

	public String insert() {
		Project project = new Project();
		project.setId(projectId);
		term.setProject(project);
		term.setCreator(SystemUtil.getSysUserName());
		return super.insert();
	}

	public String update() {
		return super.update();
	}

	public String delete() {
		term.setDeleted(true);
		return super.update();
	}
	public String checkSub() throws IOException{
		boolean flag=termService.checkSub(term);
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
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Project getThisProject() {
		return thisProject;
	}

}
