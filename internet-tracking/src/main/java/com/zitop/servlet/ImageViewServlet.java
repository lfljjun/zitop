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
 * 图片查看servlet
 * 
 * @author william
 * 
 */
public class ImageViewServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5128567273631480793L;
	private final static Log log = LogFactory.getLog(ImageViewServlet.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		try
		{
			String id = request.getParameter("id");
			String type = FileUtils.getExtension(id);
			response.setContentType("image/" + type);
			String uploadDir = ParamaterValues.getString(ConfigNameConstants.IMAGE_FILE_FOLD);
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
				log.error("未找到图片:"+filepath);
				return;
			}

		} catch (Exception e)
		{
			log.error("取得图片出错", e);
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

}
