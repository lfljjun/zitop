package com.zitop.security.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.zitop.infrastructure.dao.hibernate.GenericHibernateDAO;
import com.zitop.infrastructure.springframework.ChildOf;
import com.zitop.security.dao.IUserRoleDAO;
import com.zitop.security.entity.Role;
import com.zitop.security.entity.User;
import com.zitop.security.entity.UserRole;

@ChildOf(parent = "genericHibernateDAO")
@Repository
public class UserRoleDAOImpl extends GenericHibernateDAO<UserRole, Long> implements IUserRoleDAO
{

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoleByUserId(Long userId)
	{
		String hql = "select r from UserRole ur, Role r where ur.roleId = r.id and ur.userId = :userId ";
		Query query = getSession().createQuery(hql);
		query.setLong("userId", userId);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserByRoleId(Long roleId)
	{
		String hql = "select u from UserRole ur, User u where ur.userId = u.id and ur.roleId = :roleId ";
		Query query = getSession().createQuery(hql);
		query.setLong("roleId", roleId);
		return query.list();
	}
	@Override
	public void delUserRole(Long userId, Long roleId)
	{
		String hql = "delete UserRole ur where ur.userId = :userId and ur.roleId = :roleId ";
		Query query = getSession().createQuery(hql);
		query.setLong("userId", userId);
		query.setLong("roleId", roleId);
		query.executeUpdate();
	}
	@Override
	public void addUserRole(Long userId, Long roleId)
	{
		UserRole ur= new UserRole();
		ur.setUserId(userId);
		ur.setRoleId(roleId);
		insert(ur);
	}
	@Override
	public void delUserRoleByUserId(Long userId) {
		String hql = "delete UserRole ur where ur.userId = :userId ";
		Query query = getSession().createQuery(hql);
		query.setLong("userId", userId);
		query.executeUpdate();
	}

}
