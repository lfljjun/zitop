package com.zitop.tracking.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionSupport;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;
import com.zitop.infrastructure.util.Pager;
import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.DataItem;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.tracking.entity.Term;

@Service
public class ShowService extends ActionSupport {
	private static final long serialVersionUID = 2942865447295944265L;

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

	private List<IndexItem> indexItemList;// 小指标
	private Map<Long, Integer> subCategoryMap;// 中指标(id,跨行)
	private Map<Long, Integer> categoryMap;// 大指标(id,跨行)
	private Map<Long, IndexCategory> categoryMap2;// 大指标2(id,实体)
	private List<CustomerCategory> customerList;// 客户群子类
	private Map<String, Integer> parentCustomerMap;// 客户群大类(名称,跨列)
	private List<Term> termList;// 期数
	private List<DataItem> dataItemList;// 数据
	private List<List<List<Double>>> dataList;// 加工后的数据

	/**
	 * 表格展示
	 */
	public void showTable() {
		prepare();
		Struts2Utils.getRequest().setAttribute("index_item_list", indexItemList);
		Struts2Utils.getRequest().setAttribute("sub_category_map", subCategoryMap);
		Struts2Utils.getRequest().setAttribute("category_map", categoryMap);
		Struts2Utils.getRequest().setAttribute("category_map2", categoryMap2);
		Struts2Utils.getRequest().setAttribute("customer_list", customerList);
		Struts2Utils.getRequest().setAttribute("customer_parent_map", parentCustomerMap);
		Struts2Utils.getRequest().setAttribute("term_list", termList);
		Struts2Utils.getRequest().setAttribute("data_list", dataList);
	}

