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
import com.zitop.security.dao.IRoleDAO;
import com.zitop.security.entity.Role;

@ChildOf(parent = "genericHibernateDAO")
@Repository
public class RoleDAOImpl extends GenericHibernateDAO<Role, Long> implements IRoleDAO
{

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoleAndResource()
	{
		String hql = "select r from Role r join fetch r.securityResources where r.enabled = true ";
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	
	@Override
	public int geCountOfRoleByAllocated(ParamCondition param)
	{
		UserRoleAllocatedQueryParameterParser parser = new UserRoleAllocatedQueryParameterParser();
		// 通过参数解释器解释出相应的查询条件
		List<Criterion> criterions = parser.getCriterions(param);
		// 查询出总对象数
		int countOfEntity = getCountOfEntity(criterions);

		return countOfEntity;
	}

	@Override
	public List<Role> getRoleByAllocated(ParamCondition param, int firstResult, int pageSize)
	{
		UserRoleAllocatedQueryParameterParser parser = new UserRoleAllocatedQueryParameterParser();

		// 通过参数解释器解释出相应的查询条件
		List<Criterion> criterions = parser.getCriterions(param);
		// 通过参数解释器解释出排序方式
		List<Order> orders = parser.getOrder(param);
		// 查询出相应的实体对象
		List<Role> entitys = findEntityByCriteria(criterions, firstResult, pageSize, orders);
		return entitys;
	}

}

/**
 * 查询用户下分配角色情况
 * 
 * @author william
 * 
 */
class UserRoleAllocatedQueryParameterParser
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
		
		String code = param.getParameter("code");
		if (StringUtils.hasText(code))
		{
			criterions.add(Restrictions.like("code", "%" + code + "%"));
		}
		String name = param.getParameter("name");
		if (StringUtils.hasText(name))
		{
			criterions.add(Restrictions.like("name", "%" + name + "%"));
		}

		String allocated = param.getParameter("allocated");
		if (StringUtils.hasText(allocated))
		{
			Long userId = param.getLong("userId");
			if ("true".equals(allocated))
			{
				criterions
						.add(Restrictions
								.sqlRestriction("  {alias}.id in (select role_id from user_role where user_id = "
										+ userId + ")"));
			} else if ("false".equals(allocated))
			{
				criterions
						.add(Restrictions
								.sqlRestriction("  {alias}.id not in (select role_id from user_role where user_id = "
										+ userId + ")"));
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
