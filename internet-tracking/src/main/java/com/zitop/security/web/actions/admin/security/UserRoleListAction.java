package com.zitop.security.web.actions.admin.security;

import java.util.List;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionSupport;
import com.zitop.infrastructure.struts2.interceptor.PagerAware;
import com.zitop.infrastructure.struts2.interceptor.RememberParamAware;
import com.zitop.infrastructure.util.Pager;
import com.zitop.security.entity.Role;
import com.zitop.security.entity.User;
import com.zitop.security.service.RoleService;
import com.zitop.security.service.UserService;

/**
 * 用户下分配角色情况列表
 * 
 * @author william
 * 
 */
public class UserRoleListAction extends ActionSupport implements PagerAware<Role>, RememberParamAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1930126205601785740L;
	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;

	private Pager<Role> pager;
	private Long userId;
	private User user;
	private List<Role> roles;

	// 查询条件
	private String code;
	private String name;
	private String allocated;

	public String execute()
	{
		user = userService.getEntityById(userId);
		roles = roleService.getRoleByUserId(userId);
		roleService.getRolesByAllocated(pager);
		return SUCCESS;
	}

	public RoleService getRoleService()
	{
		return roleService;
	}

	public void setRoleService(RoleService roleService)
	{
		this.roleService = roleService;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	public Pager<Role> getPager()
	{
		return pager;
	}

	public void setPager(Pager<Role> pager)
	{
		this.pager = pager;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public List<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(List<Role> roles)
	{
		this.roles = roles;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAllocated()
	{
		return allocated;
	}

	public void setAllocated(String allocated)
	{
		this.allocated = allocated;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
