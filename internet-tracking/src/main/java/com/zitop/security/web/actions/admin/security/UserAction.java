package com.zitop.security.web.actions.admin.security;

import javax.annotation.Resource;

import com.zitop.security.entity.User;
import com.zitop.security.service.UserService;
import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBaseManageAction;
import com.zitop.infrastructure.util.EncryptUtil;

public class UserAction extends ServiceBaseManageAction<User,Long>
{
	private static final long serialVersionUID = -4110154958096776400L;
	@Resource
	private UserService userService;
	private User user;
	private String repassword;
	@Override
	public IGenericService<User, Long> getGenericService()
	{
		return userService;
	}
	
	

	@Override
	public String insert()
	{
		user.setPassword(EncryptUtil.md5(repassword));
		return super.insert();
	}

	@Override
	public String update()
	{
	    if(repassword!=null&&repassword.trim().length()>0)
	    {
	        user.setPassword(EncryptUtil.md5(repassword));
	    }
		return super.update();
	}

	public User getModel()
	{
		return user;
	}

	public void prepare() throws Exception
	{
		if (getRequestId() == null || getRequestId() == 0)
		{
			user = new User();
		} else
		{
			user = userService.getEntityById(getRequestId());
		}
	}

    public String getRepassword()
    {
        return repassword;
    }



    public void setRepassword(String repassword)
    {
        this.repassword = repassword;
    }
	

}
