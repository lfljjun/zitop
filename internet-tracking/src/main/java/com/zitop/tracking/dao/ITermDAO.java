package com.zitop.tracking.dao;

import java.util.List;

import com.zitop.tracking.entity.Project;
import com.zitop.tracking.entity.Term;
import com.zitop.infrastructure.dao.IGenericDAO;

public interface ITermDAO extends IGenericDAO<Term, Long>
{
	/**
	 * 根据指定的id数组查询期数数组
	 * @param ids
	 */
	public List<Term> getTermByIds(Long[] ids) ;

	public List<Term> getEntitiesByProject(Project project);
}
