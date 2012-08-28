package com.zitop.security.web.actions.admin.security;

import javax.annotation.Resource;

import com.zitop.security.entity.SecurityResource;
import com.zitop.security.service.SecurityResourceService;
import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBasePaginationAction;

public class SecurityResourceListAction extends ServiceBasePaginationAction<SecurityResource, Long>
{
	private static final long serialVersionUID = -7003632976108880733L;
	@Resource
	private SecurityResourceService securityResourceService;
	
	@Override
	public IGenericService<SecurityResource, Long> getGenericService()
	{
		return securityResourceService;
	}

	@Override
	public void preExecute()
	{
		
	}

}
