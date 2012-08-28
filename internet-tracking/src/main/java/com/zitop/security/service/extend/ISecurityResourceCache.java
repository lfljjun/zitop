package com.zitop.security.service.extend;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

/**安全资源缓存接口
 * @author william
 *
 */
public interface ISecurityResourceCache
{
	/**取得系统中的安全资源
	 * @return
	 * @author william
	 */
	public Map<String, Collection<ConfigAttribute>> getSecurityResources();
	
	/**取得系统中资料与角色的对应关系
	 * @return
	 */
	public Map<String, Collection<GrantedAuthority>> getSecurityResourceToAuthorityeMap();
	/**
	 * 刷新缓存
	 * @author william
	 */
	public void reflush();
}
