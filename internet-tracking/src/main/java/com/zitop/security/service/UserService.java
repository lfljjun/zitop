package com.zitop.security.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zitop.infrastructure.service.impl.AbstractService;
import com.zitop.infrastructure.util.Pager;
import com.zitop.security.dao.IUserDAO;
import com.zitop.security.dao.IUserRoleDAO;
import com.zitop.security.entity.User;
import com.zitop.security.service.extend.ISecurityResourceCache;

@Service
public class UserService extends AbstractService<User, Long, IUserDAO>
{
	@Resource
	private IUserDAO userDAO;
	
	@Resource
	private IUserRoleDAO userRoleDAO;
	
	@Resource
	private ISecurityResourceCache securityResourceCache;
	
	
	/**将某角色授权给某用户
	 * @param userId
	 * @param roleId
	 * @author william
	 */
	public void grantRole(Long userId,Long roleId)
	{
		userRoleDAO.addUserRole(userId, roleId);
		securityResourceCache.reflush();
	}
	
	/**将某用户的某角色取消
	 * @param userId
	 * @param roleId
	 * @author william
	 */
	public void revokeRole(Long userId,Long roleId)
	{
		userRoleDAO.delUserRole(userId, roleId);
		securityResourceCache.reflush();
	}
	
	@Override
	public void delete(User entity)
	{
		super.delete(entity);
		securityResourceCache.reflush();
	}


	@Override
	public void deleteEntityById(Long id)
	{
		super.deleteEntityById(id);
		securityResourceCache.reflush();
	}


	@Override
	public User insert(User entity)
	{
		User user =  super.insert(entity);
		securityResourceCache.reflush();
		return user;
	}


	@Override
	public User saveOrUpdate(User entity)
	{
		User user =  super.saveOrUpdate(entity);
		securityResourceCache.reflush();
		return user;
	}


	@Override
	public User update(User entity)
	{
		User user =  super.update(entity);
		securityResourceCache.reflush();
		return user;
	}


	/**根据用户帐号取得User对象，如果没有则返回NULL
	 * @param username 用户帐号
	 * @return User对象
	 * @author william
	 */
	public User getUserByUsername(String username)
	{
		return userDAO.getUserByUsername(username);
	}
	
	
	/**根据角色ID，取得角色下所用用户集合
	 * @param roleId 角色id
	 * @return 用户集合
	 * @author william
	 */
	public List<User> getUserByRoleId(Long roleId)
	{
		return userRoleDAO.getUserByRoleId(roleId);
	}
	
	/**根据用户分配到角色查询（将用户绑定到某一角色情况）
	 * @param pager
	 * @author william
	 */
	public void getUsersByAllocated(Pager<User> pager)
	{
		List<User> roles = userDAO.getUserByAllocated(pager.getParamCondition(), pager.getFirstResult(), pager.getPageSize());
		int count = userDAO.geCountOfUserByAllocated(pager.getParamCondition());
		pager.setItems(roles);
		pager.setCountOfTotalItem(count);
	}

	@Override
	public IUserDAO getGenericDAO()
	{
		return userDAO;
	}
	
	
	/**通用查询
	 * @param pager
	 */
	public void getUserByCondition(Pager<User> pager)
	{
		
	}
}
