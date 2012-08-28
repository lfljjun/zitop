package com.zitop.appsetting;

import com.zitop.security.service.UserGroupService;
import com.zitop.util.SysContextUtil;

public class ParamegerInitBean {
	private String fileName = "";
	private UserGroupService userGroupService;

	public void init() {
		AppParametersLoaderService appParametersLoaderService = new AppParametersLoaderService();
		appParametersLoaderService.loadParameter(fileName);

		SysContextUtil.putUserGroupMap(userGroupService.getUserGroupMap());
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public UserGroupService getUserGroupService() {
		return userGroupService;
	}

	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

}
