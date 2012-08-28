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
 * 指标
 * @author Joseph
 */
@Entity
@Where(clause="deleted = 0")
public class IndexItem extends UsingLogger implements Serializable
{
	private static final long serialVersionUID = 2846125216406409138L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** 分类 */
	@ManyToOne
	@JoinColumn(name="category_id")
	public IndexCategory indexCategory;
	/** 名称 */
	public java.lang.String name;
	/** 单位 */
	public java.lang.String unit;
	/** 说明 */
	public java.lang.String intro;
	/** 展现方式(预留字段，暂时不使用) */
	public Integer graphType = 1;
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
	
	public IndexCategory getIndexCategory() {
		return indexCategory;
	}
	public java.lang.String getName() {
		return name;
	}
	public java.lang.String getIntro() {
		return intro;
	}
	public Integer getGraphType() {
		return graphType;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setIndexCategory(IndexCategory indexCategory) {
		this.indexCategory = indexCategory;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public void setIntro(java.lang.String intro) {
		this.intro = intro;
	}
	public void setGraphType(Integer graphType) {
		this.graphType = graphType;
	}
	public java.lang.String getUnit() {
		return unit;
	}
	public void setUnit(java.lang.String unit) {
		this.unit = unit;
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
