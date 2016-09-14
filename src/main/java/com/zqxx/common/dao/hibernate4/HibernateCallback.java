package com.zqxx.common.dao.hibernate4;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author jinxx1102@starit.com.cn
 *  
 */
public interface HibernateCallback<T> {

	T doInHibernate(Session session) throws HibernateException, SQLException;

}
