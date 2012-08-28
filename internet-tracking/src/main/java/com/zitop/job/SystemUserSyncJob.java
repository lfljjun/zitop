package com.zitop.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.zitop.evaluate.service.SystemUserService;
import com.zitop.util.SystemUtil;

public class SystemUserSyncJob extends TransactionalQuartzTask{

	protected void executeTransactional(JobExecutionContext ctx) throws JobExecutionException 
	{
		SystemUserService systemUserService = SystemUtil.getBean("systemUserService");
		systemUserService.sync();
	}

}
