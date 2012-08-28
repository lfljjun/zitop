package com.zitop.security.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class UserGroup implements Serializable
{
    private static final long serialVersionUID = -2055744536060998661L;
    @Id
	private long groupId;
	private String groupName;
	private String groupDes;
	private boolean enabled = true;
	private Date createTime;
	private int leftNum;
	private int rightNum;
	private Long parentGroupId;
	private Boolean tagIt;
	@Version
	private Long version;
	
	public long getGroupId()
	{
		return groupId;
	}
	public void setGroupId(long groupId)
	{
		this.groupId = groupId;
	}
	public String getGroupName()
	{
		return groupName;
	}
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}
	public String getGroupDes()
	{
		return groupDes;
	}
	public void setGroupDes(String groupDes)
	{
		this.groupDes = groupDes;
	}
	public boolean isEnabled()
	{
		return enabled;
	}
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public int getLeftNum()
	{
		return leftNum;
	}
	public void setLeftNum(int leftNum)
	{
		this.leftNum = leftNum;
	}
	public int getRightNum()
	{
		return rightNum;
	}
	public void setRightNum(int rightNum)
	{
		this.rightNum = rightNum;
	}
	public Long getParentGroupId() {
		return parentGroupId;
	}
	public void setParentGroupId(Long parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
	public Boolean getTagIt() {
		return tagIt;
	}
	public void setTagIt(Boolean tagIt) {
		this.tagIt = tagIt;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}

	
	
	
}
