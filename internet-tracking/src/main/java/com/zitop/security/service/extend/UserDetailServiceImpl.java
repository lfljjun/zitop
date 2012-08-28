package com.zitop.security.service.extend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zitop.security.entity.Role;
import com.zitop.security.entity.User;
import com.zitop.security.service.RoleService;
import com.zitop.security.service.UserService;

public class UserDetailServiceImpl implements UserDetailsService
{

	private UserService userService;
	private RoleService roleService;
	/**
	 * 获取用户Detail信息的回调函数
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
	{
		User user = userService.getUserByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException(username + " 不存在");

		//用户所有的角色
		Collection<GrantedAuthority> auths=obtainGrantedAuthorities(user);
		
		//spring 实现UserDetails接口的user对象
		org.springframework.security.core.userdetails.User userDetail 
		= new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), auths);
		
		return userDetail;
	}
	
	
	/**
	 * 获得用户所有角色的权限.
	 * @param user
	 * @return
	 * @author william
	 */
	private  Collection<GrantedAuthority> obtainGrantedAuthorities(User user) 
	{
		Collection<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
		List<Role> roles = roleService.getRoleByUserId(user.getId());
		for(Role role : roles)
		{
			GrantedAuthorityImpl auth1=new GrantedAuthorityImpl(role.getCode());
			auths.add(auth1);
		}
		return auths;
	}


	public UserService getUserService()
	{
		return userService;
	}


	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}


	public RoleService getRoleService()
	{
		return roleService;
	}


	public void setRoleService(RoleService roleService)
	{
		this.roleService = roleService;
	}
}
