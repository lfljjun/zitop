package com.zitop.tracking.web.actions.main;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;
import com.zitop.infrastructure.util.Pager;
import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.DataItem;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.tracking.entity.Project;
import com.zitop.tracking.entity.Term;
import com.zitop.tracking.service.CustomerCategoryService;
import com.zitop.tracking.service.DataItemService;
import com.zitop.tracking.service.IndexCategoryService;
import com.zitop.tracking.service.IndexItemService;
import com.zitop.tracking.service.ProjectService;
import com.zitop.tracking.service.ShowService;
import com.zitop.tracking.service.TermService;

@Results({ @Result(name = "category", location = "/main/category.jsp"), 
	@Result(name = "customer", location = "/main/customer.jsp"),
	@Result(name = "term", location = "/main/term.jsp"),
	@Result(name = "preview", location = "/main/show-table.jsp")
})
public class IndexAction extends ActionSupport {

	private static final long serialVersionUID = -5853263476680331978L;
	private final static Log log = LogFactory.getLog(IndexAction.class);

	@Resource
	private ShowService showService;
	@Resource
	private ProjectService projectService;
	@Resource
	private CustomerCategoryService customerCategoryService;
	@Resource
	private DataItemService dataItemService;
	@Resource
	private IndexCategoryService indexCategoryService;
	@Resource
	private IndexItemService indexItemService;
	@Resource
	private TermService termService;

	private List<Project> projectList;
	private List<IndexCategory> indexCategoryList;
	private List<IndexItem> indexItemList;
	private List<CustomerCategory> customerCategoryList;
	private List<Term> termList;
	private List<DataItem> dataItemList;

	private String projectId;
	private Project project;

	public String execute() {
		projectList = projectService.getAllEntity();
		if (projectList.size() == 0) {
			return "index";
		}
		if (projectId == null || "".equals(projectId) || "null".equals(projectId)) {
			projectId = "" + projectList.get(0).getId();
		}
		Struts2Utils.getSession().setAttribute("projectId", projectId);
		log.info("项目id:" + projectId);
		
		project = projectService.getEntityById(Long.valueOf(projectId));
		Pager<IndexCategory> icPager = new Pager<IndexCategory>();
		icPager.setPageSize(1000);
		icPager.getParamCondition().addParameter("parentId", "" + 0);
		icPager.getParamCondition().addParameter("projectId", projectId);
		indexCategoryService.getEntitiesOfPagerByParamCondition(icPager);
		indexCategoryList = icPager.getItems();
		return "index";
	}

	public String category() {
		String categoryId = Struts2Utils.getParameter("categoryId");
		log.info("categoryId:" + categoryId);
		Struts2Utils.getRequest().setAttribute("categoryId", categoryId);
		Pager<IndexCategory> icPager = new Pager<IndexCategory>();
		icPager.setPageSize(1000);
		icPager.getParamCondition().addParameter("parentId", categoryId);
		indexCategoryService.getEntitiesOfPagerByParamCondition(icPager);
		indexCategoryList = icPager.getItems();
		return "category";
	}

	public String customer() {
		Pager<CustomerCategory> cPager = new Pager<CustomerCategory>();
		cPager.setPageSize(-1);
		cPager.getParamCondition().addParameter("projectId", Struts2Utils.getSession().getAttribute("projectId").toString());
		customerCategoryService.getEntitiesOfPagerByParamCondition(cPager);
		customerCategoryList = cPager.getItems();
		return "customer";
	}
	
	public String term() {
		Pager<Term> tpager = new Pager<Term>();
		tpager.setPageSize(-1);
		tpager.getParamCondition().addParameter("projectId", Struts2Utils.getSession().getAttribute("projectId").toString());
		termService.getEntitiesOfPagerByParamCondition(tpager);
		termList = tpager.getItems();
		return "term";
	}
	
	public String preview() {
		String zhibiaos = "";
		for (IndexCategory index : indexCategoryService.getAllEntity()) {
			if (index.getParentId() != 0) {
				zhibiaos = zhibiaos + index.getId() + "|";
			}
		}
		Struts2Utils.getRequest().setAttribute("zhibiaos", zhibiaos);
		
		String kehus = "";
		for (CustomerCategory customer : customerCategoryService.getAllEntity()) {
			if (customer.getParentId() != 0) {
				kehus = kehus + customer.getId() + "|";
			}
		}
		Struts2Utils.getRequest().setAttribute("kehus", kehus);
		
		String qishus = "";
		for (Term term : termService.getAllEntity()) {
			qishus = qishus + term.getId() + "|";
		}
		Struts2Utils.getRequest().setAttribute("qishus", qishus);

		Struts2Utils.getRequest().setAttribute("preview", "true");
		showService.showTable();
		return "preview";
	}
	
	public List<IndexCategory> getIndexCategoryList() {
		return indexCategoryList;
	}

	public void setIndexCategoryList(List<IndexCategory> indexCategoryList) {
		this.indexCategoryList = indexCategoryList;
	}

	public List<IndexItem> getIndexItemList() {
		return indexItemList;
	}

	public void setIndexItemList(List<IndexItem> indexItemList) {
		this.indexItemList = indexItemList;
	}

	public List<CustomerCategory> getCustomerCategoryList() {
		return customerCategoryList;
	}

	public void setCustomerCategoryList(List<CustomerCategory> customerCategoryList) {
		this.customerCategoryList = customerCategoryList;
	}

	public List<Term> getTermList() {
		return termList;
	}

	public void setTermList(List<Term> termList) {
		this.termList = termList;
	}

	public List<DataItem> getDataItemList() {
		return dataItemList;
	}

	public void setDataItemList(List<DataItem> dataItemList) {
		this.dataItemList = dataItemList;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}
}
