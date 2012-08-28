package com.zitop.security.service.extend;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

/**从数据库取得安全资源
 * @author william
 *
 */
public class DatabaseSecureResourceFilterInvocationDefinitionSource implements FilterInvocationSecurityMetadataSource
{
	private final static Log log = LogFactory.getLog(DatabaseSecureResourceFilterInvocationDefinitionSource.class);
	private UrlMatcher urlMatcher = new AntUrlPathMatcher();;
	private ISecurityResourceCache securityResourceCache;

	/**
	 * 根据url查询这个URL的权限配置
	 * @see org.springframework.security.access.SecurityMetadataSource#getAttributes(java.lang.Object)
	 */
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException
	{
		Map<String, Collection<ConfigAttribute>> resourceMap = securityResourceCache.getSecurityResources();
		// guess object is a URL.
		String url = ((FilterInvocation) object).getRequestUrl();
		log.debug("根据"+url+",取得URL的权限配置");
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext())
		{
			String resURL = ite.next();
			boolean result = urlMatcher.pathMatchesUrl( resURL,url);
			log.debug("对比"+url+" 与  "+resURL+" 结果："+result);
			if (result)
			{
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz)
	{
		return true;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes()
	{
		return null;
	}

	public ISecurityResourceCache getSecurityResourceCache()
	{
		return securityResourceCache;
	}

	public void setSecurityResourceCache(ISecurityResourceCache securityResourceCache)
	{
		this.securityResourceCache = securityResourceCache;
	}
	
}
