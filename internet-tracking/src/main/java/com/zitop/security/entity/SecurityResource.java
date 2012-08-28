package com.zitop.security.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

@Entity
public class SecurityResource implements Serializable
{
	private static final long serialVersionUID = -8556037339704688151L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String type;//安全资源类型
	private String value;//值
	private String name;//名称
	private String description;//描述
	private boolean preinstall = false;//是否预设
	private boolean showed = true;//是否显示
	private boolean enabled = true;//是否启用
	@ManyToMany(mappedBy = "securityResources")
	private Set<Role> roles;
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
	
	public Set<Role> getRoles()
	{
		return roles;
	}
	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public boolean isPreinstall()
	{
		return preinstall;
	}
	public void setPreinstall(boolean preinstall)
	{
		this.preinstall = preinstall;
	}
	public boolean isShowed()
	{
		return showed;
	}
	public void setShowed(boolean showed)
	{
		this.showed = showed;
	}
	public boolean isEnabled()
	{
		return enabled;
	}
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
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
