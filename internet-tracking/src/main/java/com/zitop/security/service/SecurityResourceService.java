package com.zitop.security.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zitop.infrastructure.service.impl.AbstractService;
import com.zitop.infrastructure.util.Pager;
import com.zitop.security.dao.ISecurityResourceDAO;
import com.zitop.security.entity.SecurityResource;
import com.zitop.security.service.extend.ISecurityResourceCache;

@Service
public class SecurityResourceService extends AbstractService<SecurityResource, Long, ISecurityResourceDAO>
{
	@Resource
	private ISecurityResourceDAO securityResourceDAO;
	@Resource
	private ISecurityResourceCache securityResourceCache;
	
	@Override
	public void delete(SecurityResource entity)
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
	public SecurityResource insert(SecurityResource entity)
	{
		SecurityResource securityResource = super.insert(entity);
		securityResourceCache.reflush();
		return securityResource;
	}

	@Override
	public SecurityResource saveOrUpdate(SecurityResource entity)
	{
		SecurityResource securityResource = super.saveOrUpdate(entity);
		securityResourceCache.reflush();
		return securityResource;
	}

	@Override
	public SecurityResource update(SecurityResource entity)
	{
		SecurityResource securityResource = super.update(entity);
		securityResourceCache.reflush();
		return securityResource;
	}

	/**取得资源及对应的角色(同时加载角色)
	 * @return
	 * @author william
	 */
	public List<SecurityResource> getAllSecurityResourceAndRoles()
	{
		return securityResourceDAO.getAllSecurityResourceAndRoles();
	}
	
	/**根据资源分配情况查询资源，角色权限分配
	 * @param pager
	 * @author william
	 */
	public void getSecurityResourceByAllocated(Pager<SecurityResource> pager)
	{
		List<SecurityResource> securityResources = securityResourceDAO.getSecurityResourceByAllocated(pager.getParamCondition(), pager.getFirstResult(), pager.getPageSize());
		int count = securityResourceDAO.geCountOfSecurityResourceByAllocated(pager.getParamCondition());
		pager.setItems(securityResources);
		pager.setCountOfTotalItem(count);
	}
	
	@Override
	public ISecurityResourceDAO getGenericDAO()
	{
		return securityResourceDAO;
	}
}
