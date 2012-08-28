package com.zitop.security.service.extend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.zitop.security.entity.Role;
import com.zitop.security.entity.SecurityResource;
import com.zitop.security.service.SecurityResourceService;

/**
 * 安全资源缓存-内存方式实现
 * 
 * @author william
 * 
 */
public class MemSecurityResourceCache implements ISecurityResourceCache,InitializingBean
{
	private final static Log log = LogFactory.getLog(MemSecurityResourceCache.class);

	private SecurityResourceService securityResourceService;
	private Map<String, Collection<ConfigAttribute>> securityResourceMap;//url与ConfigAttribute
	private Map<String, Collection<GrantedAuthority>> securityResourceToAuthorityeMap;//名称与Authority

	@Override
	public Map<String, Collection<ConfigAttribute>> getSecurityResources()
	{
		return securityResourceMap;
	}

	public Map<String, Collection<GrantedAuthority>> getSecurityResourceToAuthorityeMap()
    {
        return securityResourceToAuthorityeMap;
    }

    private synchronized void loadResourceDefine()
	{
		securityResourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		securityResourceToAuthorityeMap = new HashMap<String, Collection<GrantedAuthority>>();
		List<SecurityResource> securityResources = securityResourceService.getAllSecurityResourceAndRoles();
		log.info("系统共配置[" + securityResources.size() + "]项安全资源");
		for (SecurityResource resource : securityResources)
		{
			String url = resource.getValue();
			String resourcename = resource.getName();
			Collection<ConfigAttribute> atts = securityResourceMap.get(url);
			Collection<GrantedAuthority> authoritys = securityResourceToAuthorityeMap.get(resourcename);
			if (atts == null)
			{
				atts = new HashSet<ConfigAttribute>();
				securityResourceMap.put(url, atts);
			}
			if (authoritys == null)
			{
			    authoritys = new HashSet<GrantedAuthority>();
			    securityResourceToAuthorityeMap.put(resourcename, authoritys);
			}
			if(resource.getRoles() == null) continue;
			for (Role role : resource.getRoles())
			{
				ConfigAttribute ca = new SecurityConfig(role.getCode());
				atts.add(ca);
				authoritys.add(new GrantedAuthorityImpl(role.getCode()));
				
			}
		}
	}

	public SecurityResourceService getSecurityResourceService()
	{
		return securityResourceService;
	}

	public void setSecurityResourceService(SecurityResourceService securityResourceService)
	{
		this.securityResourceService = securityResourceService;
	}

	@Override
	public synchronized void reflush()
	{
		log.info("刷新安全资源缓存");
		loadResourceDefine();
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		loadResourceDefine();
	}

}
