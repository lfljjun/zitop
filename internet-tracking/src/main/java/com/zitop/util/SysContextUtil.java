package com.zitop.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;

import com.zitop.appsetting.ConfigNameConstants;
import com.zitop.appsetting.ParamaterValues;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;
import com.zitop.security.entity.UserGroup;

/**
 * 系统环境帮助类，方便取得系统运行的静态属性
 * 
 * @author wuwenyi
 * 
 */
public class SysContextUtil {
	public static final ThreadLocal<User> users = new ThreadLocal<User>();
	public static final ThreadLocal<String> ips = new ThreadLocal<String>();
	public static Map<Long, UserGroup> userGroups = new HashMap<Long, UserGroup>();

	/**
	 * 取到当前系统用户的角色组
	 * @return
	 */
	public static List<String> getCurrentRoles() {
		List<String> roles=new ArrayList<String>();
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) Struts2Utils.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) securityContextImpl.getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			roles.add(grantedAuthority.getAuthority());
		}
		return roles;
	}
	/**
	 * 取到系统配置的管理员的角色
	 * @return
	 */
	public static String[] getAdminRoles() {
		String str=ParamaterValues.getString(ConfigNameConstants.ADMIN_ROLES);
		String[] adminRoles=str.split(",");
		return adminRoles;
	}
	/**
	 * 判断当前 用户是否有管理员角色
	 * @return
	 */
	public static boolean hasAdminRole(){
		List<String> roles=getCurrentRoles();
		String[] adminRoles=getAdminRoles();
		if(roles.size()>0&&adminRoles.length>0){
			for(String role:roles){
				for(String str:adminRoles){
					if(str.equals(role.trim())){
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * 取得当前系统用户
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		User user = users.get();
		return user;
	}

	/**
	 * 取得当前系统用户的帐号
	 * 
	 * @return
	 */
	public static String getCurrentUserAccount() {
		try {
			return getCurrentUser().getUsername();
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static void setIP(String ip) {
		ips.set(ip);
	}

	public static void setUser(User user) {
		users.set(user);
	}

	public static User getUser() {
		return users.get();
	}

	/**
	 * 清理系统用户登录数据
	 * 
	 */
	public static void clearSysUser() {
		users.set(null);
		ips.set(null);
	}

	public static String getIp() {
		return ips.get();
	}

	public static UserGroup getUserGroup(Long id) {
		return userGroups.get(id);
	}

	public static String getUserGroupName(Long id) {
		UserGroup userGroup = userGroups.get(id);
		if (userGroup != null) {
			return userGroup.getGroupName();
		} else {
			return null;
		}
	}

	public static void addUserGroups(UserGroup userGroup) {
		userGroups.put(userGroup.getGroupId(), userGroup);
	}

	public static void putUserGroupMap(Map<Long, UserGroup> userGroupMap) {
		userGroups.putAll(userGroupMap);
	}

}
