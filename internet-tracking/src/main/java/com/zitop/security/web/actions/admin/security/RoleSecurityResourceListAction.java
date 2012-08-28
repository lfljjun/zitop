package com.zitop.security.web.actions.admin.security;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionSupport;
import com.zitop.infrastructure.struts2.interceptor.PagerAware;
import com.zitop.infrastructure.struts2.interceptor.RememberParamAware;
import com.zitop.infrastructure.util.Pager;
import com.zitop.security.entity.Role;
import com.zitop.security.entity.SecurityResource;
import com.zitop.security.service.RoleService;
import com.zitop.security.service.SecurityResourceService;

/**角色与安全资源关联列表
 * @author william
 *
 */
public class RoleSecurityResourceListAction extends ActionSupport implements PagerAware<SecurityResource>,RememberParamAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1930126205601785740L;
	
	@Resource
	private RoleService  roleService;
	@Resource
	private SecurityResourceService  securityResourceService;
	
	private Pager<SecurityResource> pager;
	private Long roleId;
	private Role role;
	
	//查询条件
	private String name;
	private String value;
	private String type;
	private String allocated;
	
	
	public String execute()
	{
		role = roleService.getEntityById(roleId);
		securityResourceService.getSecurityResourceByAllocated(pager);
		return SUCCESS;
	}
	
	@Override
	public void setPager(Pager<SecurityResource> pager)
	{
		this.pager = pager;		
	}
	public Pager<SecurityResource> getPager()
	{
		return pager;
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getAllocated()
	{
		return allocated;
	}

	public void setAllocated(String allocated)
	{
		this.allocated = allocated;
	}
}
