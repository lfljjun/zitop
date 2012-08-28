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
import com.zitop.tracking.dao.IIndexItemDAO;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;

@ChildOf(parent = "genericHibernateDAO")
@Repository
public class IndexItemDAOImpl extends GenericHibernateDAO<IndexItem, Long> implements IIndexItemDAO
{
	@SuppressWarnings("unchecked")
	@Override
	public List<IndexItem> getEntitiesByParamCondition(ParamCondition paramCondition, int firstResult, int pageSize) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		// 通过参数解释器解释出相应的查询条件
		setCriterions(crit, paramCondition);
		
		if (firstResult >= 0 && pageSize >= 0) {
			crit.setMaxResults(pageSize);
			crit.setFirstResult(firstResult);
		}
//		crit.add(Restrictions.eq("deleted",false));
		// 查询出相应的实体对象
		return crit.list();
	}
	/**
	 * 根据参数获取符合条件的元素个数
	 * 
	 * @param paramCondition
	 * @return
	 */
	public int getCountOfEntitiesByParamCondition(ParamCondition paramCondition) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		// 通过参数解释器解释出相应的查询条件
		setCriterions(crit, paramCondition);
		crit.setProjection(Projections.rowCount());
		// 查询出总对象数
		int countOfEntity = ((Number) crit.uniqueResult()).intValue();
		return countOfEntity;
	}
	/**
	 * 添加参数
	 * 
	 * @param criteria
	 * @param paramCondition
	 */
	private void setCriterions(Criteria criteria, ParamCondition paramCondition) {
		String categoryId=paramCondition.getParameter("categoryId");
		if (StringUtils.isNotEmpty(categoryId)) {
			criteria.add(Restrictions.eq("indexCategory.id",Long.parseLong(categoryId)));
		}
		String categoryIds[] = paramCondition.get("categoryIds");
		if (categoryIds != null && categoryIds.length >=0) {
			if(categoryIds.length==0){
				criteria.add(Restrictions.eq("indexCategory.id", 0L));
			}
			else{
				Long idL[] = new Long[categoryIds.length];
				for (int i = 0; i < categoryIds.length; i++) {
					idL[i] = Long.valueOf(categoryIds[i]);
				}
				criteria.add(Restrictions.in("indexCategory.id", idL));
			}
		}
		String ids[] = paramCondition.get("ids");
		if (ids != null && ids.length > 0) {
			Long idL[] = new Long[ids.length];
			for (int i = 0; i < ids.length; i++) {
				idL[i] = Long.valueOf(ids[i]);
			}
			criteria.add(Restrictions.in("id", idL));
		}

		criteria.add(Restrictions.eq("deleted", false));
	}
	
	private void setOrder(Criteria crit, ParamCondition paramCondition) {
		String orders = paramCondition.getParameter("orders");
		if (StringUtils.isNotEmpty(orders)) {
			String[] orderArray = orders.split(",");
			for (String orderSource : orderArray) {
				String[] orderArray2 = orderSource.split("\\|");
				if (orderArray2.length == 2) {
					if ("desc".equalsIgnoreCase(orderArray2[1])) {
						crit.addOrder(Order.desc(orderArray2[0]));
					} else {
						crit.addOrder(Order.asc(orderArray2[0]));
					}
				} else if (orderArray2.length == 1) {
					crit.addOrder(Order.asc(orderArray2[0]));
				} else {
					
				}
			}
		} else {
			crit.addOrder(Order.desc("id"));
		}
	}
	/**
	 * 根据指定的id数组查询指标数组
	 * @param ids
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<IndexItem> getIndexItemByIds(Long[] ids) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		if(ids != null && ids.length >0)
			crit.add(Restrictions.in("id", ids));
		crit.addOrder(Order.desc("sequence")).addOrder(Order.asc("id"));
		return crit.list();
	}
	/**
	 * 根据指定的指标id数据查询指标分类
	 * @param ids
	 */
	@SuppressWarnings("unchecked")
	public List<IndexCategory> getIndexCategoryByIndexIds(Long[] ids)
	{
		Criteria crit = getSession().createCriteria(getPersistentClass());
		if(ids != null && ids.length >0)
			crit.add(Restrictions.in("id", ids));
		crit.setProjection(Projections.distinct(Projections.property("indexCategory")));
		crit.addOrder(Order.desc("sequence")).addOrder(Order.asc("id"));
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IndexItem> getEntitiesByCategory(IndexCategory indexCategory) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("indexCategory.id",indexCategory.getId()));
		return crit.list();
	}
	@Override
	public Integer findCountByIndexCategoryId(Long indexCategoryId) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("indexCategory.id", indexCategoryId));
		crit.setProjection(Projections.rowCount());
		int countOfEntity = ((Number) crit.uniqueResult()).intValue();
		return countOfEntity;
	}
	
}
