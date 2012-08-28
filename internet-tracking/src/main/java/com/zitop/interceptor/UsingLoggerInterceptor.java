package com.zitop.interceptor;

import java.util.Date;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.zitop.tracking.entity.UsingLogger;
import com.zitop.util.SystemUtil;

/**
 * 记录操作的时间和人员
 * @author Joseph
 */

@Component
@Aspect
public class UsingLoggerInterceptor {
	
	@Before("execution(* com.zitop.infrastructure.service.impl.AbstractService.update(..)) && args(entity,..)")  
	public void beforeUpdate(Object entity)
	{
		if(entity instanceof UsingLogger)
		{
			UsingLogger logger = (UsingLogger)entity; 
			logger.setModifyTime(new Date());
			logger.setModifier(SystemUtil.getSysUserName());
		}
	}

	@Before("execution(* com.zitop.infrastructure.service.impl.AbstractService.insert(..)) && args(entity,..)")
	public void beforeInsert(Object entity)
	{
		if(entity instanceof UsingLogger)
		{
			UsingLogger logger = (UsingLogger)entity;
			logger.setCreator(SystemUtil.getSysUserName());
			logger.setCreateTime(new Date());
		}
	}
}
