package com.zitop.tracking.web.actions.admin.index;

import javax.annotation.Resource;

import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBasePaginationAction;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.tracking.entity.IndexCategory;
import com.zitop.tracking.service.IndexCategoryService;
import com.zitop.util.SystemUtil;

public class IndexCategoryListAction extends ServiceBasePaginationAction<IndexCategory, Long> {
	private static final long serialVersionUID = 4498607849205992363L;
	@Resource
	private IndexCategoryService indexCategoryService;

	private Long parentId;
	private Long parentParentId;

	@Override
	public IGenericService<IndexCategory, Long> getGenericService() {
		return indexCategoryService;
	}

	@Override
	public void preExecute() {

	}

	/**
	 * 指标分类列表
	 */
	@Override
	public String execute() {
		ParamCondition paramCondition = getPager().getParamCondition();
		SystemUtil.addParamCurrentProjectId(paramCondition);
		if (parentId == null) {
			parentId = 0L;
			paramCondition.addParameter("parentId", "0");
		} else {
			IndexCategory index = indexCategoryService.getEntityById(parentId);
			if (index != null) {
				parentParentId = index.getParentId();
			}
		}
		return super.execute();
	}

	public String child() {
		indexCategoryService.getEntitiesOfPagerByParamCondition(getPager());
		return "child";
	}

	public String[] getGraphTypes() {
		return IndexCategory.GRAPH_TYPE_ARRAY;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentParentId() {
		return parentParentId;
	}

	public void setParentParentId(Long parentParentId) {
		this.parentParentId = parentParentId;
	}

}
