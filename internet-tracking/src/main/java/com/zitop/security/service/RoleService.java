package com.zitop.security.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zitop.infrastructure.service.impl.AbstractService;
import com.zitop.infrastructure.util.Pager;
import com.zitop.security.dao.IRoleDAO;
import com.zitop.security.dao.IUserRoleDAO;
import com.zitop.security.entity.Role;
import com.zitop.security.entity.SecurityResource;
import com.zitop.security.service.extend.ISecurityResourceCache;

@Service
public class RoleService extends AbstractService<Role, Long, IRoleDAO>
{
	@Resource
	private IRoleDAO roleDAO;
	@Resource
	private ISecurityResourceCache securityResourceCache;
	
	@Resource
	private IUserRoleDAO userRoleDAO;
	
	
	@Override
	public void delete(Role entity)
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
	public Role insert(Role entity)
	{
		Role role = super.insert(entity);
		securityResourceCache.reflush();
		return role;
	}

	@Override
	public Role saveOrUpdate(Role entity)
	{
		Role role =  super.saveOrUpdate(entity);
		securityResourceCache.reflush();
		return role;
	}

	@Override
	public Role update(Role entity)
	{
		Role role =  super.update(entity);
		securityResourceCache.reflush();
		return role;
	}
	
	/**给某角色加上某权限
	 * @param role
	 * @param securityResource
	 * @author william
	 */
	public void grantSecurityResource(Role role,SecurityResource securityResource)
	{
		role.getSecurityResources().add(securityResource);
		roleDAO.update(role);
		securityResourceCache.reflush();
	}
	/**将某角色的某权限去掉
	 * @param role
	 * @param securityResource
	 * @author william
	 */
	public void revokeSecurityResource(Role role,SecurityResource securityResource)
	{
		role.getSecurityResources().remove(securityResource);
		roleDAO.update(role);
		securityResourceCache.reflush();
	}

	/**取得角色及角色下的所有安全资源权限
	 * @return
	 * @author william
	 */
	public List<Role> getAllRoleAndResource()
	{
		return roleDAO.getAllRoleAndResource();
	}
	
	/**根据用户ID取得用户拥有的角色集合
	 * @param userId 用户ID
	 * @return 角色集合
	 * @author william
	 */
	public List<Role> getRoleByUserId(Long userId)
	{
		return userRoleDAO.getRoleByUserId(userId);
	}
	

	/**根据角色分配到用户查询（将角色绑定到某一用户情况）
	 * @param pager
	 * @author william
	 */
	public void getRolesByAllocated(Pager<Role> pager)
	{
		List<Role> roles = roleDAO.getRoleByAllocated(pager.getParamCondition(), pager.getFirstResult(), pager.getPageSize());
		int count = roleDAO.geCountOfRoleByAllocated(pager.getParamCondition());
		pager.setItems(roles);
		pager.setCountOfTotalItem(count);
	}
	
	@Override
	public IRoleDAO getGenericDAO()
	{
		return roleDAO;
	}
}
