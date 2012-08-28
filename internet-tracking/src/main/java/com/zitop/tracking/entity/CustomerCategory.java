package com.zitop.tracking.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.hibernate.annotations.Where;

/**
 * 客户群
 * @author Joseph
 */
@Entity
@Where(clause="deleted = 0")
public class CustomerCategory extends UsingLogger implements Serializable
{
	private static final long serialVersionUID = 2489544672381875369L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** 项目 */
	@ManyToOne
	@JoinColumn(name="project_id")
	public Project project;
	/** 名称 */
	public java.lang.String name;
	/** 父分类id */
	public Long parentId;
	/** 是否有子分类 */
	public Boolean hasChilds;
	/** 序号 */
	public Integer sequence = 0;
	/** 是否删除 */
	public Boolean deleted = false;

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
	public Project getProject() {
		return project;
	}
	public java.lang.String getName() {
		return name;
	}
	public Long getParentId() {
		return parentId;
	}
	public Boolean getHasChilds() {
		return hasChilds;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public void setHasChilds(Boolean hasChilds) {
		this.hasChilds = hasChilds;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
