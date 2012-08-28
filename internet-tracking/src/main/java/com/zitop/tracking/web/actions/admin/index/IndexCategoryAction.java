package com.zitop.tracking.web.actions.admin.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBaseManageAction;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.Project;
import com.zitop.tracking.service.IndexCategoryService;
import com.zitop.tracking.service.ProjectService;
import com.zitop.util.SystemUtil;

/**
 * 指标分类管理
 * 
 * @author xiess
 * 
 */
public class IndexCategoryAction extends ServiceBaseManageAction<IndexCategory, Long> implements ServletResponseAware{
	private static final long serialVersionUID = 5159168369524067879L;

	@Resource
	private IndexCategoryService indexCategoryService;

	@Resource
	private ProjectService projectService;

	private IndexCategory indexCategory;
	private IndexCategory parentCategory;

	private List<Project> projects = new ArrayList<Project>();
	private List<IndexCategory> categories = new ArrayList<IndexCategory>();
	private List<IndexCategory> childs = new ArrayList<IndexCategory>();

	private Long projectId;
	private Long parentId;
	private Project thisProject;
	private HttpServletResponse response;
	@Override
	public IGenericService<IndexCategory, Long> getGenericService() {
		return indexCategoryService;
	}

	public IndexCategory getModel() {
		return indexCategory;
	}

	public void prepare() throws Exception {
		if(parentId!=null&&parentId!=0){
			parentCategory=indexCategoryService.getEntityById(parentId);
		}
		settingThisProject();
		if (getRequestId() == null || getRequestId() == 0) {
			indexCategory = new IndexCategory();
		} else {
			indexCategory = indexCategoryService.getEntityById(getRequestId());
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

	@Override
	@SkipValidation
	public String input() {
		setProject();
		projects = projectService.getAllEntity();
		categories = indexCategoryService.getEntitiesByParentId(null, getRequestId());
		return super.input();
	}

	@Override
	@SkipValidation
	public String edit() {
		projects = projectService.getAllEntity();
		categories = indexCategoryService.getEntitiesByParentId(null, getRequestId());
		childs = indexCategoryService.getEntitiesByParentId(getRequestId(), null);
		return super.edit();
	}

	@Override
	public String insert() {
		setProject();
		return super.insert();
	}

	private void setProject() {
		Project project = new Project();
		project.setId(projectId);
		indexCategory.setProject(project);
	}

	@Override
	public String update() {
		Project project = projectService.getEntityById(projectId);
		indexCategory.setProject(project);
		return super.update();
	}

	@Override
	public String delete() {
		indexCategory.setDeleted(true);
		return super.update();
	}
	
	public String checkSub() throws IOException{
		boolean flag=indexCategoryService.checkSub(indexCategory);
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

	public List<IndexCategory> getCategories() {
		return categories;
	}

	public List<IndexCategory> getChilds() {
		return childs;
	}

	public String[] getGraphTypes() {
		return IndexCategory.GRAPH_TYPE_ARRAY;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Project getThisProject() {
		return thisProject;
	}

	public IndexCategory getParentCategory() {
		return parentCategory;
	}
	
}
