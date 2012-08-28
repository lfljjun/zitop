package com.zitop.tracking.web.actions.admin.data;

import javax.annotation.Resource;

import com.zitop.tracking.entity.DataItem;
import com.zitop.tracking.service.DataItemService;
import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBasePaginationAction;

public class DataItemListAction extends ServiceBasePaginationAction<DataItem, Long>
{
	private static final long serialVersionUID = -6842820226440569449L;
	@Resource
	private DataItemService dataItemService;
	
	@Override
	public IGenericService<DataItem, Long> getGenericService()
	{
		return dataItemService;
	}

	@Override
	public void preExecute()
	{
		
	}

}