	/**
	 * 导出excel
	 * 
	 * @throws Exception
	 */
	public void exportTable() throws Exception {
		prepare();

		HttpServletResponse response = Struts2Utils.getResponse();
		String fileName = new String("移动互联网行为跟踪宽表.xls".getBytes("GBK"), "iso-8859-1");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "inline; filename=" + fileName);
		WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());

		for (int i = 0; i < termList.size(); i++) {
			Term term = termList.get(i);
			WritableSheet sheet = workbook.createSheet(term.getName(), i); // 添加第一个工作表
			sheet.getSettings().setDefaultColumnWidth(15);
			sheet.getSettings().setHorizontalFreeze(3);
			sheet.getSettings().setVerticalFreeze(2);
			sheet.getSettings().setHorizontalCentre(true);// ?
			sheet.getSettings().setVerticalCentre(true);// ?

			final int right = 0, down = 0;
			int tmpRight = right;
			int tmpDown = down;

			// 固定表头
			sheet.mergeCells(right, down, right, right + 1);
			sheet.addCell(new Label(right, down, "模块"));
			sheet.mergeCells(right + 1, down, right + 1, down + 1);
			sheet.addCell(new Label(right + 1, down, "分类"));
			sheet.mergeCells(right + 2, down, right + 2, down + 1);
			sheet.addCell(new Label(right + 2, down, "指标"));

			tmpRight = right + 3;
			tmpDown = down;
			// 客户群大类
			Iterator<String> parentCustomerIt = parentCustomerMap.keySet().iterator();
			while (parentCustomerIt.hasNext()) {
				String key = parentCustomerIt.next();
				sheet.mergeCells(tmpRight, tmpDown, tmpRight + parentCustomerMap.get(key) - 1, tmpDown);
				sheet.addCell(new Label(tmpRight, tmpDown, key));
				tmpRight += parentCustomerMap.get(key);
			}

			tmpRight = right + 3;
			tmpDown = down;
			// 客户群子类
			for (int j = 0; j < customerList.size(); j++) {
				sheet.addCell(new Label(tmpRight + j, tmpDown + 1, customerList.get(j).getName()));
			}

			tmpRight = right;
			tmpDown = down + 2;
			// 大指标
			Iterator<Long> categoryIt = categoryMap.keySet().iterator();
			while (categoryIt.hasNext()) {
				Long key = categoryIt.next();
				sheet.mergeCells(tmpRight, tmpDown, tmpRight, tmpDown + categoryMap.get(key) - 1);
				sheet.addCell(new Label(tmpRight, tmpDown, indexCategoryService.getEntityById(key).getName()));
				tmpDown += categoryMap.get(key);
			}

			tmpRight = right + 1;
			tmpDown = down + 2;
			// 中指标
			Iterator<Long> subCategoryIt = subCategoryMap.keySet().iterator();
			while (subCategoryIt.hasNext()) {
				Long key = subCategoryIt.next();
				sheet.mergeCells(tmpRight, tmpDown, tmpRight, tmpDown + subCategoryMap.get(key) - 1);
				sheet.addCell(new Label(tmpRight, tmpDown, indexCategoryService.getEntityById(key).getName()));
				tmpDown += subCategoryMap.get(key);
			}

			tmpRight = right + 2;
			tmpDown = down + 2;
			// 小指标
			for (int j = 0; j < indexItemList.size(); j++) {
				sheet.addCell(new Label(tmpRight, tmpDown + j, indexItemList.get(j).getName()));
			}

			tmpRight = right + 3;
			tmpDown = down + 2;
			// 数据
			List<List<Double>> termList = dataList.get(i);
			for (List<Double> indexList : termList) {
				for (int j = 0; j < indexList.size(); j++) {
					sheet.addCell(new Label(tmpRight + j, tmpDown, indexList.get(j) == null ? "" : String.valueOf(indexList.get(j))));
				}
				++tmpDown;
			}
		}

		workbook.write();
		workbook.close();
	}

	/**
	 * 展示和导出功能共用
	 */
	private void prepare() {
		String zhibiaos_s = Struts2Utils.getRequest().getAttribute("zhibiaos").toString();
		String kehus_s = Struts2Utils.getRequest().getAttribute("kehus").toString();
		String qishus_s = Struts2Utils.getRequest().getAttribute("qishus").toString();

		String[] zhibiaoCategory = zhibiaos_s.split("\\|");
		String[] zhibiaos = getAllSubZhibiao(zhibiaoCategory);
		String[] kehus = kehus_s.split("\\|");
		String[] qishus = qishus_s.split("\\|");
		
		// 小指标
		indexItemList = getIndexItemList(zhibiaos);
		// 中指标
		subCategoryMap = getSubCategoryMap(indexItemList);
		// 大指标
		categoryMap = new LinkedHashMap<Long, Integer>();
		categoryMap2 = new LinkedHashMap<Long, IndexCategory>();
		for (int i = 0; i < indexItemList.size(); i++) {
			long subCategory_parentId = indexItemList.get(i).getIndexCategory().getParentId();
			int count = 1;
			for (int j = i + 1; j < indexItemList.size(); j++) {
				if ((long) indexItemList.get(j).getIndexCategory().getParentId() != subCategory_parentId) {
					i = i + count - 1;
					break;
				}
				++count;
				if (j == indexItemList.size() - 1) {
					i = i + count - 1;
				}
			}
			categoryMap.put(subCategory_parentId, count);
			categoryMap2.put(subCategory_parentId, indexCategoryService.getEntityById(subCategory_parentId));
		}
		// 客户群子类
		customerList = getCustomerCategoryList(kehus);
		// 客户群大类
		parentCustomerMap = getParentCustomer(customerList);
		// 期数
		termList = getTermList(qishus);
		
		if (Struts2Utils.getRequest().getAttribute("preview") == null) {
			String params = "";
			if (Struts2Utils.getRequest().getAttribute("termId") != null) {
				params = Struts2Utils.getRequest().getAttribute("termId").toString() + "#" + getParams(zhibiaos) + "#" + kehus_s;
			} else {
				params = qishus_s + "#" + getParams(zhibiaos) + "#" + kehus_s;
			}
			// 数据
			dataItemList = getDataItemList(params);
			// 加工数据
			dataList = processData();
		}
	}
	
	private String getParams(String[] kehus) {
		String params="";
		for(String str:kehus){
			params+=str+"|";
		}
		return params;
	}

	public String[] getAllSubZhibiao(String[] zhibiaoCategory) {
		List<String>list=new ArrayList<String>();
		for(int i=0;i<zhibiaoCategory.length;i++){
		    IndexCategory category=indexCategoryService.getEntityById(Long.parseLong(zhibiaoCategory[i]));
			List<IndexItem> indexItems=indexItemService.getEntitiesByCategory(category);
			for(IndexItem indexItem:indexItems){
				list.add(""+indexItem.getId());
			}
		}
		String []str=new String[list.size()];
		for(int i=0;i<list.size();i++){
			str[i]=list.get(i);
		}
		return str;
	}

	public String[] getAllSubKehus(String[] kehuParentCategory) {
		List<String>list=new ArrayList<String>();
		for(int i=0;i<kehuParentCategory.length;i++){
			CustomerCategory category=customerCategoryService.getEntityById(Long.parseLong(kehuParentCategory[i]));
			List<CustomerCategory> categorys=customerCategoryService.getSubEntities(category);
			for(CustomerCategory a:categorys){
				list.add(""+a.getId());
			}
		}
		String []str=new String[list.size()];
		for(int i=0;i<list.size();i++){
			str[i]=list.get(i);
		}
		return str;
	}

	/**
	 * 获取小指标
	 * 
	 * @param zhibiaos
	 * @return
	 */
	private List<IndexItem> getIndexItemList(String[] zhibiaos) {
		Pager<IndexItem> indexItemPager = new Pager<IndexItem>();
		indexItemPager.setPageSize(-1);
		indexItemPager.getParamCondition().put("ids", zhibiaos);
		indexItemPager.getParamCondition().addParameter("orders", "categoryId|asc,id|asc");
		indexItemService.getEntitiesOfPagerByParamCondition(indexItemPager);
		return indexItemPager.getItems();
	}

	/**
	 * 获取中指标
	 * 
	 * @param indexItemList
	 * @return
	 */
	private Map<Long, Integer> getSubCategoryMap(List<IndexItem> indexItemList) {
		Map<Long, Integer> subCategoryMap = new LinkedHashMap<Long, Integer>();
		for (int i = 0; i < indexItemList.size(); i++) {
			long categoryId = indexItemList.get(i).getIndexCategory().getId();
			int count = 1;
			for (int j = i + 1; j < indexItemList.size(); j++) {
				if ((long) indexItemList.get(j).getIndexCategory().getId() != categoryId) {
					i = i + count - 1;
					break;
				}
				++count;
				if (j == indexItemList.size() - 1) {
					i = i + count - 1;
				}
			}
			subCategoryMap.put(categoryId, count);
		}
		return subCategoryMap;
	}

	/**
	 * 获取用户群子类
	 * 
	 * @param kehus
	 * @return
	 */
	private List<CustomerCategory> getCustomerCategoryList(String[] kehus) {
		Pager<CustomerCategory> customerPager = new Pager<CustomerCategory>();
		customerPager.setPageSize(-1);
		customerPager.getParamCondition().put("ids", kehus);
		customerPager.getParamCondition().addParameter("orders", "parentId|asc,id|asc");
		customerCategoryService.getEntitiesOfPagerByParamCondition(customerPager);
		return customerPager.getItems();
	}

	/**
	 * 获取客户群大类
	 * 
	 * @param customerList
	 * @return
	 */
	private Map<String, Integer> getParentCustomer(List<CustomerCategory> customerList) {
		Map<String, Integer> parentCustomerMap = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < customerList.size(); i++) {
			long parentId = customerList.get(i).getParentId();
			int count = 1;
			for (int j = i + 1; j < customerList.size(); j++) {
				if ((long) customerList.get(j).getParentId() != parentId) {
					i = i + count - 1;
					break;
				}
				++count;
				if (j == customerList.size() - 1) {
					i = i + count - 1;
				}
			}
			parentCustomerMap.put(customerCategoryService.getEntityById(parentId).getName(), count);
		}
		return parentCustomerMap;
	}

	/**
	 * 获取期数
	 * 
	 * @param qishus
	 * @return
	 */
	private List<Term> getTermList(String[] qishus) {
		Pager<Term> termPager = new Pager<Term>();
		termPager.setPageSize(-1);
		termPager.getParamCondition().put("ids", qishus);
		termService.getEntitiesOfPagerByParamCondition(termPager);
		return termPager.getItems();
	}

	/**
	 * 获取数据
	 * 
	 * @param params
	 * @return
	 */
	private List<DataItem> getDataItemList(String params) {
		Pager<DataItem> dataPager = new Pager<DataItem>();
		dataPager.setPageSize(-1);
		dataPager.getParamCondition().addParameter("params", params);
		dataItemService.getEntitiesOfPagerByParamCondition(dataPager);
		return dataPager.getItems();
	}

	/**
	 * 加工数据
	 * 
	 * @return
	 */
	private List<List<List<Double>>> processData() {
		List<List<List<Double>>> termDatas = new ArrayList<List<List<Double>>>();
		for (Term term : termList) {
			long termId = term.getId();
			List<List<Double>> indexDatas = new ArrayList<List<Double>>();
			for (IndexItem indexItem : indexItemList) {
				long indexItemId = indexItem.getId();
				List<Double> customerDatas = new ArrayList<Double>();
				for (CustomerCategory customerCategory : customerList) {
					long customerCategoryId = customerCategory.getId();
					for (int i = 0; i < dataItemList.size(); i++) {
						DataItem dataItem = dataItemList.get(i);
						if (dataItem.getTerm().getId() == termId && dataItem.getIndexItem().getId() == indexItemId
								&& dataItem.getCustomerCategory().getId() == customerCategoryId) {
							customerDatas.add(dataItem.getValue());
							break;
						}
						if (dataItem.getTerm().getId() > termId) {
							customerDatas.add(null);
							break;
						}
						if (dataItem.getTerm().getId() == termId && dataItem.getIndexItem().getId() > indexItemId) {
							customerDatas.add(null);
							break;
						}
						if (dataItem.getTerm().getId() == termId && dataItem.getIndexItem().getId() == indexItemId
								&& dataItem.getCustomerCategory().getId() > customerCategoryId) {
							customerDatas.add(null);
							break;
						}
						if (i == dataItemList.size() - 1) {
							customerDatas.add(null);
							break;
						}
					}
				}
				indexDatas.add(customerDatas);
			}
			termDatas.add(indexDatas);
		}
		return termDatas;
	}

}
