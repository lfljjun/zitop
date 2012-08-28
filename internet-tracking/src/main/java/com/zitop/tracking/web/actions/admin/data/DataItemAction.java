package com.zitop.tracking.web.actions.admin.data;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.zitop.appsetting.ConfigNameConstants;
import com.zitop.appsetting.ParamaterValues;
import com.zitop.infrastructure.file.FileItemInfo;
import com.zitop.infrastructure.file.IFileSystem;
import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBaseManageAction;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;
import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.DataItem;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.tracking.entity.Project;
import com.zitop.tracking.entity.Term;
import com.zitop.tracking.service.DataItemService;
import com.zitop.tracking.service.ProjectService;
import com.zitop.tracking.service.TermService;
import com.zitop.util.SysContextUtil;
import com.zitop.util.SystemUtil;

@Result(name = "listTerm", location = "/admin/dimension/term-list.action?flag=true", type = "redirect")
public class DataItemAction extends ServiceBaseManageAction<DataItem, Long> implements ServletResponseAware,
		ServletRequestAware {
	private static final long serialVersionUID = 1996854557480321722L;
	private final static Log log = LogFactory.getLog(DataItemAction.class);
	@Resource
	private DataItemService dataItemService;
	private DataItem dataItem;
	private HttpServletResponse response;
	private List<DataItem> dataItems;
	private Long termId;
	private Long customerCategoryId;
	private Long indexItemId;
	private double dataValue;
	private Term thisTerm;
	private Map<String, List<CustomerCategory>> colMap;
	private Map<String, Map<IndexCategory, List<IndexItem>>> rowMap;
	private JSONObject rowJson;
	private int[] rowCount;
	private Map<String, Double> valueMap;
	private String[] graphTypeArray;
	private Map<String, String[]> paramsMap;
	@Resource
	private TermService termService;
	private String termName;
	private int statYear;
	private int statMonth;
	@Resource
	private ProjectService projectService;
	private Project thisProject;
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Resource
	private IFileSystem fileSystem;
	private File dataFile;

	@Override
	public IGenericService<DataItem, Long> getGenericService() {
		return dataItemService;
	}

	public DataItem getModel() {
		return dataItem;
	}

	public void prepare() throws Exception {
		settingThisProject();
		if (getRequestId() == null || getRequestId() == 0) {
			dataItem = new DataItem();
		} else {
			dataItem = dataItemService.getEntityById(getRequestId());
		}
	}
	/**
	 * 设置当前所选的项目
	 */
	private void settingThisProject() {
		String thisProjectId=SystemUtil.getParamProjectId();
		if(StringUtils.isNotBlank(thisProjectId)){
			thisProject=projectService.getEntityById(Long.parseLong(thisProjectId));
		}
	}

	public String download() {
		try {
			OutputStream os = response.getOutputStream();
			String fileName = new String((thisProject.getName()+"模板.xls").getBytes("GBK"), "iso-8859-1");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "inline; filename=" + fileName);
			getDownloadFile(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String uploadPage() {
		return "upload";
	}

	public String importData() {
		if (dataFile != null) {
			String folder = ParamaterValues.getString(ConfigNameConstants.UPLOAD_FILE_FOLD);
			FileItemInfo item = fileSystem.saveFile(dataFile, "xls", folder);
			String filePath = folder + item.getFileUrl() + "/" + item.getFileName();
			log.info("上传了文件：" + filePath);
			dataItemService.importExcelData(filePath, termName,statYear,statMonth);
		}
		return "upload";
	}

	/*
	 * 以期数修改，删除数据
	 */
	public String editByTerm(){
		if(termId!=null){
			thisTerm=termService.getEntityById(termId);
		if(SysContextUtil.hasAdminRole()){
			return getShowData();
		}
		else if(thisTerm.getCreator().equals(SystemUtil.getSysUserName())){
			return getShowData();
		}
		}
		return "norole";
	
	}
	
	/*
	 * 以期数修改，删除数据
	 */
	public String editByTermGrid(){
		if(SysContextUtil.hasAdminRole()){
			dataItems=dataItemService.getEntitiesByTermId(termId); 
			thisTerm=termService.getEntityById(termId);
			colMap=dataItemService.getColMap();
			rowMap=dataItemService.getRowMap();
			valueMap=dataItemService.getValueMap(dataItems);
			rowCount=dataItemService.getRowCount(rowMap);
			graphTypeArray=IndexCategory.GRAPH_TYPE_ARRAY;
			return "managegrid";
		}
		else{
			return "norole";
		}
	}
	private String getShowData() {
		dataItems=dataItemService.getEntitiesByTermId(termId); 
		colMap=dataItemService.getColMap();
		rowMap=dataItemService.getRowMap();
		valueMap=dataItemService.getValueMap(dataItems);
		rowCount=dataItemService.getRowCount(rowMap);
		graphTypeArray=IndexCategory.GRAPH_TYPE_ARRAY;
		return "manage";
	}


	public String editByGrid() {
		colMap = dataItemService.getColMap();
		rowJson = dataItemService.getIndexJson();
		return "mgrid";
	}

	public String gridJson() {
		dataItems = dataItemService.getEntitiesByTermId(termId);
		JSONArray array = dataItemService.getValueJson1(dataItems);
		JSONObject jsonx = new JSONObject();
		jsonx.put("total", array.size());
		jsonx.put("rows", array);
		Struts2Utils.renderJson(jsonx);
		return null;
	}

	public String updateData() throws IOException {
		boolean flag = dataItemService.updateData(termId, customerCategoryId, indexItemId, dataValue);
		response.setCharacterEncoding("UTF-8");
		if (flag) {
			response.getWriter().write("true");
		} else {
			response.getWriter().write("false");
		}
		return null;
	}

	public String updateByTerm() {
		dataItemService.updateByParamsMap(paramsMap);
		return "listTerm";
	}

	public String deleteByTerm(){
		if(termId!=null){
			thisTerm=termService.getEntityById(termId);
			if(SysContextUtil.hasAdminRole()){
				return deleteData();
			}
			else if(thisTerm.getCreator().equals(SystemUtil.getSysUserName())){
				return deleteData();
			}
		}
		return "norole";
	}

	private String deleteData() {
//		dataItems=dataItemService.getEntitiesByTermId(termId);
//		dataItemService.deleteEntities(dataItems);
		dataItemService.deleteAll(thisTerm.getId());
		termService.delete(thisTerm);
		return "listTerm";
	}
	
	public boolean getDownloadFile(OutputStream os) {
		return dataItemService.getDownloadFile(os);
	}

//	public void createRandData() {
//		//		生成随机测试数据
//		try {
//			DataItemUtil.createData(colMap, rowMap);
//		} catch (RowsExceededException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (WriteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	public File getDataFile() {
		return dataFile;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	public List<DataItem> getDataItems() {
		return dataItems;
	}

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public Map<String, List<CustomerCategory>> getColMap() {
		return colMap;
	}

	public Map<String, Map<IndexCategory, List<IndexItem>>> getRowMap() {
		return rowMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setServletRequest(HttpServletRequest request) {
		paramsMap = request.getParameterMap();
	}

	public Map<String, Double> getValueMap() {
		return valueMap;
	}

	public Term getThisTerm() {
		return thisTerm;
	}

	public int[] getRowCount() {
		return rowCount;
	}

	public String[] getGraphTypeArray() {
		return graphTypeArray;
	}

	public void setCustomerCategoryId(Long customerCategoryId) {
		this.customerCategoryId = customerCategoryId;
	}

	public void setIndexItemId(Long indexItemId) {
		this.indexItemId = indexItemId;
	}

	public void setDataValue(double dataValue) {
		this.dataValue = dataValue;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}
	
	public void setStatYear(int statYear) {
		this.statYear = statYear;
	}

	public void setStatMonth(int statMonth) {
		this.statMonth = statMonth;
	}

	public JSONObject getRowJson() {
		return rowJson;
	}
 

}
