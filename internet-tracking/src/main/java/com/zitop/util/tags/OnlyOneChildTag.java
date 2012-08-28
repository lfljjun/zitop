package com.zitop.util.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zitop.tracking.service.CustomerCategoryService;
import com.zitop.util.SystemUtil;

public class OnlyOneChildTag extends TagSupport{

	private static final long serialVersionUID = -714308401207443810L;

	/** 分类id */
	private Long parentId;

	public int doStartTag() throws JspException {
		CustomerCategoryService customerCategoryService = SystemUtil.getBean("customerCategoryService");
		int count = customerCategoryService.getCountByParentId(parentId);
		if(count == 1)
			return TagSupport.EVAL_BODY_INCLUDE;
		else
			return TagSupport.SKIP_BODY;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	
	
}
