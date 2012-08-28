package com.zitop.evaluate.vo;

import java.util.Date;

/**
 * MKB系统用户
 * 
 * @author wuwenyi
 * 
 */

public class MkbSystemUserVO 
{
	private String username;//登录录帐号
	private String password;//密码
	private String name;
	//private String lastName;
	private String company;//公司
	private String department;//部门
	private String email;//email
	private String homePhone;//联系电话
	private String mobileTelephone;//手机
	private String phs;//小灵通
	private boolean deleted = false;
	private int auth = 0;
	private Date createTime;
	private Date lastModifyTime;
	private String remark;
	private Long groupId;//用户所在组
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getCompany()
	{
		return company;
	}
	public void setCompany(String company)
	{
		this.company = company;
	}
	public String getDepartment()
	{
		return department;
	}
	public void setDepartment(String department)
	{
		this.department = department;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getHomePhone()
	{
		return homePhone;
	}
	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}
	public String getMobileTelephone()
	{
		return mobileTelephone;
	}
	public void setMobileTelephone(String mobileTelephone)
	{
		this.mobileTelephone = mobileTelephone;
	}
	public String getPhs()
	{
		return phs;
	}
	public void setPhs(String phs)
	{
		this.phs = phs;
	}
	public boolean isDeleted()
	{
		return deleted;
	}
	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}
	public int getAuth()
	{
		return auth;
	}
	public void setAuth(int auth)
	{
		this.auth = auth;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public Date getLastModifyTime()
	{
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime)
	{
		this.lastModifyTime = lastModifyTime;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	

}