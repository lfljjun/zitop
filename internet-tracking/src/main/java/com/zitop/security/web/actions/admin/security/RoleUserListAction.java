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

/**角色下分配用户情况列表
 * @author william
 *
 */
public class RoleUserListAction extends ActionSupport implements PagerAware<User>,RememberParamAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1930126205601785740L;
	@Resource
	private RoleService  roleService;
	@Resource
	private UserService  userService;
	
	private Pager<User> pager;
	private Long roleId;
	private Role role;
	private List<User> roleUsers;
	
	//查询条件
	private String username;
	private String allocated;
	
	
	public String execute()
	{
		role = roleService.getEntityById(roleId);
		roleUsers = userService.getUserByRoleId(roleId);
		userService.getUsersByAllocated(pager);
		return SUCCESS;
	}
	

	public Pager<User> getPager()
	{
		return pager;
	}



	public void setPager(Pager<User> pager)
	{
		this.pager = pager;
	}



	public Long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public String getAllocated()
	{
		return allocated;
	}

	public void setAllocated(String allocated)
	{
		this.allocated = allocated;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}


	public List<User> getRoleUsers()
	{
		return roleUsers;
	}


	public void setRoleUsers(List<User> roleUsers)
	{
		this.roleUsers = roleUsers;
	}
}
