package com.zitop.security.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

/**用户角色对应表
 * @author william
 *
 */
@Entity
public class UserRole implements Serializable
{
	private static final long serialVersionUID = 903881839849340034L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long userId;
	private Long roleId;
	
	@Version
	private long version;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getUserId()
	{
		return userId;
	}
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	public Long getRoleId()
	{
		return roleId;
	}
	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}
	public long getVersion()
	{
		return version;
	}
	public void setVersion(long version)
	{
		this.version = version;
	}
}
