package com.zitop.security.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zitop.infrastructure.dao.hibernate.GenericHibernateDAO;
import com.zitop.infrastructure.springframework.ChildOf;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.security.dao.IUserGroupDAO;
import com.zitop.security.entity.UserGroup;

@ChildOf(parent = "genericHibernateDAO")
@Repository
@SuppressWarnings("unchecked")
public class UserGroupDAOImpl extends GenericHibernateDAO<UserGroup, Long> implements IUserGroupDAO
{

	public List<UserGroup> getAllUserGroupNotDel()
	{
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.ne("isDeleted", true));
		return crit.list();
	}
	
	public int getCountOfUserGroupByCondition(ParamCondition paramCondition) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		setCriterions(crit,paramCondition);
		crit.setProjection(Projections.rowCount());
		return ((Number)crit.uniqueResult()).intValue();
	}

	
	public List<UserGroup> getUserGroupByCondition(ParamCondition paramCondition,
			int firstResult, int pageSize) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		setCriterions(crit,paramCondition);
		setOrders(crit,paramCondition);
		if(firstResult > -1 && pageSize > -1)
			crit.setFirstResult(firstResult).setMaxResults(pageSize);
		return crit.list();
	}

	protected void setCriterions(Criteria crit,ParamCondition paramCondition)
	{
		String name = paramCondition.getParameter("name");
		if(StringUtils.isNotBlank(name))
		{
			crit.add(Restrictions.like("name", "%"+name+"%"));
		}
		crit.add(Restrictions.ne("isDeleted", true));
	}
	
	protected void setOrders(Criteria crit,ParamCondition paramCondition)
	{
		crit.addOrder(Order.desc("id"));
	}
	
	public void markTag()
	{
		getSession().createQuery("update UserGroup set tagIt = false").executeUpdate();
	}
	
	public void delTag()
	{
		getSession().createQuery("delete UserGroup where tagIt = false").executeUpdate();
	}
}
