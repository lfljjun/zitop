package com.zitop.security.web.actions.admin.appsetting;

import java.util.Map;

import com.zitop.appsetting.AppParametersLoaderService;

public class ShowParametersAction
{
	private Map<String,String> map;
	public String execute()
	{
		AppParametersLoaderService appParametersLoaderService = new AppParametersLoaderService();
		map = appParametersLoaderService.getParameter();
		return "success";
	}
	public Map<String, String> getMap()
	{
		return map;
	}
	public void setMap(Map<String, String> map)
	{
		this.map = map;
	}
}
