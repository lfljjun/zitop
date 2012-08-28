package com.zitop.tracking.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zitop.infrastructure.dao.hibernate.GenericHibernateDAO;
import com.zitop.infrastructure.springframework.ChildOf;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.tracking.dao.IProjectDAO;
import com.zitop.tracking.entity.Project;

@ChildOf(parent = "genericHibernateDAO")
@Repository
public class ProjectDAOImpl extends GenericHibernateDAO<Project, Long> implements IProjectDAO
{

	@Override
	public int getCountOfEntitiesByParamCondition(ParamCondition paramCondition) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		
		setCriterions(paramCondition, crit);
		crit.setProjection(Projections.rowCount());
		
		return ((Number)crit.uniqueResult()).intValue();
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Project> getEntitiesByParamCondition(ParamCondition paramCondition, int firstResult, int pageSize) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		
		setCriterions(paramCondition, crit);
		setOrders(crit);
		
		if(firstResult > -1 && pageSize > -1)
		{
			crit.setFirstResult(firstResult).setMaxResults(pageSize);
		}
		
		crit.add(Restrictions.eq("deleted", false));
		
		return crit.list();
	}
	private void setOrders(Criteria crit) {
		crit.addOrder(Order.asc("id"));
	}
	private void setCriterions(ParamCondition paramCondition, Criteria crit) {
		String name = paramCondition.getParameter("name");
		if(StringUtils.isNotBlank(name))
		{
			crit.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
		}
	}

}
