package com.zitop.tracking.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zitop.infrastructure.service.impl.AbstractService;
import com.zitop.tracking.dao.ICustomerCategoryDAO;
import com.zitop.tracking.dao.IDataItemDAO;
import com.zitop.tracking.entity.CustomerCategory;
import com.zitop.tracking.entity.Project;
import com.zitop.util.DataItemUtil;
import com.zitop.util.SystemUtil;


@Service
public class CustomerCategoryService extends AbstractService<CustomerCategory, Long, ICustomerCategoryDAO>
{
	@Resource
	private ICustomerCategoryDAO customerCategoryDAO;
	@Resource
	private IDataItemDAO dataItemDAO;
	@Override
	public ICustomerCategoryDAO getGenericDAO()
	{
		return customerCategoryDAO;
	}
	/**
	 * 返回存在子分类的客户群
	 * @return
	 */
	public List<CustomerCategory> getHasChildsEntities() {
		List<CustomerCategory> customerCategorys=customerCategoryDAO.getAllEntity();
		List<CustomerCategory> list=new ArrayList<CustomerCategory>();
		for(CustomerCategory customerCategory:customerCategorys){
			if(customerCategory.getHasChilds()){
				list.add(customerCategory);
			}
		}
		return list;
	}
	public boolean hasChilds(CustomerCategory customerCategory) {
		boolean flag=false;
		List<CustomerCategory> customerCategories=customerCategoryDAO.getSubEntities(customerCategory);
		if(customerCategories.size()>0){
			flag=true;
		}
		return flag;
	}
	@Override
	public void delete(CustomerCategory entity) {
		entity.setDeleted(true);
		customerCategoryDAO.update(entity);
	}
	public List<CustomerCategory> getSubEntities(CustomerCategory customerCategory){
		return customerCategoryDAO.getSubEntities(customerCategory);
	}
	
	/**
	 * 根据指定的id数组查询客户群数组
	 * @param ids
	 */
	public List<CustomerCategory> getSubCustomerCategoryByIds(Long[] ids)
	{
		List<CustomerCategory> list = null;
		if (ids != null && ids.length > 0)
		{
			list = customerCategoryDAO.getSubCustomerCategoryByIds(ids);
		}
		return list;
	}
	/**
	 * 获取指定分类的指标数
	 * @param indexCategoryId 分类id
	 */
	public int getCountByParentId(Long parentId)
	{
		return customerCategoryDAO.getCountByParentId(parentId);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void importExcelEntities( String filePath, Project project ){
		List<List> result = null;
		try {
			result = DataItemUtil.read(filePath);
		} catch (BiffException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
		List<String> items=result.get(0);
		List<String> items2=result.get(1);
		long parentId=0;
		for(int i=0;i<items.size();i++){
			String item=items.get(i);
			if(StringUtils.isNotEmpty(item)){
				CustomerCategory category=new CustomerCategory();
				category.setName(item);
				category.setHasChilds(true);
				category.setParentId((long) 0);
				category.setProject(project);
				category.setCreator(SystemUtil.getSysUserName());
				parentId=customerCategoryDAO.insert(category).getId();
			}
			String item2=items2.get(i);
			CustomerCategory category=new CustomerCategory();
			category.setName(item2);
			category.setHasChilds(false);
			category.setProject(project);
			category.setParentId(parentId);
			category.setCreator(SystemUtil.getSysUserName());
			customerCategoryDAO.insert(category);
			
		}
		
	}
	/**
	 * 查看是否有下级数据
	 * @param customerCategory
	 * @return
	 */
	public boolean checkSub(CustomerCategory customerCategory) {
		if(customerCategoryDAO.getSubEntities(customerCategory).size()==0&&dataItemDAO.getEntitiesByCustomerId(customerCategory.getId()).size()==0){
			return false;
		}
		return true;
	}
}
