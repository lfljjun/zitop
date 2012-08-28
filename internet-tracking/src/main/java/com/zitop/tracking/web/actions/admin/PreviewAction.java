package com.zitop.tracking.web.actions.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionSupport;
import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.tracking.service.DataItemService;



public class PreviewAction extends ActionSupport {
	private static final long serialVersionUID = -6647688392200853583L;
	@Resource
	private DataItemService dataItemService;
	private Map<String, List<CustomerCategory>> colMap;
	private Map<String, Map<IndexCategory, List<IndexItem>>> rowMap;
	private int[] rowCount;
	private String[] graphTypeArray;

	public String execute() {
		colMap=dataItemService.getColMap();
		rowMap=dataItemService.getRowMap();
		rowCount=dataItemService.getRowCount(rowMap);
		graphTypeArray=IndexCategory.GRAPH_TYPE_ARRAY;
		return "preview";
	}

	public Map<String, List<CustomerCategory>> getColMap() {
		return colMap;
	}

	public Map<String, Map<IndexCategory, List<IndexItem>>> getRowMap() {
		return rowMap;
	}

	public int[] getRowCount() {
		return rowCount;
	}

	public String[] getGraphTypeArray() {
		return graphTypeArray;
	}

}
