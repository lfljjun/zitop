package com.zitop.security.dao;

import java.util.List;

import com.zitop.infrastructure.dao.IGenericDAO;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.security.entity.User;

public interface IUserDAO extends IGenericDAO<User, Long>
{
	/**根据用户帐号取得User对象，如果没有则返回NULL
	 * @param username 用户帐号
	 * @return User对象
	 * @author william
	 */
	public User getUserByUsername(String username);
	
	/**根据用户分配到角色查询（将用户绑定到某一角色情况）
	 * @param param
	 * @return
	 * @author william
	 */
	public List<User> getUserByAllocated(ParamCondition param,int firstResult, int pageSize);
	/**根据用户分配到角色查询总数（将用户绑定到某一角色情况）
	 * @param param
	 * @return
	 * @author william 
	 */
	public int geCountOfUserByAllocated(ParamCondition param);

	
	/**通用查询
	 */
	public List<User> getUserByCondition(ParamCondition paramCondition,int firstResult,int pageSize);
	public int getCountOfUserByCondition(ParamCondition paramCondition);
	
}
