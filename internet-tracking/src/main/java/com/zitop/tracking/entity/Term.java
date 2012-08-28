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
 * 期数
 * @author Joseph
 */
@Entity
@Where(clause="deleted = 0")
public class Term extends UsingLogger implements Serializable
{
	private static final long serialVersionUID = -5259413400400328465L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** 项目 */
	@ManyToOne
	@JoinColumn(name="project_id")
	public Project project;
	/** 名称 */
	public java.lang.String name;
	/** 月 */
	public Integer statYear;
	/** 日 */
	public Integer statMonth;
	/** 备注 */
	public String intro;
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
	public Boolean getDeleted() {
		return deleted;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public void setName(java.lang.String name) {
		this.name = name;
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
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Integer getStatYear() {
		return statYear;
	}
	public Integer getStatMonth() {
		return statMonth;
	}
	public String getIntro() {
		return intro;
	}
	public void setStatYear(Integer statYear) {
		this.statYear = statYear;
	}
	public void setStatMonth(Integer statMonth) {
		this.statMonth = statMonth;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
}
