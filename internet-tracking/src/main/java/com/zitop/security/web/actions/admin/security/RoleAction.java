package com.zitop.security.web.actions.admin.security;

import javax.annotation.Resource;

import com.zitop.security.entity.Role;
import com.zitop.security.service.RoleService;
import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBaseManageAction;

public class RoleAction extends ServiceBaseManageAction<Role,Long>
{
	private static final long serialVersionUID = -8462710654802266191L;
	@Resource
	private RoleService roleService;
	private Role role;
	@Override
	public IGenericService<Role, Long> getGenericService()
	{
		return roleService;
	}

	public Role getModel()
	{
		return role;
	}

	public void prepare() throws Exception
	{
		if (getRequestId() == null || getRequestId() == 0)
		{
			role = new Role();
		} else
		{
			role = roleService.getEntityById(getRequestId());
		}
	}

}
