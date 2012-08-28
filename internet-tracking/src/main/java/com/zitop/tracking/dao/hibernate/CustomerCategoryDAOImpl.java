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
import com.zitop.tracking.dao.ICustomerCategoryDAO;
import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.Project;

@ChildOf(parent = "genericHibernateDAO")
@Repository
public class CustomerCategoryDAOImpl extends GenericHibernateDAO<CustomerCategory, Long> implements ICustomerCategoryDAO {
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerCategory> getEntitiesByParamCondition(ParamCondition paramCondition, int firstResult, int pageSize) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		// 通过参数解释器解释出相应的查询条件
		setCriterions(crit, paramCondition);
		setOrder(crit, paramCondition);

		if (firstResult >= 0 && pageSize >= 0) {
			crit.setMaxResults(pageSize);
			crit.setFirstResult(firstResult);
		}
		// 查询出相应的实体对象
		return crit.list();
	}

	/**
	 * 根据参数获取符合条件的元素个数
	 * 
	 * @param paramCondition
	 * @return
	 */
	public int getCountOfEntitiesByParamCondition(ParamCondition paramCondition) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		// 通过参数解释器解释出相应的查询条件
		setCriterions(crit, paramCondition);
		crit.setProjection(Projections.rowCount());
		// 查询出总对象数
		int countOfEntity = ((Number) crit.uniqueResult()).intValue();
		return countOfEntity;
	}

	/**
	 * 添加参数
	 * 
	 * @param criteria
	 * @param paramCondition
	 */
	private void setCriterions(Criteria criteria, ParamCondition paramCondition) {

		// 处理普通参数
		String parentId = paramCondition.getParameter("currentParentId");
		if (StringUtils.isNotEmpty(parentId)) {
			criteria.add(Restrictions.eq("parentId", Long.parseLong(parentId)));
		}

		String projectId = paramCondition.getParameter("projectId");
		if (StringUtils.isNotEmpty(projectId)) {
			criteria.add(Restrictions.eq("project.id", Long.parseLong(projectId)));
		}

		String ids[] = paramCondition.get("ids");
		if (ids != null && ids.length > 0) {
			Long idL[] = new Long[ids.length];
			for (int i = 0; i < ids.length; i++) {
				idL[i] = Long.valueOf(ids[i]);
			}
			criteria.add(Restrictions.in("id", idL));
		}
		criteria.add(Restrictions.eq("deleted", false));
	}

	private void setOrder(Criteria crit, ParamCondition paramCondition) {
		String orders = paramCondition.getParameter("orders");
		if (StringUtils.isNotEmpty(orders)) {
			String[] orderArray = orders.split(",");
			for (String orderSource : orderArray) {
				String[] orderArray2 = orderSource.split("\\|");
				if (orderArray2.length == 2) {
					if ("desc".equalsIgnoreCase(orderArray2[1])) {
						crit.addOrder(Order.desc(orderArray2[0]));
					} else {
						crit.addOrder(Order.asc(orderArray2[0]));
					}
				} else if (orderArray2.length == 1) {
					crit.addOrder(Order.asc(orderArray2[0]));
				} else {

				}
			}
		} else {
			crit.addOrder(Order.desc("sequence")).addOrder(Order.asc("id"));
		}
	}

	/**
	 * 查找其的子分类
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerCategory> getSubEntities(CustomerCategory customerCategory) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("parentId", customerCategory.getId()));
		crit.add(Restrictions.eq("project.id", customerCategory.getProject().getId()));
		crit.add(Restrictions.eq("deleted", false));
		return crit.list();
	}

	/**
	 * 根据指定的id数组查询客户群数组
	 * 
	 * @param ids
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<CustomerCategory> getSubCustomerCategoryByIds(Long[] ids) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		if (ids != null)
			crit.add(Restrictions.in("id", ids));
		crit.add(Restrictions.eq("hasChilds", false));
		crit.addOrder(Order.desc("sequence")).addOrder(Order.asc("id"));
		return crit.list();
	}

	/**
	 * 获取子分类的个数
	 * 
	 * @param indexCategoryId
	 *            分类id
	 */
	@Override
	public int getCountByParentId(Long parentId) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("parentId", parentId));
		crit.setProjection(Projections.rowCount());
		return ((Number) crit.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerCategory> getEntitiesByProject(Project project) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("project.id", project.getId()));
		return crit.list();
	}

	@Override
	public List<CustomerCategory> getChildEntities() {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("hasChilds", false));
		crit.add(Restrictions.eq("deleted", false));
		crit.addOrder(Order.asc("id"));
		return crit.list();
	}
}
