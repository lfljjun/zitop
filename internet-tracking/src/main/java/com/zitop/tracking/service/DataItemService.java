package com.zitop.tracking.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zitop.infrastructure.service.impl.AbstractService;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.tracking.dao.ICustomerCategoryDAO;
import com.zitop.tracking.dao.IDataItemDAO;
import com.zitop.tracking.dao.IIndexCategoryDAO;
import com.zitop.tracking.dao.IIndexItemDAO;
import com.zitop.tracking.dao.ITermDAO;
import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.DataItem;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.tracking.entity.Project;
import com.zitop.tracking.entity.Term;
import com.zitop.util.DataItemUtil;
import com.zitop.util.IndexCategoryComparator;
import com.zitop.util.SysCodeConst;
import com.zitop.util.SystemUtil;

@Service
public class DataItemService extends AbstractService<DataItem, Long, IDataItemDAO>
{
	private final static Log log = LogFactory.getLog(DataItemService.class);
	@Resource
	private IDataItemDAO dataItemDAO;
	@Resource
	private IIndexItemDAO indexItemDAO;
	@Resource
	private ICustomerCategoryDAO customerCategoryDAO;
	@Resource
	private IIndexCategoryDAO indexCategoryDAO;
	@Resource 
	private ITermDAO termDAO;


	@Override
	public IDataItemDAO getGenericDAO()
	{
		return dataItemDAO;
	}
	
	/**
	 * 根据输入的参数查询数据
	 * @param paramCondition
	 */
	public List<DataItem> getDataItemByCondition(ParamCondition paramCondition)
	{
		return dataItemDAO.getEntitiesByParamCondition(paramCondition, -1, -1);
	}
	
	public boolean getDownloadFile(OutputStream os) {
		Map<String, List<CustomerCategory>> colMap = getColMap();
		Map<String, Map<IndexCategory, List<IndexItem>>> rowMap = getRowMap();
		return DataItemUtil.write(os, colMap, rowMap);
	}

	public Map<String, List<CustomerCategory>> getColMap() {
		//横表头输出数据
		Map<String,List<CustomerCategory>> colMap=new LinkedHashMap<String, List<CustomerCategory>>();
		CustomerCategory category=new CustomerCategory();
		category.setId((long) 0);
		category.setProject(newProject());
		List<CustomerCategory> customerCategories=customerCategoryDAO.getSubEntities(category);
		for(CustomerCategory customerCategory:customerCategories){
			List<CustomerCategory> list=customerCategoryDAO.getSubEntities(customerCategory);
			if(list.size()==0){
				CustomerCategory cc=new CustomerCategory();
				cc.setName("");
				list.add(cc);
			}
			colMap.put(customerCategory.getName(), list);
		}
		return colMap;
	}

	private Project newProject() {
		Project project=new Project();
		String projectId=Struts2Utils.getSession().getAttribute("projectId").toString();
		project.setId(Long.parseLong(projectId));
		return project;
	}

