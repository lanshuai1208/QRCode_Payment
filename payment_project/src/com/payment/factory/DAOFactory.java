package com.payment.factory;

import com.payment.dao.UserDAO;
import com.payment.dao.payhistoryDAO;
import com.payment.dao.proxy.UserDAOProxy;
import com.payment.dao.proxy.payhistoryDAOProxy;

public class DAOFactory {
	public static UserDAO getUserDAOInstance() throws Exception{
		return new UserDAOProxy();
	}
	public static payhistoryDAO getPayhistoryDAOInstance() throws Exception{
		return new payhistoryDAOProxy();
	}
}
