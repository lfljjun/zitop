package com.zitop.security.web.actions.admin.security;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.zitop.security.entity.Role;
import com.zitop.security.entity.SecurityResource;
import com.zitop.security.service.RoleService;
import com.zitop.security.service.SecurityResourceService;

/**角色与权限分配Action
 * @author william
 *
 */
@Results({    
	  @Result(name="success",type="redirect", location="/admin/security/role-security-resource-list.action?roleId=${roleId}&restore_params=true")    
})    
public class RoleSecurityResourceAllotAction 	extends ActionSupport
{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long roleId;
	private Long securityResourceId;
	@Resource
	private RoleService roleService;
	@Resource
	private SecurityResourceService securityResourceService;
	/**将权限分配给角色
	 * @return
	 * @author william
	 */
	public String grant()
	{
		Role role = roleService.getEntityById(roleId);
		SecurityResource securityResource = securityResourceService.getEntityById(securityResourceId);
		roleService.grantSecurityResource(role, securityResource);
		return SUCCESS;
	}
	/**取消角色的某权限
	 * @return
	 * @author william
	 */
	public String revoke()
	{
		Role role = roleService.getEntityById(roleId);
		SecurityResource securityResource = securityResourceService.getEntityById(securityResourceId);
		roleService.revokeSecurityResource(role, securityResource);
		return SUCCESS;
	}
	public Long getRoleId()
	{
		return roleId;
	}
	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}
	public Long getSecurityResourceId()
	{
		return securityResourceId;
	}
	public void setSecurityResourceId(Long securityResourceId)
	{
		this.securityResourceId = securityResourceId;
	}
	
	

}
