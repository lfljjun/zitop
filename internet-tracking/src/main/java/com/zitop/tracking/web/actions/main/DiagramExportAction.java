package com.zitop.tracking.web.actions.main;

import java.io.StringReader;

import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 图形导出
 */
public class DiagramExportAction extends ActionSupport implements ServletResponseAware{

	private static final long serialVersionUID = 2142959021681360942L;
	
	private String type;
	private String width;
	private String svg;
	private HttpServletResponse response;
	
	public String execute()
	{
		response.addHeader("Content-Disposition", "attachment; filename=chart.jpg");
        response.addHeader("Content-Type", type);
		Transcoder trans = new JPEGTranscoder();
		try {
			TranscoderInput input = new TranscoderInput(new StringReader(svg));
	        TranscoderOutput output = new TranscoderOutput(response.getOutputStream());
	        trans.transcode(input, output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getType() {
		return type;
	}

	public String getWidth() {
		return width;
	}

	public String getSvg() {
		return svg;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setSvg(String svg) {
		this.svg = svg;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = (HttpServletResponse)response;
	}

}
