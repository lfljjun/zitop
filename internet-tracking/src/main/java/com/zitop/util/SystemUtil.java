package com.zitop.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.zitop.appsetting.ConfigNameConstants;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.infrastructure.util.SystemContext;

@SuppressWarnings("unchecked")
public class SystemUtil {
	private static Log log = LogFactory.getLog(SystemUtil.class);
	
	public static <T> T getBean(String beanName) {
		return (T) (SystemContext.getApplicationContext().getBean(beanName));

	}
	
	/**
	 * 获取用户名 
	 */
	public static String getSysUserName()
	{
		String username = null;
		try {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(userDetails != null)
			{
				username = userDetails.getUsername();
			}
		} catch (NullPointerException e) {
		}
		return username;
	}

	/**
	 *	输出JOSN 
	 */
	public static void writeJSONToResponse(String json) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/json;charset=utf-8");
			response.setContentLength(json.getBytes("utf-8").length);
			PrintWriter out = response.getWriter();
			out.print(json);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *	输出文本 
	 */
	public static void writeToResponse(String text) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/text; charset=utf-8");
			response.setContentLength(text.getBytes("utf-8").length);
			PrintWriter out = response.getWriter();
			out.print(text);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *	输出系统消息 
	 */
	public static void renderSysMessage(String message) {
		Struts2Utils.getRequest().setAttribute(ConfigNameConstants.SYSTEM_MESSAGE, message);
	}

	/**
	 * 输出文件 
	 */
	public static void renderFile(String outputFileName, String customerFileName) throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setHeader("Content-Disposition", String.format("attachment;filename=%s", URLEncoder.encode(customerFileName, "utf-8")));

		InputStream reader = new FileInputStream(outputFileName);
		OutputStream writer = response.getOutputStream();
		byte[] cbuf = new byte[1024];
		int len = 0;
		do {
			if (len > 0) {
				writer.write(cbuf, 0, len);
			}
			len = reader.read(cbuf);
		} while (len > 0);

		writer.flush();
		writer.close();
	}

	/**
	 * 将id字符串转换为数组 
	 */
	public static Long[] stringIdToLongArray(String idString) {
		if(StringUtils.isBlank(idString))
			return null;
		idString = idString.trim();
		
		String[] ids = idString.split("\\|");
		Long[] tmpIds = null;
		if(ids != null && ids.length > 0)
		{
			tmpIds = new Long[ids.length ];
			for(int i = 0; i< ids.length  ; i++)
			{
				try {
					tmpIds[i] = Long.valueOf(ids[i]);
				} catch (NumberFormatException e) {
					log.error(e);
				}
			}
		}
		return tmpIds;
	}

	/**
	 * 获取cookie值
	 * 
	 * @param name
	 * @return
	 */
	public static String getCookie(String name) {
		Cookie[] cs = Struts2Utils.getRequest().getCookies();
		for (Cookie c : cs) {
			if (c.getName().equals(name))
				return c.getValue();
//				return c.getValue().replaceAll("%7C", "|");
		}
		return null;
	}
	/**
	 * 添加当前选中的项目id参数
	 * @return
	 */
	public static void addParamCurrentProjectId(ParamCondition paramCondition){ 
		paramCondition.addParameter("projectId", getParamProjectId());
	}
    
	/**
	 * 从session中取得当前项目
	 * @return
	 */
	public static String getParamProjectId() {
		Object projectId = Struts2Utils.getSession().getAttribute("projectId");
		if(projectId == null)
			projectId = new Long(1);
		return projectId.toString();
	}
}
