package com.zitop.security.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.zitop.infrastructure.dao.hibernate.GenericHibernateDAO;
import com.zitop.infrastructure.springframework.ChildOf;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.security.dao.IUserDAO;
import com.zitop.security.entity.User;

@ChildOf(parent = "genericHibernateDAO")
@Repository
public class UserDAOImpl extends GenericHibernateDAO<User, Long> implements IUserDAO
{

	@Override
	public User getUserByUsername(String username)
	{
		Criteria crit = getSession().createCriteria(User.class);
		crit.add(Restrictions.eq("username", username));
		Object o = crit.uniqueResult();
		 
		
		return o != null?(User)o:null;
	}
	
	@Override
	public int geCountOfUserByAllocated(ParamCondition param)
	{
		RoleUserAllocatedQueryParameterParser parser = new RoleUserAllocatedQueryParameterParser();
		// 通过参数解释器解释出相应的查询条件
		List<Criterion> criterions = parser.getCriterions(param);
		// 查询出总对象数
		int countOfEntity = getCountOfEntity(criterions);

		return countOfEntity;
	}

	@Override
	public List<User> getUserByAllocated(ParamCondition param, int firstResult, int pageSize)
	{
		RoleUserAllocatedQueryParameterParser parser = new RoleUserAllocatedQueryParameterParser();

		// 通过参数解释器解释出相应的查询条件
		List<Criterion> criterions = parser.getCriterions(param);
		// 通过参数解释器解释出排序方式
		List<Order> orders = parser.getOrder(param);
		// 查询出相应的实体对象
		List<User> entitys = findEntityByCriteria(criterions, firstResult, pageSize, orders);
		return entitys;
	}

	@Override
	public int getCountOfUserByCondition(ParamCondition paramCondition) {
		UserQueryParameterParser parser = new UserQueryParameterParser();
		// 通过参数解释器解释出相应的查询条件
		List<Criterion> criterions = parser.getCriterions(paramCondition);
		// 查询出总对象数
		int countOfEntity = getCountOfEntity(criterions);

		return countOfEntity;
	}

	@Override
	public List<User> getUserByCondition(ParamCondition paramCondition,
			int firstResult, int pageSize) {
		UserQueryParameterParser parser = new UserQueryParameterParser();

		// 通过参数解释器解释出相应的查询条件
		List<Criterion> criterions = parser.getCriterions(paramCondition);
		// 通过参数解释器解释出排序方式
		List<Order> orders = parser.getOrder(paramCondition);
		// 查询出相应的实体对象
		List<User> entitys = findEntityByCriteria(criterions, firstResult, pageSize, orders);
		return entitys;
	}

}

/**
 * 查询角色下分配用户情况
 * 
 * @author william
 * 
 */
class RoleUserAllocatedQueryParameterParser
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
		String username = param.getParameter("username");
		if (StringUtils.hasText(username))
		{
			criterions.add(Restrictions.like("username", "%" + username + "%"));
		}

		String allocated = param.getParameter("allocated");
		if (StringUtils.hasText(allocated))
		{
			Long roleId = param.getLong("roleId");
			if ("true".equals(allocated))
			{
				criterions
						.add(Restrictions
								.sqlRestriction("  {alias}.id in (select user_id from user_role where role_id = "
										+ roleId + ")"));
			} else if ("false".equals(allocated))
			{
				criterions
						.add(Restrictions
								.sqlRestriction("  {alias}.id not in (select user_id from user_role where role_id = "
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

/**
 * 通用查询
 */
class UserQueryParameterParser
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
		String username = param.getParameter("username");
		if (StringUtils.hasText(username))
		{
			criterions.add(Restrictions.like("username", "%" + username + "%"));
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
