package com.zitop.security.web.actions.admin.security;

import javax.annotation.Resource;

import com.zitop.security.entity.SecurityResource;
import com.zitop.security.service.SecurityResourceService;
import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBaseManageAction;

public class SecurityResourceAction extends ServiceBaseManageAction<SecurityResource,Long>
{
	private static final long serialVersionUID = -8374140252888801540L;
	@Resource
	private SecurityResourceService securityResourceService;
	private SecurityResource securityResource;
	@Override
	public IGenericService<SecurityResource, Long> getGenericService()
	{
		return securityResourceService;
	}

	public SecurityResource getModel()
	{
		return securityResource;
	}

	public void prepare() throws Exception
	{
		if (getRequestId() == null || getRequestId() == 0)
		{
			securityResource = new SecurityResource();
		} else
		{
			securityResource = securityResourceService.getEntityById(getRequestId());
		}
	}

}
