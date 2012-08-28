package com.zitop.tracking.web.actions.main;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;
import com.zitop.tracking.service.CustomerCategoryService;
import com.zitop.tracking.service.DataItemService;
import com.zitop.tracking.service.IndexCategoryService;
import com.zitop.tracking.service.IndexItemService;
import com.zitop.tracking.service.ProjectService;
import com.zitop.tracking.service.ShowService;
import com.zitop.tracking.service.TermService;
import com.zitop.util.SystemUtil;

@Results({
	@Result(name = "table", location = "/main/show-table.jsp"),
	@Result(name = "tablegrid", location = "/main/show-table-grid.jsp")
})
public class TableAction extends ActionSupport {
	private static final long serialVersionUID = 7428301994866708188L;
	@Resource
	private ShowService showService;
	@Resource
	private ProjectService projectService;
	@Resource
	private CustomerCategoryService customerCategoryService;
	@Resource
	private DataItemService dataItemService;
	@Resource
	private IndexCategoryService indexCategoryService;
	@Resource
	private IndexItemService indexItemService;
	@Resource
	private TermService termService;

	public String showTable() {
		Struts2Utils.getRequest().setAttribute("termId", Struts2Utils.getParameter("termId"));
		
		Struts2Utils.getRequest().setAttribute("zhibiaos", SystemUtil.getCookie("zhibiaos").replaceAll("%7C", "|"));
		Struts2Utils.getRequest().setAttribute("kehus", SystemUtil.getCookie("kehus").replaceAll("%7C", "|"));
		Struts2Utils.getRequest().setAttribute("qishus", SystemUtil.getCookie("qishus").replaceAll("%7C", "|"));
		
		showService.showTable();
		return "table";
	}
	
	public String showTableGrid() {
		Struts2Utils.getRequest().setAttribute("termId", Struts2Utils.getParameter("termId"));
		
		Struts2Utils.getRequest().setAttribute("zhibiaos", SystemUtil.getCookie("zhibiaos").replaceAll("%7C", "|"));
		Struts2Utils.getRequest().setAttribute("kehus", SystemUtil.getCookie("kehus").replaceAll("%7C", "|"));
		Struts2Utils.getRequest().setAttribute("qishus", SystemUtil.getCookie("qishus").replaceAll("%7C", "|"));
		
		showService.showTable();
		return "tablegrid";
	}

	public String exportExcel() {
		Struts2Utils.getRequest().setAttribute("zhibiaos", SystemUtil.getCookie("zhibiaos").replaceAll("%7C", "|"));
		Struts2Utils.getRequest().setAttribute("kehus", SystemUtil.getCookie("kehus").replaceAll("%7C", "|"));
		Struts2Utils.getRequest().setAttribute("qishus", SystemUtil.getCookie("qishus").replaceAll("%7C", "|"));
		
		try {
			showService.exportTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
