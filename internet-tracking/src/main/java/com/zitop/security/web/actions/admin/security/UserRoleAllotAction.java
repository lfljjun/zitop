package com.zitop.security.web.actions.admin.security;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.zitop.security.service.UserService;

/**用户角色分配Action
 * @author william
 *
 */
@Results({    
	  @Result(name="success",type="redirect", location="/admin/security/user-role-list.action?userId=${userId}&restore_params=true")    
})   
public class UserRoleAllotAction extends ActionSupport
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9137109281596566559L;
	
	private Long roleId;
	private Long userId;
	
	@Resource
	private UserService userService;
	
       
   @Action(value="/admin/security/role-user-grant",
    			results={
    				@Result(name="success",
    						type="redirect", 
    						location="/admin/security/role-user-list.action?roleId=${roleId}&restore_params=true")
    })  
	public String grant()
	{
		userService.grantRole(userId, roleId);
		return SUCCESS;
	}
    @Action(value="/admin/security/role-user-revoke",
    			results={
    				@Result(name="success",
    						type="redirect", 
    						location="/admin/security/role-user-list.action?roleId=${roleId}&restore_params=true")
    }) 
	public String revoke()
	{
		userService.revokeRole(userId, roleId);
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

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	
}
