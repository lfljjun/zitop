package com.zitop.security.web.actions.admin.appsetting;

import com.zitop.appsetting.AppParametersLoaderService;

public class LoadParametersAction
{
	private String fileName="system-parameters.properties";
	public String execute()
	{
		AppParametersLoaderService appParametersLoaderService = new AppParametersLoaderService();
		appParametersLoaderService.loadParameter(fileName);
		return "success";
	}
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
}
