package com.zitop.util.tags;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zitop.tracking.entity.Term;
import com.zitop.util.SysDataUtil;

/**
 * 标签工具类
 * 判断只有一期数据展示
 * @author Joseph
 */
public class IsSingleTerm extends TagSupport {

	private static final long serialVersionUID = -3302635868773504927L;
	private List<Term> terms;
	
	public int doStartTag() throws JspException {
		if(SysDataUtil.isSingleTerm(terms))
			return TagSupport.EVAL_BODY_INCLUDE;
		else
			return TagSupport.SKIP_BODY;
	}
	public List<Term> getTerms() {
		return terms;
	}
	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}
	
	

}
