package com.zitop.security.dao;

import java.util.List;

import com.zitop.infrastructure.dao.IGenericDAO;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.security.entity.SecurityResource;

public interface ISecurityResourceDAO extends IGenericDAO<SecurityResource, Long>
{
	/**取得资源及对应的角色(同时加载角色)
	 * @return
	 * @author william
	 */
	public List<SecurityResource> getAllSecurityResourceAndRoles();
	
	/**根据资源分配情况查询资源，用户角色权限分配
	 * @param param
	 * @return
	 * @author william
	 */
	public List<SecurityResource> getSecurityResourceByAllocated(ParamCondition param,int firstResult, int pageSize);
	/**根据资源分配情况查询资源总数，用户角色权限分配
	 * @param param
	 * @return
	 * @author william 
	 */
	public int geCountOfSecurityResourceByAllocated(ParamCondition param);
}
