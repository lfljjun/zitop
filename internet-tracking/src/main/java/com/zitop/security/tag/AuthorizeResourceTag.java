package com.zitop.security.tag;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zitop.appsetting.ConfigNameConstants;
import com.zitop.appsetting.ParamaterValues;
import com.zitop.security.service.extend.ISecurityResourceCache;

public class AuthorizeResourceTag extends TagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 4094128206088041125L;
    
    private String ifAllGranted = "";// 具有全部需要校验的权限
    private String ifAnyGranted = "";
    private String ifNotGranted = "";

    public int doStartTag() throws JspException
    {
        if (((null == ifAllGranted) || "".equals(ifAllGranted)) && ((null == ifAnyGranted) || "".equals(ifAnyGranted)) && ((null == ifNotGranted) || "".equals(ifNotGranted))  )
        {
            return Tag.SKIP_BODY;
        }

        HttpSession session = pageContext.getSession();
        
        if ((null != ifNotGranted) && !"".equals(ifNotGranted.trim())) {
            String key = "NOT_"+ifNotGranted;
            Object flag = session.getAttribute(key);
            if(flag == null)
            {
                //判断权限
                flag = checkIfNotGrantedAuthorities();
                //将权限放到session中
                if(ParamaterValues.getBoolean(ConfigNameConstants.CACHED_AUTHORITY_IN_SESSION))
                {
                    session.setAttribute(key, flag);
                }
            }
            
            boolean bflag = (Boolean) flag;
            if(!bflag)
            {
                return Tag.SKIP_BODY;
            }
        }
        
        //判断ifAllGranted情况
        if ((null != ifAllGranted) && !"".equals(ifAllGranted.trim())) {
            String key = "All_"+ifAllGranted;
            Object flag = session.getAttribute(key);
            if(flag == null)
            {
                //判断权限
                flag = checkIfAllGrantedAuthorities();
                //将权限放到session中
                if(ParamaterValues.getBoolean(ConfigNameConstants.CACHED_AUTHORITY_IN_SESSION))
                {
                    session.setAttribute(key, flag);
                }
            }
            
            boolean bflag = (Boolean) flag;
            
            if(!bflag)
            {
                return Tag.SKIP_BODY; 
            }   
        }

        if ((null != ifAnyGranted) && !"".equals(ifAnyGranted.trim())) {
            String key = "ANY_"+ifAnyGranted;
            Object flag = session.getAttribute(key);
            if(flag == null)
            {
                //判断权限
                flag = checkIfAnyGrantedAuthorities();
                //将权限放到session中
                if(ParamaterValues.getBoolean(ConfigNameConstants.CACHED_AUTHORITY_IN_SESSION))
                {
                    session.setAttribute(key, flag);
                }
            }
            
            boolean bflag = (Boolean) flag;
            if(!bflag)
            {
                return Tag.SKIP_BODY;
            }
        }
        
        
        return Tag.EVAL_BODY_INCLUDE;
    }
    
    private boolean checkIfAllGrantedAuthorities()
    {
        ServletContext servletContext = pageContext.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        ISecurityResourceCache securityResourceCache = (ISecurityResourceCache) ctx.getBean("securityResourceCache");
        //从cache中取得安全资源与角色(权限)的对象map
        Map<String, Collection<GrantedAuthority>> securityResourceToAuthorityeMap = securityResourceCache.getSecurityResourceToAuthorityeMap();
        Collection<GrantedAuthority> principalAuthorities =  getPrincipalAuthorities();//取得个人的权限
        Set<String> resourcesSet = getResourcesSet(ifAllGranted);//将ifAllGranted 转换成安全资源set
        
        labelresource:
        for(String resourceName : resourcesSet)
        {
            Collection<GrantedAuthority> authoritys = securityResourceToAuthorityeMap.get(resourceName);
            if(authoritys==null) 
            {
                return false;
            }
            for(GrantedAuthority g : authoritys)
            {
                if(principalAuthorities.contains(g))
                {
                    continue labelresource;
                }
            }
            return false;
        }
        return true;
    }
    
    //是否不包涵相关权限，只要有包涵，则返回false
    private boolean checkIfNotGrantedAuthorities()
    {
        ServletContext servletContext = pageContext.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        ISecurityResourceCache securityResourceCache = (ISecurityResourceCache) ctx.getBean("securityResourceCache");
        //从cache中取得安全资源与角色(权限)的对象map
        Map<String, Collection<GrantedAuthority>> securityResourceToAuthorityeMap = securityResourceCache.getSecurityResourceToAuthorityeMap();
        Collection<GrantedAuthority> principalAuthorities =  getPrincipalAuthorities();//取得个人的权限
        Set<String> resourcesSet = getResourcesSet(ifNotGranted);//将ifAllGranted 转换成安全资源set
        
        labelresource:
        for(String resourceName : resourcesSet)
        {
            Collection<GrantedAuthority> authoritys = securityResourceToAuthorityeMap.get(resourceName);
            if(authoritys==null) 
            {
                continue labelresource;
            }
            for(GrantedAuthority g : authoritys)
            {
                if(principalAuthorities.contains(g))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
  //是否包涵相关其中之一权限，则返回false
    private boolean checkIfAnyGrantedAuthorities()
    {
        ServletContext servletContext = pageContext.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        ISecurityResourceCache securityResourceCache = (ISecurityResourceCache) ctx.getBean("securityResourceCache");
        //从cache中取得安全资源与角色(权限)的对象map
        Map<String, Collection<GrantedAuthority>> securityResourceToAuthorityeMap = securityResourceCache.getSecurityResourceToAuthorityeMap();
        Collection<GrantedAuthority> principalAuthorities =  getPrincipalAuthorities();//取得个人的权限
        Set<String> resourcesSet = getResourcesSet(ifAnyGranted);//将ifAllGranted 转换成安全资源set
        
        labelresource:
        for(String resourceName : resourcesSet)
        {
            Collection<GrantedAuthority> authoritys = securityResourceToAuthorityeMap.get(resourceName);
            if(authoritys==null) 
            {
                continue labelresource;
            }
            for(GrantedAuthority g : authoritys)
            {
                if(principalAuthorities.contains(g))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private Set<String> getResourcesSet(String resourceString)
    {
        String[] resources = StringUtils.tokenizeToStringArray(resourceString,",");

        Set<String> resourcesSet = new HashSet<String>();

        for (int i = 0; i < resources.length; i++)
        {
            resourcesSet.add(resources[i]);
        }
        return resourcesSet;
        
    }

    private Collection<GrantedAuthority> getPrincipalAuthorities()
    {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

        if (null == currentUser)
        {
            return Collections.emptyList();
        }
        return currentUser.getAuthorities();
    }

    public String getIfAllGranted()
    {
        return ifAllGranted;
    }

    public void setIfAllGranted(String ifAllGranted)
    {
        this.ifAllGranted = ifAllGranted;
    }

    public String getIfAnyGranted()
    {
        return ifAnyGranted;
    }

    public void setIfAnyGranted(String ifAnyGranted)
    {
        this.ifAnyGranted = ifAnyGranted;
    }

    public String getIfNotGranted()
    {
        return ifNotGranted;
    }

    public void setIfNotGranted(String ifNotGranted)
    {
        this.ifNotGranted = ifNotGranted;
    }
}
