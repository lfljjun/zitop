package com.zitop.security.dao;

import java.util.List;

import com.zitop.infrastructure.dao.IGenericDAO;
import com.zitop.security.entity.Role;
import com.zitop.security.entity.User;
import com.zitop.security.entity.UserRole;

public interface IUserRoleDAO extends IGenericDAO<UserRole, Long>
{
	/**根据用户ID取得用户拥有的角色集合
	 * @param userId 用户ID
	 * @return 角色集合
	 * @author william
	 */
	public List<Role> getRoleByUserId(Long userId);
	
	/**根据角色ID，取得角色下所用用户集合
	 * @param roleId 角色id
	 * @return 用户集合
	 * @author william
	 */
	public List<User> getUserByRoleId(Long roleId);
	
	/**删除某用户的某角色
	 * @param userId
	 * @param roleId
	 * @author william
	 */
	public void delUserRole(Long userId,Long roleId);
	/**为某用户增加某角色
	 * @param userId
	 * @param roleId
	 * @author william
	 */
	public void addUserRole(Long userId,Long roleId);
	
	/**删除某用户的角色
	 * @param userId
	 * @param roleId
	 * @author william
	 */
	public void delUserRoleByUserId(Long userId);
}
