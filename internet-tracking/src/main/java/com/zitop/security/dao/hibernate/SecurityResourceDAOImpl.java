package com.zitop.security.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.zitop.infrastructure.dao.hibernate.GenericHibernateDAO;
import com.zitop.infrastructure.springframework.ChildOf;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.security.dao.ISecurityResourceDAO;
import com.zitop.security.entity.SecurityResource;

@ChildOf(parent = "genericHibernateDAO")
@Repository
public class SecurityResourceDAOImpl extends GenericHibernateDAO<SecurityResource, Long> implements
		ISecurityResourceDAO
{

	@Override
	@SuppressWarnings("unchecked")
	public List<SecurityResource> getAllSecurityResourceAndRoles()
	{
		String hql = " select s from SecurityResource s left outer join fetch  s.roles where s.enabled = true ";
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	@Override
	public int geCountOfSecurityResourceByAllocated(ParamCondition param)
	{
		RoleAllocatedQueryParameterParser parser = new RoleAllocatedQueryParameterParser();
		// 通过参数解释器解释出相应的查询条件
		List<Criterion> criterions = parser.getCriterions(param);
		// 查询出总对象数
		int countOfEntity = getCountOfEntity(criterions);

		return countOfEntity;
	}

	@Override
	public List<SecurityResource> getSecurityResourceByAllocated(ParamCondition param, int firstResult, int pageSize)
	{
		RoleAllocatedQueryParameterParser parser = new RoleAllocatedQueryParameterParser();

		// 通过参数解释器解释出相应的查询条件
		List<Criterion> criterions = parser.getCriterions(param);
		// 通过参数解释器解释出排序方式
		List<Order> orders = parser.getOrder(param);
		// 查询出相应的实体对象
		List<SecurityResource> entitys = findEntityByCriteria(criterions, firstResult, pageSize, orders);
		return entitys;

	}

}

/**
 * 分类角色权限时查询资源参数解释器
 * 
 * @author william
 * 
 */
class RoleAllocatedQueryParameterParser
{

	/**
	 * 根据查询参数来解释相应的查询条件
	 * 
	 * @see com.zitop.common.dao.hibernate.IQueryParameterParser#getCriterions(com.zitop.common.util.Pager)
	 */
	public List<Criterion> getCriterions(ParamCondition param)
	{
		ArrayList<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("enabled", true));
		String type = param.getParameter("type");
		if (StringUtils.hasText(type))
		{
			criterions.add(Restrictions.eq("type", type));
		}
		String name = param.getParameter("name");
		if (StringUtils.hasText(name))
		{
			criterions.add(Restrictions.like("name", "%" + name + "%"));
		}
		String value = param.getParameter("value");
		if (StringUtils.hasText(value))
		{
			criterions.add(Restrictions.like("value", "%" + value + "%"));
		}

		String allocated = param.getParameter("allocated");
		if (StringUtils.hasText(allocated))
		{
			Long roleId = param.getLong("roleId");
			if ("true".equals(allocated))
			{
				criterions
						.add(Restrictions
								.sqlRestriction("  {alias}.id in (select security_resource_id from role_security_resource where role_id = "
										+ roleId + ")"));
			} else if ("false".equals(allocated))
			{
				criterions
						.add(Restrictions
								.sqlRestriction("  {alias}.id not in (select security_resource_id from role_security_resource where role_id = "
										+ roleId + ")"));
			}
		}

		return criterions;
	}

	/**
	 * 根据查询排序设置来解释排序方式
	 * 
	 * @see com.zitop.common.dao.hibernate.IQueryParameterParser#getOrder(com.zitop.common.util.Pager)
	 */
	public List<Order> getOrder(ParamCondition param)
	{
		ArrayList<Order> orders = new ArrayList<Order>();
		return orders;
	}

}