package com.zitop.evaluate.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zitop.appsetting.ParamaterValues;
import com.zitop.evaluate.dao.ISystemUserDAO;
import com.zitop.evaluate.entity.SystemUser;
import com.zitop.evaluate.vo.MkbSystemUserGroupVO;
import com.zitop.evaluate.vo.MkbSystemUserVO;
import com.zitop.security.dao.IUserDAO;
import com.zitop.security.dao.IUserGroupDAO;
import com.zitop.security.dao.IUserRoleDAO;
import com.zitop.security.entity.User;
import com.zitop.security.entity.UserGroup;
import com.zitop.util.SysContextUtil;

@Service
@Transactional(readOnly = false)
public class SystemUserService {
	
	private Log log = LogFactory.getLog(SystemUserService.class);
	@Resource
	private ISystemUserDAO systemUserDAO;
	@Resource
	private IUserDAO userDAO;
	@Resource
	private IUserRoleDAO userRoleDAO;
	@Resource
	private IUserGroupDAO userGroupDAO;
	
	public SystemUser getSystemUser(String userAccount) 
	{
		return systemUserDAO.getSystemUser(userAccount);
	}
	
	public void sync()
	{
		try {
			
			Class.forName(ParamaterValues.getString("jdbc.driverClassName"));
			Connection connection = DriverManager.getConnection(
					ParamaterValues.getString("jdbc.url"),
					ParamaterValues.getString("jdbc.username"),
					ParamaterValues.getString("jdbc.password")
					);
			
			//同步用户
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from system_users");
			while(rs.next())
			{
				MkbSystemUserVO userVO = getSystemUser(rs);
				User user = userDAO.getUserByUsername(userVO.getUsername());
				
				if(userVO.isDeleted() && user!=null)
				{
					log.info("用户同步：删除["+userVO.getUsername()+"]的账号和权限");
					//删除用户与角色的关联
					userRoleDAO.delUserRoleByUserId(user.getId());
					//删除用户
					userDAO.delete(user);
				}
				else if(user == null)
				{
					log.info("用户同步：增加用户["+userVO.getUsername()+"]");
					//新增用户
					user = new User();
					BeanUtils.copyProperties(userVO, user);
					userDAO.insert(user);
					
				}
				else if(user.getLastModifyTime() != null && userVO.getLastModifyTime().after(user.getLastModifyTime()))
				{
					log.info("用户同步：更新用户["+userVO.getUsername()+"]");
					//保存用户
					BeanUtils.copyProperties(userVO, user);
					userDAO.update(user);
				}
				else
				{
					//用户数据没有变化
				}
			}
			rs.close();
			
			//同步用户组
			userGroupDAO.markTag();
			rs = statement.executeQuery("select * from system_user_group");
			while(rs.next())
			{
				MkbSystemUserGroupVO userGroupVO = getSystemUserGroup(rs);
				UserGroup userGroup = userGroupDAO.getEntityById(userGroupVO.getGroupId());
				if(userGroup == null)
				{
					userGroup = new UserGroup();
				}
				BeanUtils.copyProperties(userGroupVO, userGroup);
				userGroup.setTagIt(true);
				userGroupDAO.saveOrUpdate(userGroup);
				SysContextUtil.addUserGroups(userGroup);
			}
			userGroupDAO.delTag();
			rs.close();
			
				
			
			/*批处理
			String[] systemUserSyncSQL = ParamaterValues.getString("systemUserSyncSQL").split(";");
			for(String sql : systemUserSyncSQL)
				statement.executeUpdate(sql);
			*/
			
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private MkbSystemUserVO getSystemUser(ResultSet rs) throws SQLException
	{
		MkbSystemUserVO userVO = new MkbSystemUserVO();
		
		userVO.setUsername(rs.getString("user_account"));
		userVO.setPassword(rs.getString("password"));
		userVO.setName(rs.getString("lastname"));
		userVO.setCompany(rs.getString("company"));
		userVO.setDepartment(rs.getString("department"));
		userVO.setEmail(rs.getString("email"));
		userVO.setHomePhone(rs.getString("home_phone"));
		userVO.setMobileTelephone(rs.getString("mobile_telephone"));
		userVO.setPhs(rs.getString("phs"));
		userVO.setDeleted(rs.getBoolean("deleted"));
		userVO.setAuth(rs.getInt("auth"));
		userVO.setCreateTime(rs.getDate("create_time"));
		userVO.setLastModifyTime(rs.getDate("last_modify_time"));
		userVO.setRemark(rs.getString("remark"));
		userVO.setGroupId(rs.getLong("group_id"));
		
		return userVO;
	}
	
	private MkbSystemUserGroupVO getSystemUserGroup(ResultSet rs) throws SQLException
	{
		MkbSystemUserGroupVO userGroupVO = new MkbSystemUserGroupVO();
		
		userGroupVO.setGroupId(rs.getLong("group_id"));
		userGroupVO.setGroupName(rs.getString("group_name"));
		userGroupVO.setGroupDes(rs.getString("group_des"));
		userGroupVO.setCreateTime(rs.getDate("create_time"));
		userGroupVO.setEnabled(rs.getBoolean("enabled"));
		userGroupVO.setParentId(rs.getLong("parent_id"));
		userGroupVO.setLeftNum(rs.getInt("left_num"));
		userGroupVO.setRightNum(rs.getInt("right_num"));
		
		return userGroupVO;
	}
}
