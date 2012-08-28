package com.zitop.tracking.web.actions.admin.dimension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.zitop.appsetting.ConfigNameConstants;
import com.zitop.appsetting.ParamaterValues;
import com.zitop.infrastructure.file.FileItemInfo;
import com.zitop.infrastructure.file.IFileSystem;
import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBaseManageAction;
import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.Project;
import com.zitop.tracking.service.CustomerCategoryService;
import com.zitop.tracking.service.ProjectService;
import com.zitop.util.SystemUtil;
@Result(name = "list", location = "/admin/dimension/customer-category-list.action?currentParentId=${currentParentId}", type = "redirect") 
public class CustomerCategoryAction extends ServiceBaseManageAction<CustomerCategory,Long> implements ServletResponseAware
{
	private static final long serialVersionUID = 6524396356383115629L;
	@Resource
	private CustomerCategoryService customerCategoryService;
	private CustomerCategory customerCategory;
	private List<CustomerCategory> customerCategorys;
	private boolean flag=false;
	private String currentParentId;
	private CustomerCategory parentCustomerCategory;
	@Resource
	private ProjectService projectService;
	private List<Project> projects = new ArrayList<Project>();
	@Resource
	private IFileSystem fileSystem;
	private File dataFile;
	private HttpServletResponse response;
	
	private Project thisProject;
	@Override
	public IGenericService<CustomerCategory, Long> getGenericService()
	{
		return customerCategoryService;
	}
  
	public CustomerCategory getModel()
	{
		return customerCategory;
	}

	public void prepare() throws Exception
	{
		settingThisProject();
		customerCategorys=customerCategoryService.getHasChildsEntities();
		projects = projectService.getAllEntity();
		if (getRequestId() == null || getRequestId() == 0)
		{
			customerCategory = new CustomerCategory();
		} else
		{
			customerCategory = customerCategoryService.getEntityById(getRequestId());
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
	public String input() {
		if(StringUtils.isNotEmpty(currentParentId)&&!"0".equals(currentParentId)){
			parentCustomerCategory=customerCategoryService.getEntityById(Long.parseLong(currentParentId));
		}
		return super.input();
	}
    @Override
    public String edit() {
    	flag=customerCategoryService.hasChilds(customerCategory);
    	return super.edit();
    }
	@Override
	public String insert() {
		customerCategoryService.insert(getModel());
		return "list";
	}
	@Override
	public String update() {
		customerCategoryService.update(getModel());
		return "list";
	}
	@Override
	public String delete() {
		customerCategoryService.delete(getModel());
		return "list"; 
	}
	public String back(){
		if(StringUtils.isNotEmpty(currentParentId)&&!"0".equals(currentParentId)){
			parentCustomerCategory=customerCategoryService.getEntityById(Long.parseLong(currentParentId));
		}
		currentParentId=parentCustomerCategory.getParentId().toString();
		return "list";
	}
	
	public String importExcelEntities(){
		if (dataFile != null) {
			String folder = ParamaterValues.getString(ConfigNameConstants.UPLOAD_FILE_FOLD);
			FileItemInfo item = fileSystem.saveFile(dataFile, "xls", folder);
			String filePath = folder + item.getFileUrl() + "/" + item.getFileName();
			customerCategoryService.importExcelEntities(filePath,thisProject);
		}
		return "list";
	}
	public String checkSub() throws IOException{
		boolean flag=customerCategoryService.checkSub(customerCategory);
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
	public List<CustomerCategory> getCustomerCategorys() {
		return customerCategorys;
	}

	public boolean isFlag() {
		return flag;
	}
	
	public void setCurrentParentId(String currentParentId) {
		this.currentParentId = currentParentId;
	}

	public String getCurrentParentId() {
		return currentParentId;
	}

	public CustomerCategory getParentCustomerCategory() {
		return parentCustomerCategory;
	}
	public List<Project> getProjects() {
		return projects;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	public Project getThisProject() {
		return thisProject;
	}


}
