package com.zitop.tracking.web.actions.admin.dimension;

import javax.annotation.Resource;

import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBasePaginationAction;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.service.CustomerCategoryService;
import com.zitop.util.SystemUtil;

public class CustomerCategoryListAction extends ServiceBasePaginationAction<CustomerCategory, Long>
{
	private static final long serialVersionUID = 3662125836009622861L;
	@Resource
	private CustomerCategoryService customerCategoryService;
	private String currentParentId;
	@Override
	public IGenericService<CustomerCategory, Long> getGenericService()
	{
		return customerCategoryService;
	}

	@Override
	public void preExecute()
	{
		
	}
	@Override
	public String execute() {
		ParamCondition paramCondition = getPager().getParamCondition();
		SystemUtil.addParamCurrentProjectId(paramCondition);
		return super.execute();
	}
	public void setCurrentParentId(String currentParentId) {
		this.currentParentId = currentParentId;
	}

	public String getCurrentParentId() {
		return currentParentId;
	}

	
}
