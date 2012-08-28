package com.zitop.util.tags;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.entity.IndexItem;
import com.zitop.tracking.entity.Term;
import com.zitop.util.SysDataUtil;

/**
 * 标签工具类
 * @author Joseph
 */
public class IsPieTag extends TagSupport {
	private static final long serialVersionUID = -4276232224239846533L;
	private List<IndexItem> indexItems;
	private List<Term> terms;
	private boolean eq;
	
	@Override
	public int doStartTag() throws JspException {
		if(isPie(indexItems,terms) == eq)
			return TagSupport.EVAL_BODY_INCLUDE;
		else
			return TagSupport.SKIP_BODY;
	}

	/**
	 * 判断是否是饼图展示 
	 */
	public static boolean isPie(List<IndexItem> indexItems,List<Term> terms)
	{
		if(terms != null && terms.size() > 1)
			return false;
		if(indexItems == null || indexItems.size() == 0)
			return false;
		for(IndexItem item : indexItems)
		{
			if(item.getIndexCategory().getGraphType() != IndexCategory.GRAPHTYPE_PIE)
				return false;
		}
		return SysDataUtil.isSingleIndexCategory(indexItems);
	}

	public List<IndexItem> getIndexItems() {
		return indexItems;
	}

	public void setIndexItems(List<IndexItem> indexItems) {
		this.indexItems = indexItems;
	}

	public boolean isEq() {
		return eq;
	}

	public void setEq(boolean eq) {
		this.eq = eq;
	}

	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}
}
