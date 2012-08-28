package com.zitop.evaluate.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zitop.evaluate.dao.ISystemUserDAO;
import com.zitop.evaluate.entity.SystemUser;
import com.zitop.infrastructure.dao.hibernate.GenericHibernateDAO;
import com.zitop.infrastructure.springframework.ChildOf;

@ChildOf(parent = "genericHibernateDAO")
@Repository
public class SystemUserDAOImpl extends GenericHibernateDAO<SystemUser, Long> implements ISystemUserDAO {

	public SystemUser getSystemUser(String userAccount) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("userAccount", userAccount));
		return (SystemUser) crit.uniqueResult();
	}

}
