package com.zitop.tracking.entity;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class UsingLogger {

	/** 创建人 */
	public java.lang.String creator;
	/** 创建时间 */
	public java.util.Date createTime;
	/** 修改人 */
	public java.lang.String modifier;
	/** 修改时间 */
	public java.util.Date modifyTime;

	public void setCreator(java.lang.String creator) {
		this.creator = creator;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public void setModifier(java.lang.String modifier) {
		this.modifier = modifier;
	}
	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public java.lang.String getCreator() {
		return creator;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public java.lang.String getModifier() {
		return modifier;
	}
	public java.util.Date getModifyTime() {
		return modifyTime;
	}
	
}