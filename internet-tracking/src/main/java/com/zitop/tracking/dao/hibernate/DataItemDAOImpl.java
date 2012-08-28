package com.zitop.tracking.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zitop.infrastructure.dao.hibernate.GenericHibernateDAO;
import com.zitop.infrastructure.springframework.ChildOf;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.tracking.dao.IDataItemDAO;
import com.zitop.tracking.entity.DataItem;
import com.zitop.tracking.entity.IndexItem;

@ChildOf(parent = "genericHibernateDAO")
@Repository
public class DataItemDAOImpl extends GenericHibernateDAO<DataItem, Long> implements IDataItemDAO
{

	@Override
	public int getCountOfEntitiesByParamCondition(ParamCondition paramCondition) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		
		setCriterions(crit, paramCondition);
		crit.setProjection(Projections.rowCount());
		
		return ((Number)crit.uniqueResult()).intValue();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<DataItem> getEntitiesByParamCondition(ParamCondition paramCondition, int firstResult, int pageSize) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		
		setCriterions(crit, paramCondition);
		setOrders(crit, paramCondition);
		if(firstResult > -1 && pageSize > -1)
		{
			crit.setMaxResults(pageSize).setFirstResult(firstResult);
		}
		
		return crit.list();
	}

	private void setOrders(Criteria crit, ParamCondition paramCondition) {
		crit.addOrder(Order.asc("term.id")).addOrder(Order.asc("indexItem.id"))
			.addOrder(Order.asc("customerCategory.id"));
	}

	private void setCriterions(Criteria crit, ParamCondition paramCondition) {
		String indexItemId = paramCondition.getParameter("indexItemId");
		if(StringUtils.isNotBlank(indexItemId))
		{
			crit.add(Restrictions.eq("indexItem.id", Long.valueOf(indexItemId)));
		}
		String termId = paramCondition.getParameter("termId");
		if(StringUtils.isNotBlank(termId))
		{
			crit.add(Restrictions.eq("term.id", Long.valueOf(termId)));
		}
		String customerCategoryId = paramCondition.getParameter("customerCategoryId");
		if(StringUtils.isNotBlank(customerCategoryId))
		{
			crit.add(Restrictions.eq("customerCategory.id", Long.valueOf(customerCategoryId)));
		}
		
		// params:termIds#indexIds#customerIds
		String params = paramCondition.getParameter("params");
		if (StringUtils.isNotEmpty(params)) {
			String[] paramArray = params.split("#");
			
			String[] termIds = paramArray[0].split("\\|");
			Long termIdL[] = new Long[termIds.length];
			for (int i = 0; i < termIds.length; i++) {
				termIdL[i] = Long.valueOf(termIds[i]);
			}
			crit.add(Restrictions.in("term.id", termIdL));

			String[] indexIds = paramArray[1].split("\\|");
			Long indexIdL[] = new Long[indexIds.length];
			for (int i = 0; i < indexIds.length; i++) {
				indexIdL[i] = Long.valueOf(indexIds[i]);
			}
			crit.add(Restrictions.in("indexItem.id", indexIdL));
			
			String[] customerIds = paramArray[2].split("\\|");
			Long customerIdL[] = new Long[customerIds.length];
			for (int i = 0; i < customerIds.length; i++) {
				customerIdL[i] = Long.valueOf(customerIds[i]);
			}
			crit.add(Restrictions.in("customerCategory.id", customerIdL));
			
		}

		List<Long> indexItemIds = paramCondition.getLongList("indexItemIds");
		if(indexItemIds != null && indexItemIds.size() > 0)
		{
			crit.add(Restrictions.in("indexItem.id", indexItemIds));
		}
		List<Long> termIds = paramCondition.getLongList("termIds");
		if(termIds != null && termIds.size() > 0)
		{
			crit.add(Restrictions.in("term.id", termIds));
		}
		List<Long> customerCategoryIds = paramCondition.getLongList("customerCategoryIds");
		if(customerCategoryIds != null && customerCategoryIds.size() > 0)
		{
			crit.add(Restrictions.in("customerCategory.id", customerCategoryIds));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataItem> getEntitiesByTermId(Long termId) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("term.id", termId));
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataItem getEntityByIds(Long termId, Long customerCategoryId,Long indexItemId) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("term.id", termId));
		crit.add(Restrictions.eq("customerCategory.id", customerCategoryId));
		crit.add(Restrictions.eq("indexItem.id", indexItemId));
		List<DataItem> dataItems=crit.list();
		if(dataItems.size()==0){
			return null;
		}
		return dataItems.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndexItem> getEntitiesByIndexItemId(Long indexItemId) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("indexItem.id", indexItemId));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndexItem> getEntitiesByCustomerId(Long customerId) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("customerCategory.id", customerId));
		return crit.list();
	}

	@Override
	public void deleteAll(Long termId) {
		String sql = "update DataItem set deleted = true where term.id = "+termId;
		getSession().createQuery(sql).executeUpdate();
		
	}
	

}
