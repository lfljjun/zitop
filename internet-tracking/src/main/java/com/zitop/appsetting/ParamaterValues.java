package com.zitop.appsetting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**系统配置的值参数
 * @author william
 *
 */
public class ParamaterValues
{
	private final static Log log = LogFactory.getLog(ParamaterValues.class);
	//参数值map
	private static Map<String,Object> valueMap= new HashMap<String,Object>();
	private static Properties prop = new Properties();
	
	
	/**根据配置名称取得相应的boolean值。
	 * 对应关系如下：
	 * on-true
	 * off=false
	 * true=true
	 * false=false
	 * 1=true
	 * 0=false
	 * 其它=false
	 * @param name
	 * @author william
	 */
	public static Boolean getBoolean(String name)
	{
		Object v = valueMap.get(name);
		if(v==null)
		{
			String s = getValueFromProp(name);
			if(s==null)
			{
				log.warn("在配置文件中未找到配置项[boolean]:"+name+",将返回null值");
				return null;
			}
			s=s.trim();
			if("on".equalsIgnoreCase(s)||"true".equalsIgnoreCase(s)||"1".equalsIgnoreCase(s))
			{
				v=true;
			}
			else
			{
				v=false;
			}
			valueMap.put(name, v);
		}
		return (Boolean)v;
	}
	
	
	/**根据配置名称取得相应的int值。
	 * @param name
	 * @return
	 * @author william
	 */
	public static Integer getInteger(String name)
	{
		Object v = valueMap.get(name);
		if(v==null)
		{
			String s = getValueFromProp(name);
			if(s==null)
			{
				log.warn("在配置文件中未找到配置项[Integer]:"+name+",将返回null值");
				return null;
			}
			v = Integer.parseInt(s.trim());
			valueMap.put(name, v);
		}
		return (Integer)v;
	}
	
	/**根据配置名称取得相应的String。
	 * @param name
	 * @return
	 * @author william
	 */
	public static String getString(String name)
	{
		Object v = valueMap.get(name);
		if(v==null)
		{
			String s = getValueFromProp(name);
			if(s==null)
			{
				log.warn("在配置文件中未找到配置项[String]:"+name+",将返回null值");
				return null;
			}
			v=s.trim();
			valueMap.put(name, v);
		}
		return (String)v;
	}
	
	/**根据配置名称取得相应的List,List内的值为string。
	 * 值使用豆号(,)或者分号(;)来分隔
	 * @param name
	 * @return
	 * @author william
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getStringOfList(String name)
	{
		Object v = valueMap.get(name);
		if(v==null)
		{
			String s = getValueFromProp(name);
			if(s==null)
			{
				log.warn("在配置文件中未找到配置项[String]:"+name+",将返回null值");
				return null;
			}
			String[] values = s.split(",|;");
			List<String> list = new ArrayList<String>();
			for(String item : values)
			{
				if(item==null)continue;
				list.add(item.trim());
			}
			v=list;
			valueMap.put(name, v);
		}
		return (List<String>)v;
	}
	
	
	
	private static String getValueFromProp(String name)
	{
		String value = prop.getProperty(name);
		return value;
	}
	
	
	protected static void loadProperties(String fileName)
	{
		try
		{
			Properties p = new Properties();
			p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
			synchronized (ParamaterValues.class)
			{
				prop = p;
				valueMap= new HashMap<String,Object>();
			}
		} catch (IOException e)
		{
			log.error("加载配置文件["+fileName+"]出错",e);
		}
	}
	
	protected static  Map<String,String>  getProperties()
	{
		Map<String,String> map = new HashMap<String,String> ();
		for(Object key:prop.keySet())
		{
			map.put(key.toString(), prop.getProperty(key.toString()));
		}
		return map;
	}

}
