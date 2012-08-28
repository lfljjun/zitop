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
 * 数据项
 * @author Joseph
 */
@Entity
@Where(clause="deleted = 0")
public class DataItem extends UsingLogger implements Serializable
{
	private static final long serialVersionUID = 3648891614544453382L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    /** 期数id */
	@ManyToOne
	@JoinColumn(name="term_id")
	public Term term;
	/** 指标  */
	@ManyToOne
	@JoinColumn(name="index_id")
	public IndexItem indexItem;
	/** 客户群id */
	@ManyToOne
	@JoinColumn(name="customer_category_id")
	public CustomerCategory customerCategory;
	/** 指标值 */
	public Double value;
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
	public Term getTerm() {
		return term;
	}
	public IndexItem getIndexItem() {
		return indexItem;
	}
	public Double getValue() {
		return value;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	public void setIndexItem(IndexItem indexItem) {
		this.indexItem = indexItem;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public CustomerCategory getCustomerCategory() {
		return customerCategory;
	}
	public void setCustomerCategory(CustomerCategory customerCategory) {
		this.customerCategory = customerCategory;
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
