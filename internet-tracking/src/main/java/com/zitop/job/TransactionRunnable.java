package com.zitop.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.zitop.infrastructure.util.SystemContext;

public abstract class TransactionRunnable implements Runnable
{
	private final static Log log = LogFactory.getLog(TransactionalQuartzTask.class);

	public void run()
	{
		SessionFactory hibernateSessionFactory = (SessionFactory) SystemContext.getApplicationContext().getBean(
				"sessionFactory");

		Session session = SessionFactoryUtils.getSession(hibernateSessionFactory, true);
		boolean existingTransaction = SessionFactoryUtils.isSessionTransactional(session, hibernateSessionFactory);

		if (existingTransaction)
		{
			log.debug("Found thread-bound Session for TransactionRunnable");
		} else
		{
			TransactionSynchronizationManager.bindResource(hibernateSessionFactory, new SessionHolder(session));
		}

		try
		{
			executeTransactional();
		} catch (HibernateException ex)
		{
			throw ex;
		} finally
		{
			if (existingTransaction)
			{
				log.debug("Not closing pre-bound Hibernate Session after TransactionRunnable");
			} else
			{
				TransactionSynchronizationManager.unbindResource(hibernateSessionFactory);
				SessionFactoryUtils.releaseSession(session, hibernateSessionFactory);
			}
		}
	}

	/**
	 * 实现实际的Job任务
	 */
	protected abstract void executeTransactional();

}
