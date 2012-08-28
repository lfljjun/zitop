package com.zitop.evaluate.dao;

import com.zitop.evaluate.entity.SystemUser;

public interface ISystemUserDAO
{

	/**
	 * 根据用户帐号取得一个系统用户
	 * 
	 * @param userAccount
	 * @return 系统用户VO
	 * @throws DAOException
	 *             数据访问层异常
	 */
	public SystemUser getSystemUser(String userAccount) ;

}
