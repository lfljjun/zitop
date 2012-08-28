package com.zitop.security.web.actions.admin.security;

import javax.annotation.Resource;

import com.zitop.security.entity.Role;
import com.zitop.security.service.RoleService;
import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBasePaginationAction;

public class RoleListAction extends ServiceBasePaginationAction<Role, Long>
{
	private static final long serialVersionUID = -3442788424795763980L;
	@Resource
	private RoleService roleService;
	
	@Override
	public IGenericService<Role, Long> getGenericService()
	{
		return roleService;
	}

	@Override
	public void preExecute()
	{
		
	}

}
