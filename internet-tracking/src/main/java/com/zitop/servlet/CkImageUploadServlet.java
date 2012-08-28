package com.zitop.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zitop.appsetting.ConfigNameConstants;
import com.zitop.appsetting.ParamaterValues;
import com.zitop.infrastructure.util.DateUtil;

/**CKEditor图片上传servelt
 * @author william
 *
 */
public class CkImageUploadServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5128567273631480793L;
	private final static Log log = LogFactory.getLog(CkImageUploadServlet.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		try
		{
			// 判断提交的请求是否包含文件
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(!isMultipart)
			{
				responseError(out,"无上传文件");
				return;
			}
			// 设置上传的保存路径
			String uploadDir = ParamaterValues.getString(ConfigNameConstants.IMAGE_FILE_FOLD);
			if (uploadDir == null)
			{
				log.error("无法访问存储目录");
				responseError(out,"无法访问存储目录");
				return;
			}
			File fUploadDir = new File(uploadDir);
			if (!fUploadDir.exists())
			{
				if (!fUploadDir.mkdirs())
				{
					log.error("无法创建存储目录!");
					responseError(out,"无法创建存储目录");
					return;
				}
			}
			// 按日期划分上传目录
			Date date = new Date();
			String dsr = DateUtil.formatDateByPattern(date, "yyyy/MM/dd");
			File dirfile = new File(fUploadDir, dsr);
			if (!dirfile.exists())
			{
				if (!dirfile.mkdirs())
				{
					log.error("无法创建存储目录!");
					responseError(out,"无法创建存储目录");
					return;
				}
			}

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置内存中最大存储5M，超过则保存到临时文件夹下
			factory.setSizeThreshold(1024 * 1024 * 5);
			// 设置临时文件夹地址
			factory.setRepository(new File("/filetmp"));
			ServletFileUpload upload = new ServletFileUpload(factory);
			int maxSize = ParamaterValues.getInteger(ConfigNameConstants.IMAGE_MAX_SIZE);
			// 设置最大上传的文件大小 3M
			upload.setSizeMax(1024 * 1024 * maxSize);
			String fileName = "";
			
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext())
			{
				FileItem item = iter.next();
				if (item.isFormField())
				{
					System.out.println("不是上传的文件");
				} else
				{
					String[] strs = item.getName().split("\\.");
					String fileType=strs[strs.length - 1].toLowerCase();
					List<String> allowTypes = ParamaterValues.getStringOfList(ConfigNameConstants.IMAGE_ALLOWED);
					if(allowTypes.contains(fileType))
					{
						fileName = UUID.randomUUID().toString() + "." +fileType ;
						File nFile = new File(dirfile, fileName);
						item.write(nFile);
					}
					else
					{
						responseError(out,"图片格式不正确");
					}
				}
			}
			
			String fileUrl = ParamaterValues.getString(ConfigNameConstants.IMAGE_VIEW_URL) + dsr + "/" + fileName;
			// CKEditorFuncNum就是在提交上传文件的同时传递到后台的request内容，表明应该插入到编辑器中的位置
			String callback = request.getParameter("CKEditorFuncNum");
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileUrl + "',''" + ")");
			out.println("</script>");
			out.flush();
			out.close();
		} 
		catch (Exception e)
		{
			log.error(e);
			if("org.apache.commons.fileupload.FileUploadBase$SizeLimitExceededException".equalsIgnoreCase(e.getClass().getName()))
			{
				responseError(out,"上传文件超出范围，最大不能超过:"+ParamaterValues.getInteger(ConfigNameConstants.IMAGE_MAX_SIZE)+" M");
			}
			else
			{
				responseError(out,"服务暂不可用");
			}
		}
	}
	
	private void responseError(PrintWriter out,String errorMsg)
	{
		out.println("<script type=\"text/javascript\">");
		out.println("alert(\"" + errorMsg+ "\");");
		out.println("window.parent.CKEDITOR.tools.callFunction(1)");
		out.println("</script>");
		out.flush();
		out.close();
	}
}
