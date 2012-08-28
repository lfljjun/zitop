package com.zitop.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zitop.util.SysCodeConst;

/**
 * 系统编码
 * @author Joseph
 */
public class SysCodeConstInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = -4517495841332235595L;

	public String intercept(ActionInvocation inv) throws Exception {
		
		ActionContext context = inv.getInvocationContext();
		context.getValueStack().push(SysCodeConst.getInstance());
		String result = inv.invoke();
		
		return result;
	}

}
