package com.zitop.security.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
public class Role implements Serializable
{
	private static final long serialVersionUID = -6476674202466399605L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String code;//角色代码
	private String name;//角色名称
	private String description;//描述
	private boolean preinstall = false;//预设
	private boolean showed = true;//是否显示
	private boolean enabled = true;//是否启用
	@ManyToMany
    @JoinTable(name = "role_security_resource", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "security_resource_id"))
    @Cascade(value={CascadeType.SAVE_UPDATE,CascadeType.PERSIST})
    private Set<SecurityResource> securityResources = new HashSet<SecurityResource>(0);
	
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
	public Set<SecurityResource> getSecurityResources()
	{
		return securityResources;
	}
	public void setSecurityResources(Set<SecurityResource> securityResources)
	{
		this.securityResources = securityResources;
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
