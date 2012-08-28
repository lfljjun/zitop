package com.zitop.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zitop.infrastructure.service.impl.AbstractService;
import com.zitop.infrastructure.util.Pager;
import com.zitop.security.dao.IUserGroupDAO;
import com.zitop.security.entity.UserGroup;

@Service
public class UserGroupService extends AbstractService<UserGroup, Long, IUserGroupDAO>
{
	@Resource
	private IUserGroupDAO userGroupDAO;

	@Override
	public IUserGroupDAO getGenericDAO()
	{
		return userGroupDAO;
	}
	
	public void getUserGroupByCondition(Pager<UserGroup> pager)
	{
		pager.setItems(userGroupDAO.getUserGroupByCondition(pager.getParamCondition(), pager.getFirstResult(), pager.getPageSize()));
		pager.setCountOfTotalItem(userGroupDAO.getCountOfUserGroupByCondition(pager.getParamCondition()));
	}
	
	public List<UserGroup> getAllUserGroup()
	{
		return userGroupDAO.getAllUserGroupNotDel();
	}
	
	public Map<Long,UserGroup> getUserGroupMap()
	{
		Map<Long,UserGroup> map = new HashMap<Long,UserGroup>();
		
		List<UserGroup> list = userGroupDAO.getAllEntity();
		for(UserGroup userGroup : list)
		{
			map.put(userGroup.getGroupId(), userGroup);
		}
		
		return map;
	}
}
