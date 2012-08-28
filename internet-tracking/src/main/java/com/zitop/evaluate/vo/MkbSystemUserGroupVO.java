package com.zitop.evaluate.vo;

import java.util.Date;

public class MkbSystemUserGroupVO {
	private long groupId;
	private String groupName;
	private String groupDes;
	private Boolean enabled ;
	private Date createTime;
	private int leftNum;
	private int rightNum;
	private Long parentId;
	
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDes() {
		return groupDes;
	}
	public void setGroupDes(String groupDes) {
		this.groupDes = groupDes;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getLeftNum() {
		return leftNum;
	}
	public void setLeftNum(int leftNum) {
		this.leftNum = leftNum;
	}
	public int getRightNum() {
		return rightNum;
	}
	public void setRightNum(int rightNum) {
		this.rightNum = rightNum;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
}
