package com.zitop.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zitop.appsetting.ConfigNameConstants;
import com.zitop.appsetting.ParamaterValues;
import com.zitop.infrastructure.file.FileUtils;

/**
 * 文件下载servlet
 * 
 * @author william
 * 
 */
public class FileDownServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5128567273631480793L;
	private final static Log log = LogFactory.getLog(FileDownServlet.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		try
		{
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String type = FileUtils.getExtension(id);
			response.setContentType("APPLICATION/OCTET-STREAM");

			String filename = "";
			if (name != null && name.trim().length() > 0)
			{
				String ftype = FileUtils.getExtension(name);
				filename = ftype.equalsIgnoreCase(type)?name:(name+"."+type);
			} else
			{
				
				filename = System.currentTimeMillis() + "." + type;
			}
			response.setHeader("Content-Disposition",
			"attachment;filename=\"" + filename + "\"");
			String uploadDir = ParamaterValues.getString(ConfigNameConstants.UPLOAD_FILE_FOLD);
			String filepath = uploadDir.lastIndexOf("/") == 0 ? uploadDir + id : uploadDir + "/" + id;

			File file = new File(filepath);
			if (file.exists())
			{
				FileInputStream inputStream = new FileInputStream(file);
				OutputStream outputStream = response.getOutputStream();
				byte[] readBytes = new byte[1024];
				int readLength = inputStream.read(readBytes);
				while (readLength != -1)// 读取数据到文件输出流
				{
					outputStream.write(readBytes, 0, readLength);
					outputStream.flush();
					readLength = inputStream.read(readBytes);
				}
				inputStream.close();
				outputStream.close();
			} else
			{
				log.error("未找到文件:" + filepath);
				return;
			}

		} catch (Exception e)
		{
			log.error("取得文件出错", e);
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

}
