package com.zitop.tracking.web.actions.admin.index;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.zitop.appsetting.ConfigNameConstants;
import com.zitop.appsetting.ParamaterValues;
import com.zitop.infrastructure.file.FileItemInfo;
import com.zitop.infrastructure.file.IFileSystem;
import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBaseManageAction;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.tracking.entity.Project;
import com.zitop.tracking.service.IndexCategoryService;
import com.zitop.tracking.service.IndexItemService;
import com.zitop.tracking.service.ProjectService;
import com.zitop.util.SystemUtil;
@Results({
@Result(name = "list", location = "/admin/index/index-item-list.action?categoryId=${categoryId}", type = "redirect"),
@Result(name = "back", location = "/admin/index/index-category-list.action?parentId=${parentId}", type = "redirect"),
})
public class IndexItemAction extends ServiceBaseManageAction<IndexItem,Long> implements ServletResponseAware
{
	private static final long serialVersionUID = -4953593555996973659L;
	@Resource
	private IndexItemService indexItemService;
	private IndexItem indexItem;
	@Resource
	private ProjectService projectService;
	private Project thisProject;
	@Resource
	private IndexCategoryService indexCategoryService;
	private List<IndexCategory> indexCategorys;
	private String categoryId;
	private IndexCategory currentIndexCategory;
	private Long parentId;
	@Resource
	private IFileSystem fileSystem;
	private File indexFile;
	private HttpServletResponse response;
	@Override
	public IGenericService<IndexItem, Long> getGenericService()
	{
		return indexItemService;
	}

	public IndexItem getModel()
	{
		return indexItem;
	}

	public void prepare() throws Exception
	{
		settingThisProject();
		indexCategorys=indexCategoryService.getSubEntrities(); 
		if (getRequestId() == null || getRequestId() == 0)
		{
			indexItem = new IndexItem();
		} else
		{
			indexItem = indexItemService.getEntityById(getRequestId());
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
		if(StringUtils.isNotEmpty(categoryId)){
			currentIndexCategory=indexCategoryService.getEntityById(Long.parseLong(categoryId));
		}
		return super.input();
	}
	public String back(){
		if(StringUtils.isNotEmpty(categoryId)){
			currentIndexCategory=indexCategoryService.getEntityById(Long.parseLong(categoryId));
			parentId=currentIndexCategory.getParentId();
		}
		return "back";
	}
	public String importExcelEntities(){
		if (indexFile != null) {
			String folder = ParamaterValues.getString(ConfigNameConstants.UPLOAD_FILE_FOLD);
			FileItemInfo item = fileSystem.saveFile(indexFile, "xls", folder);
			String filePath = folder + item.getFileUrl() + "/" + item.getFileName();
			indexItemService.importExcelEntities(filePath,thisProject);
		}
		return "list";
	}
	
	public String checkSub() throws IOException{
		boolean flag=indexItemService.checkSub(indexItem);
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
	public List<IndexCategory> getIndexCategorys() {
		return indexCategorys;
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public IndexCategory getCurrentIndexCategory() {
		return currentIndexCategory;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setIndexFile(File indexFile) {
		this.indexFile = indexFile;
	}
	

}
