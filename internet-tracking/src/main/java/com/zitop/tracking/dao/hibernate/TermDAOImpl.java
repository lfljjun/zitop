package com.zitop.tracking.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zitop.infrastructure.dao.hibernate.GenericHibernateDAO;
import com.zitop.infrastructure.springframework.ChildOf;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.tracking.dao.ITermDAO;
import com.zitop.tracking.entity.Project;
import com.zitop.tracking.entity.Term;

@ChildOf(parent = "genericHibernateDAO")
@Repository
public class TermDAOImpl extends GenericHibernateDAO<Term, Long> implements ITermDAO {
	@SuppressWarnings("unchecked")
	public List<Term> getEntitiesByParamCondition(ParamCondition paramCondition, int firstResult, int pageSize) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		// 通过参数解释器解释出相应的查询条件
		setCriterions(crit, paramCondition);
		setOrder(crit, paramCondition);
		// 页码控制
		if (firstResult >= 0 && pageSize >= 0) {
			crit.setMaxResults(pageSize);
			crit.setFirstResult(firstResult);
		}
		// 查询出相应的实体对象
		return crit.list();
	}

	public int getCountOfEntitiesByParamCondition(ParamCondition paramCondition) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		// 通过参数解释器解释出相应的查询条件
		setCriterions(crit, paramCondition);
		crit.setProjection(Projections.rowCount());
		// 查询出总对象数
		int countOfEntity = ((Number) crit.uniqueResult()).intValue();
		return countOfEntity;
	}

	private void setCriterions(Criteria criteria, ParamCondition paramCondition) {
		String ids[] = paramCondition.get("ids");
		if (ids != null && ids.length > 0) {
			Long idL[] = new Long[ids.length];
			for (int i = 0; i < ids.length; i++) {
				idL[i] = Long.valueOf(ids[i]);
			}
			criteria.add(Restrictions.in("id", idL));
		}
		
		String creator = paramCondition.getParameter("creator");
		if (StringUtils.isNotEmpty(creator)) {
			criteria.add(Restrictions.eq("creator", creator));
		}
		
		String projectId = paramCondition.getParameter("projectId");
		if (StringUtils.isNotEmpty(projectId)) {
			criteria.add(Restrictions.eq("project.id", Long.valueOf(projectId)));
		}

		criteria.add(Restrictions.eq("deleted", false));
	}
	
	private void setOrder(Criteria crit, ParamCondition paramCondition) {
		crit.addOrder(Order.asc("statYear")).addOrder(Order.asc("statMonth")).addOrder(Order.asc("id"));
	}

	/**
	 * 根据指定的id数组查询期数数组
	 * @param ids
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Term> getTermByIds(Long[] ids) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		if(ids != null && ids.length >0)
			crit.add(Restrictions.in("id", ids));
		crit.addOrder(Order.asc("statYear")).addOrder(Order.asc("statMonth")).addOrder(Order.asc("id"));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Term> getEntitiesByProject(Project project) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("project.id", project.getId()));
		crit.addOrder(Order.asc("statYear")).addOrder(Order.asc("statMonth")).addOrder(Order.asc("id"));
		return crit.list();
	}

	@Override
	public List<Term> getAllEntity() {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.addOrder(Order.asc("statYear")).addOrder(Order.asc("statMonth")).addOrder(Order.asc("id"));
		return crit.list();
	}
	
	
}