	public Map<String, Map<IndexCategory, List<IndexItem>>> getRowMap() {
		//列标题输出数据
		Map<String,Map<IndexCategory,List<IndexItem>>> rowMap=new LinkedHashMap<String,Map<IndexCategory,List<IndexItem>>>();
		IndexCategory parent=new IndexCategory();
		parent.setId((long)0);
		parent.setProject(newProject());
		List<IndexCategory> indexCategories=indexCategoryDAO.getSubEntrities(parent);
		for(IndexCategory indexCategory:indexCategories){
			Map<IndexCategory,List<IndexItem>> map=new TreeMap<IndexCategory,List<IndexItem>>(new IndexCategoryComparator());
			List<IndexCategory> list=indexCategoryDAO.getSubEntrities(indexCategory);
			if(list.size()==0){
				IndexCategory indexc=new IndexCategory();
				indexc.setName("");
				IndexItem item=new IndexItem();
				item.setName("");
				List<IndexItem> items=new ArrayList<IndexItem>();
				items.add(item);
				map.put(indexc, items);
			}else{
				for(IndexCategory ic:list){
					List<IndexItem> indexItems=indexItemDAO.getEntitiesByCategory(ic);
					if(indexItems.size()==0){
						IndexItem item=new IndexItem();
						item.setName("");
						indexItems.add(item);
					}
					map.put(ic, indexItems);
				}
			}
			rowMap.put(indexCategory.getName(),map);
		}
		return rowMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean importExcelData(String filePath, String termName, int statYear, int statMonth) {
		Set<String> uploadInfo=new LinkedHashSet<String>();
		// 验证文件
		File file = new File(filePath);
		if (!this.valid(file)) {
			uploadInfo.add("文件不符合格式");
			Struts2Utils.getRequest().setAttribute("uploadInfo", uploadInfo);
			file.delete();
			return false;
		}
		List<List> result = null;
		try {
			result = DataItemUtil.read(filePath);
		} catch (BiffException e) {
			uploadInfo.add(e.getMessage());
			Struts2Utils.getRequest().setAttribute("uploadInfo", uploadInfo);
		} catch (IOException e) {
			uploadInfo.add(e.getMessage());
			Struts2Utils.getRequest().setAttribute("uploadInfo", uploadInfo);
		}
		if(result!=null){
			Term term=new Term();
			term.setName(termName);
			term.setCreator(SystemUtil.getSysUserName());
			term.setStatYear(statYear);
			term.setStatMonth(statMonth);
			term.setProject(newProject());
			Term insertTerm=termDAO.insert(term);
			List<DataItem> dataItems=new ArrayList<DataItem>();
			int right=1;
			int up=1;
//			for (List<String> cell : result) {
//				for (int i = 0; i < cell.size(); i++) {
//					System.out.print(cell.get(i) + "\t");
//				}
//				System.out.println("\r\n");
//			}
			List<String> customerIdList=result.get(0);
			for(int i=4+right;i<customerIdList.size();i++){
				String str=customerIdList.get(i);
				if(StringUtils.isNotEmpty(str)&&!"".equals(str)&&StringUtils.isNumeric(str)){
				Long customerId=Long.parseLong(str);
				for(int j=2+up;j<result.size();j++){
					String str2=(String) result.get(j).get(0);
					if(StringUtils.isNotEmpty(str2)&&!"".equals(str2)&&StringUtils.isNumeric(str2)){
						Long indexItemId=Long.parseLong(str2);
						String value=(String) result.get(j).get(i);
						if(StringUtils.isNotEmpty(value)&&!"".equals(value)&&StringUtils.isNumeric(value)){
							DataItem dataItem=new DataItem();
							CustomerCategory customerCategory=customerCategoryDAO.getEntityById(customerId);
							dataItem.setCustomerCategory(customerCategory);
							IndexItem indexItem=indexItemDAO.getEntityById(indexItemId);
							dataItem.setIndexItem(indexItem);
							dataItem.setTerm(insertTerm);
							dataItem.setValue(Double.parseDouble(value));
							dataItems.add(dataItem);
						}
						else{
							if(!StringUtils.isNumeric(value)){
								String info="数据<"+value+">出错,在第"+(j+1)+"行，第"+i+"列";
								uploadInfo.add(info);
							}
						}
					}
					else{
						String info="";
						if(StringUtils.isNotBlank(""+result.get(j).get(0))){
						info="第"+(j+1)+"行出错,指标为空";
					}
						else info="指标<"+result.get(j).get(0)+">,第"+(j+1)+"行出错";
						uploadInfo.add(info);
					}
//					System.out.println("("+customerId+","+indexItemId+","+value+")");
				}
				}
				else{
					String info="客户群<"+result.get(1+up).get(i)+">,第"+i+"列出错";
					uploadInfo.add(info);
				}
			}
			if(dataItems.size()>0&&uploadInfo.size()==0){
				saveDataItems(dataItems);
				insertTerm.setProject(dataItems.get(0).getCustomerCategory().getProject());
				String info="导入成功{导入数据条数："+dataItems.size()+"}";
				uploadInfo.add(info);
				termDAO.update(insertTerm);
			}
			else{
				termDAO.delete(insertTerm);
				Set<String> newInfo=new LinkedHashSet<String>();
				if(dataItems.size()==0){
					if(uploadInfo.size()==0){
						String info="导入失败:{没有可导入数据}";
						newInfo.add(info);
					}
					else{
						String info="导入失败{错误："+uploadInfo.size()+"个}如下：";
						newInfo.add(info);
//						newInfo.addAll(uploadInfo);
					}
				}
				else{
					String info="导入失败{错误："+uploadInfo.size()+"个}如下：";
					newInfo.add(info);
					newInfo.addAll(uploadInfo);
				}
				Struts2Utils.getRequest().setAttribute("uploadInfo", newInfo);
				return false;
			}
			
		}
		Struts2Utils.getRequest().setAttribute("uploadInfo", uploadInfo);
		return true;
	}
	@Transactional
	public void saveDataItems(List<DataItem> dataItems) {
		for(DataItem dataItem:dataItems){
			dataItemDAO.insert(dataItem);
		}
	}

	public boolean valid(File file) {
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(file);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		// 取得第一个sheet
		Sheet sheet = book.getSheet(0);
		String flag=sheet.getCell(0,0).getContents();
		if("true".equals(flag.trim())){
			return true;
		}
		return false;
	}

	public List<DataItem> getEntitiesByTermId(Long termId) {
		return dataItemDAO.getEntitiesByTermId(termId);
	}

	public Map<String, Double> getValueMap(List<DataItem> dataItems) {
		Map<String, Double> valueMap=new HashMap<String,Double>();
		for(DataItem dataItem:dataItems){
			String str="("+dataItem.getCustomerCategory().getId()+","+dataItem.getIndexItem().getId()+")";
			valueMap.put(str, dataItem.getValue());
		}
		return valueMap;
	}

	public int[] getRowCount(Map<String, Map<IndexCategory, List<IndexItem>>> rowMap) {
		int[]rowCount=new int[rowMap.size()];
		int i=0;
		Set<String> key = rowMap.keySet();
		for(Iterator<String> it = key.iterator(); it.hasNext();i++){
			String str = (String) it.next();
			Map<IndexCategory,List<IndexItem>> subMap=rowMap.get(str);
			Set<IndexCategory> subKey = subMap.keySet();
			int j=0;
			for(Iterator<IndexCategory> it1 = subKey.iterator(); it1.hasNext();){
				IndexCategory indexCategory =it1.next();
				j+=subMap.get(indexCategory).size();
			}
			rowCount[i]=j;
		}
		return rowCount;
	}

	public void updateByParamsMap(Map<String, String[]> paramsMap) {
		Map<String, String> valueMap=new HashMap<String,String>();
		Set<String> key = paramsMap.keySet();
		for(Iterator<String> it = key.iterator(); it.hasNext();){
			String str =it.next();
			String[]str1=paramsMap.get(str);
			String flag="";
			if(str.length()>=5){
				flag=""+str.charAt(0)+str.charAt(2)+str.charAt(4);
			}
			if("(,)".equals(flag)){
				valueMap.put(str, str1[0]);
			}
		}
		String termIdStr=paramsMap.get("termId")[0];
		Long termId=null;
		if(StringUtils.isNotEmpty(termIdStr)){
			termId=Long.parseLong(termIdStr);
		}
		updateValueMap(valueMap,termId);
	}

	@SuppressWarnings("unused")
	public void updateValueMap(Map<String, String> valueMap, Long termId) {
		Term term=termDAO.getEntityById(termId);
		Set<String> key = valueMap.keySet();
		for(Iterator<String> it = key.iterator(); it.hasNext();){
			String str =it.next();
			String str1=valueMap.get(str);
			double value=Double.parseDouble(str1);
		}
		
	}

	public boolean updateData(Long termId, Long customerCategoryId,Long indexItemId, double dataValue) {
		if(dataValue>0){
		DataItem dataItem= dataItemDAO.getEntityByIds(termId,customerCategoryId,indexItemId);
		if(dataItem==null){
			dataItem=new DataItem();
			Term term=new Term();
			term=termDAO.getEntityById(termId);
			CustomerCategory category=new CustomerCategory();
			category=customerCategoryDAO.getEntityById(customerCategoryId);
			IndexItem indexItem=new IndexItem();
			indexItem=indexItemDAO.getEntityById(indexItemId);
			dataItem.setTerm(term);
			dataItem.setCustomerCategory(category);
			dataItem.setIndexItem(indexItem);
		}
		dataItem.setValue(dataValue);
		dataItemDAO.saveOrUpdate(dataItem);
		}
		return true;
	}
	@Override
	public void delete(DataItem entity) {
		entity.setDeleted(true);
		update(entity);
	}
	public void deleteEntities(List<DataItem> dataItems) {
		for(DataItem dataItem:dataItems){
			delete(dataItem);
		}
	}
	public void deleteAll(Long termId) {
		dataItemDAO.deleteAll(termId);
	}

	/**
	 * 指标标题JSON输出
	 * 
	 * @return
	 */
	public JSONObject getIndexJson() {
		IndexCategory indexCategory = new IndexCategory();
		indexCategory.setId(0L);
		List<IndexCategory> indexCategories = indexCategoryDAO.getSubEntrities(indexCategory);
		JSONObject products = new JSONObject();
		getIndexJson(products, "productId", indexCategories, 0);
		return products;
	}

	/**
	 * 指标标题JSON输出
	 * 
	 * @param products
	 * @param indexCategories
	 */
	public int getIndexJson(JSONObject products, String productKey, List<IndexCategory> indexCategories, int append) {
		List<IndexCategory> list = null;
		JSONObject productAs ;
		if (products.has(productKey)) {
			productAs = products.getJSONObject(productKey);
		} else {
			productAs = new JSONObject();
		}
		int rowspan = 0, rowIndex = 0;
		for (IndexCategory indexCategory : indexCategories) { // PA
			list = indexCategoryDAO.getSubEntrities(indexCategory);
			//productAs.add(jsonA);
			if (list.size() > 0) {
				rowspan = getIndexJson(products, "indexCategory", list, rowIndex);
			} else {
				//根据指标数量,获取通栏数
				rowspan = indexItemDAO.findCountByIndexCategoryId(indexCategory.getId());
			}
			JSONObject json = new JSONObject();
			json.put("name", indexCategory.getName());
			json.put("index", append + rowIndex);// 
			json.put("field", productKey);
			json.put(productKey, indexCategory.getId());
			json.put("rowspan", rowspan);
			productAs.put(indexCategory.getId(), json);
			rowIndex += rowspan; //设置下一个Field的位置
		}
		products.put(productKey, productAs);
		return rowIndex;
	}
	
	/**
	 * 数据值的输出
	 * 
	 * @param dataItems
	 * @return
	 */
	public JSONArray getValueJson1(List<DataItem> dataItems){
		List<CustomerCategory> customerCategories = customerCategoryDAO.getChildEntities();
		Map<String, DataItem> dataMap = new HashMap<String, DataItem>();
		for (DataItem dataItem : dataItems) {
			dataMap.put(dataItem.getIndexItem().getId() + "_" + dataItem.getCustomerCategory().getId(), dataItem);
		}
		IndexCategory indexCategory = new IndexCategory();
		indexCategory.setId(0L);
		List<IndexCategory> indexCategories = indexCategoryDAO.getSubEntrities(indexCategory);
		JSONArray array = new JSONArray();
		getValueJsonx(array, dataMap, indexCategory, indexCategories, customerCategories);
		return array;
	}

	/**
	 * 数据值的输出
	 * 
	 * @param array
	 * @param dataMap
	 * @param parentCategory
	 * @param indexCategories
	 * @param customerCategories
	 */
	public void getValueJsonx(JSONArray array, Map<String, DataItem> dataMap, IndexCategory parentCategory,
			List<IndexCategory> indexCategories, List<CustomerCategory> customerCategories) {
		List<IndexCategory> list = null;
		List<IndexItem> items;
		DataItem dataItem;
		JSONObject row = null;
		for (IndexCategory category : indexCategories) { // PA
			list = indexCategoryDAO.getSubEntrities(category);
			if (list.size() > 0) {
				getValueJsonx(array, dataMap, category, list, customerCategories);
			} else {
				items = indexItemDAO.getEntitiesByCategory(category);
				for (IndexItem indexItem : items) {
					row = new JSONObject();
					row.put("productId", parentCategory.getId());
					row.put("indexCategory", category.getId());
					row.put("graphType", SysCodeConst.graphTypeToNameMap.get(category.getGraphType()));
					row.put("indexItem", indexItem.getName());
					for (CustomerCategory customerCategory : customerCategories) {
						dataItem = dataMap.get(indexItem.getId() + "_" + customerCategory.getId());
						if (dataItem == null) {
							row.put("custChild" + customerCategory.getId(), null);
						} else {
							row.put("custChild" + customerCategory.getId(), dataItem.getValue());
						}
					}
					array.add(row);
				}
			}
		}
	}
}
