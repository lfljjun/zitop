package com.zitop.tracking.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;

import org.hibernate.annotations.Where;

/**
 * 指标分类
 * 
 * @author Joseph
 */
@Entity
@Where(clause="deleted = 0")
public class IndexCategory extends UsingLogger implements Serializable {
	private static final long serialVersionUID = 4002463918911529134L;

	/** 线图 */
	public final static int GRAPHTYPE_LINE = 0;
	/** 饼图 */
	public final static int GRAPHTYPE_PIE = 1;
	/** 柱状图 */
	public final static int GRAPHTYPE_COLUMN = 2;
	/** 条状图 */
	public final static int GRAPHTYPE_BAR = 3;
	/** 面状图 */
	public final static int GRAPHTYPE_AREA = 4;
	/** 散列图 */
	public final static int GRAPHTYPE_SCATTER = 5;

	public final static String[] GRAPH_TYPE_ARRAY = { "线图", "饼图", "柱形图", "条形图", "面状图", "散列图" };

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** 项目 */
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	/** 名称 */
	private java.lang.String name;
	/** 父分类id */
	private Long parentId = 0L;
	/** 是否有子分类 */
	private Boolean hasChilds;
	/** 展现方式 */
	private Integer graphType = GRAPHTYPE_LINE;
	/** 序号 */
	public Integer sequence = 0;
	/** 是否删除 */
	private Boolean deleted = false;
	/** 关联指标 */
	@OneToMany(mappedBy="indexCategory")
	@OrderBy(value="sequence desc , id asc")
	private List<IndexItem> indexItems;
	
	@Version
	private long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Integer getGraphType() {
		return graphType;
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

	public void setGraphType(Integer graphType) {
		this.graphType = graphType;
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

	public List<IndexItem> getIndexItems() {
		return indexItems;
	}

	public void setIndexItems(List<IndexItem> indexItems) {
		this.indexItems = indexItems;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}
