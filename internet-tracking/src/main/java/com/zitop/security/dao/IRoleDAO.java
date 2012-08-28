package com.zitop.security.dao;

import java.util.List;

import com.zitop.infrastructure.dao.IGenericDAO;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.security.entity.Role;

public interface IRoleDAO extends IGenericDAO<Role, Long>
{
	/**取得角色及角色下的所有安全资源权限
	 * @return
	 * @author william
	 */
	public List<Role> getAllRoleAndResource();
	
	/**根据角色分配到用户查询（将角色绑定到某一用户情况）
	 * @param param
	 * @return
	 * @author william
	 */
	public int geCountOfRoleByAllocated(ParamCondition param);
	
	/**根据角色分配到用户查询总数（将角色绑定到某一用户情况）
	 * @param param
	 * @param firstResult
	 * @param pageSize
	 * @return
	 * @author william
	 */
	public List<Role> getRoleByAllocated(ParamCondition param, int firstResult, int pageSize);
}