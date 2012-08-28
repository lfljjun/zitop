package com.zitop.security.web.actions.admin.security;

import javax.annotation.Resource;

import com.zitop.security.entity.User;
import com.zitop.security.service.UserService;
import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBasePaginationAction;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;

public class UserListAction extends ServiceBasePaginationAction<User, Long>
{
	private static final long serialVersionUID = 7343112876989403726L;
	@Resource
	private UserService userService;
	
	private String username;
	
	@Override
	public IGenericService<User, Long> getGenericService()
	{
		return userService;
	}

	@Override
	public void preExecute()
	{
		
	}
	
	public String execute()
	{
		userService.getUsersByAllocated(getPager());
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
