package com.zitop.tracking.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zitop.infrastructure.dao.hibernate.GenericHibernateDAO;
import com.zitop.infrastructure.springframework.ChildOf;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.tracking.dao.IIndexCategoryDAO;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.Project;

@ChildOf(parent = "genericHibernateDAO")
@Repository
public class IndexCategoryDAOImpl extends GenericHibernateDAO<IndexCategory, Long> implements IIndexCategoryDAO {

	@Override
	public int getCountOfEntitiesByParamCondition(ParamCondition paramCondition) {
		Criteria crit = getCriteria(paramCondition);
		int entryCount = (Integer) crit.setProjection(Projections.rowCount()).uniqueResult();
		return entryCount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndexCategory> getEntitiesByParamCondition(ParamCondition paramCondition, int firstResult, int pageSize) {
		Criteria crit = getCriteria(paramCondition);
		crit.setFirstResult(firstResult).setMaxResults(pageSize);
		crit.addOrder(Order.asc("id"));
		return crit.list();
	}

	private Criteria getCriteria(ParamCondition paramCondition) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		String parentId = paramCondition.getParameter("parentId");
		if (StringUtils.isNotBlank(parentId)) {
			crit.add(Expression.eq("parentId", Long.valueOf(parentId)));
		}
		String projectId = paramCondition.getParameter("projectId");
		if (StringUtils.isNotBlank(projectId)) {
			crit.add(Expression.eq("project.id", Long.valueOf(projectId)));
		}
		String name = paramCondition.getParameter("name");
		if (StringUtils.isNotBlank(name)) {
			crit.add(Expression.like("name", "%" + name + "%"));
		}
		return crit;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndexCategory> getEntitiesByParentId(Long parentId, Long entityId) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		if (parentId != null) {
			crit.add(Expression.eq("parentId", parentId));
		} else {
			crit.add(Expression.eq("hasChilds", true));
		}
		if (entityId != null) {
			crit.add(Expression.ne("id", entityId));
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndexCategory> getSubEntrities(IndexCategory indexCategory) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Expression.eq("parentId", indexCategory.getId()));
		crit.add(Restrictions.eq("project.id", indexCategory.getProject().getId()));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<IndexCategory> getRootEntities() {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Expression.eq("parentId", 0));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndexCategory> getEntitiesByProject(Project project) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("project.id", project.getId()));
		return crit.list();
	}

	@Override
	public String[] getAllCategoryIdsByProjectId(long projectId) {
		Project project=new Project();
		project.setId(projectId);
		List<IndexCategory> list=getEntitiesByProject(project);
		 String[] categoryIds=new  String[list.size()];
		for(int i=0;i<list.size();i++){
			categoryIds[i]=list.get(i).getId().toString();
		}
		return categoryIds;
	}
		
	/**
	 *  根据指定的id数组查询指标分类	
	 * @param ids
	 */
	@SuppressWarnings("unchecked")
	public List<IndexCategory> getIndexCategoryByIds(Long[] ids)
	{
		Criteria crit = getSession().createCriteria(getPersistentClass());
		if(ids != null)
			crit.add(Restrictions.in("id", ids));
		crit.addOrder(Order.desc("sequence")).addOrder(Order.asc("id"));
		return crit.list();
	}
}
