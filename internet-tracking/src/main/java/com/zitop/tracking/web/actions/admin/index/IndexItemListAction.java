package com.zitop.tracking.web.actions.admin.index;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBasePaginationAction;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.tracking.service.IndexCategoryService;
import com.zitop.tracking.service.IndexItemService;

public class IndexItemListAction extends ServiceBasePaginationAction<IndexItem, Long>
{
	private static final long serialVersionUID = -2394171434771795624L;
	@Resource
	private IndexItemService indexItemService;
	@Resource
	private IndexCategoryService indexCategoryService;
	private String categoryId;
	@Override
	public IGenericService<IndexItem, Long> getGenericService()
	{
		return indexItemService;
	}

	@Override
	public void preExecute()
	{
		
	}
	@Override
	public String execute() {
		ParamCondition paramCondition = getPager().getParamCondition();
		String projectId=Struts2Utils.getSession().getAttribute("projectId").toString();
		if(StringUtils.isNotBlank(projectId)){
			String[]categoryIds=indexCategoryService.getAllCategoryIdsByProjectId(Long.parseLong(projectId));
			paramCondition.addParameterValues("categoryIds", categoryIds);
			
		}
		return super.execute();
	}
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
