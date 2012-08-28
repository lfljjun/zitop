package com.zitop.appsetting;

import java.util.Map;

/**系统参数加载服务
 * @author william
 *
 */
public class AppParametersLoaderService
{
	public void loadParameter(String fileName)
	{
		ParamaterValues.loadProperties(fileName);
	}
	
	public Map<String,String> getParameter()
	{
		return ParamaterValues.getProperties();
	}
}
