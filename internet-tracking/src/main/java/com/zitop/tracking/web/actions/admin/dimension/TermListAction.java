package com.zitop.tracking.web.actions.admin.dimension;

import javax.annotation.Resource;

import com.zitop.infrastructure.service.IGenericService;
import com.zitop.infrastructure.struts2.action.hibernate.ServiceBasePaginationAction;
import com.zitop.infrastructure.struts2.utils.Struts2Utils;
import com.zitop.infrastructure.util.ParamCondition;
import com.zitop.tracking.entity.Term;
import com.zitop.tracking.service.TermService;
import com.zitop.util.SysContextUtil;
import com.zitop.util.SystemUtil;

public class TermListAction extends ServiceBasePaginationAction<Term, Long> {
	private static final long serialVersionUID = 3242861282910854705L;
	@Resource
	private TermService termService;
	private boolean flag;

	@Override
	public IGenericService<Term, Long> getGenericService() {
		return termService;
	}

	@Override
	public void preExecute() {

	}

	public String execute() {
		ParamCondition paramCondition = getPager().getParamCondition();
		SystemUtil.addParamCurrentProjectId(paramCondition);
		if (!SysContextUtil.hasAdminRole()) {
			getPager().getParamCondition().addParameter("creator", SystemUtil.getSysUserName());
		}
		return super.execute();
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
