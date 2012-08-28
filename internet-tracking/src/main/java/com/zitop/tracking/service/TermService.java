package com.zitop.tracking.service;

import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zitop.infrastructure.service.impl.AbstractService;
import com.zitop.tracking.dao.IDataItemDAO;
import com.zitop.tracking.dao.ITermDAO;
import com.zitop.tracking.entity.Term;

import edu.emory.mathcs.backport.java.util.Collections;

@Service
public class TermService extends AbstractService<Term, Long, ITermDAO>
{
	@Resource
	private ITermDAO termDAO;
	@Resource
	private IDataItemDAO dataItemDAO;
	@Override
	public ITermDAO getGenericDAO()
	{
		return termDAO;
	}
	
	public void test(int i)
	{}
	
	/**
	 * 根据指定的id数组查询期数数组
	 * @param ids
	 */
	public List<Term> getTermByIds(Long[] ids)
	{
		List<Term> list = null;
		if (ids != null && ids.length > 0) 
		{
			list = termDAO.getTermByIds(ids);
		}
		return list;
	}

	/**
	 * 查看是否有下级数据
	 * @param term
	 * @return
	 */
	public boolean checkSub(Term term) {
		if(dataItemDAO.getEntitiesByTermId(term.getId()).size()==0){
			return false;
		}
		return true;
	}
	@Override
	public void delete(Term entity) {
		entity.setDeleted(true);
		super.update(entity);
	}
}
