package com.zitop.tracking.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.Where;

/**
 * 项目
 * @author Joseph
 */
@Entity
@Where(clause="deleted = 0")
public class Project extends UsingLogger implements Serializable
{
	private static final long serialVersionUID = 5309023826297295043L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** 名称 */
	public java.lang.String name;
	/** 简介 */
	public java.lang.String intro;
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
	public java.lang.String getName() {
		return name;
	}
	public java.lang.String getIntro() {
		return intro;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public void setIntro(java.lang.String intro) {
		this.intro = intro;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public long getVersion()
	{
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
}
