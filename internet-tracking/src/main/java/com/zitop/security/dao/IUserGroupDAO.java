package com.zitop.security.dao;

import java.util.List;

import com.zitop.security.entity.UserGroup;
import com.zitop.infrastructure.dao.IGenericDAO;
import com.zitop.infrastructure.util.ParamCondition;

public interface IUserGroupDAO extends IGenericDAO<UserGroup, Long>
{
	public List<UserGroup> getAllUserGroupNotDel();
	public List<UserGroup> getUserGroupByCondition(ParamCondition paramCondition,int firstResult,int pageSize);
	public int getCountOfUserGroupByCondition(ParamCondition paramCondition);
	public void markTag();
	public void delTag();
}
