package com.zitop.security.web.actions.admin;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.zitop.appsetting.ConfigNameConstants;
import com.zitop.evaluate.entity.SystemUser;
import com.zitop.evaluate.service.SystemUserService;
import com.zitop.util.EncryptUtil;
import com.zitop.util.SystemUtil;

@Results({
	@Result(name="home",type="redirect",location="/"),
	@Result(name="login",type="redirect",location="/"),
	@Result(name="user-list",type="redirect",location="/admin/security/user-list.action")
})
public class EntryAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 749755109235806535L;
	
	@Resource
	private SystemUserService systemUserService;
	
	//输入参数
	private String adminName;
	private String random;
	private String key;
	
	//页面展示
	private Map<String, Object> session;
	
	
	
	public String execute()
	{
		try {
			String encKey = EncryptUtil.encryptHMAC(adminName+random, "bxUa0WOMmFc43GycNcTP0MgqHomHXJeEc+82sQph+Go7t88gBiROgqvv/Mwh88Auqd0m93B1N87/\naPRXUuN8tB==");
			if(encKey.equals(key))
			{
				SystemUser user = systemUserService.getSystemUser(adminName);
//				SysContextUtil.setUser(user);
				// 将用户相关信息放到session中
				if(user != null)
				{
					session.put(ConfigNameConstants.CURRENT_SYSTEM_USER_ACCOUNT, user.getName());// 当前用户帐号
					session.put(ConfigNameConstants.CURRENT_SYSTEM_USER, user);// 当前帐号对象
					return "home";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "login";
	}
	
	public String syncUser()
	{
		SystemUserService systemUserService = SystemUtil.getBean("systemUserService");
		systemUserService.sync();
		//return "sync";
		return "user-list";
	}
	
	public void validate()
	{
		System.out.println(String.format("%s%s%s", adminName,random,key));
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
