package com.payment.dao.proxy;

import com.payment.dao.UserDAO;
import com.payment.dao.impl.UserDAOImpl;
import com.payment.dbc.DatabaseConnection;
import com.payment.vo.user;

public class UserDAOProxy implements UserDAO {
	private DatabaseConnection dbc = null;
	private UserDAO dao = null;

	public UserDAOProxy() throws Exception {
		this.dbc = new DatabaseConnection();
		this.dao = new UserDAOImpl(this.dbc.getConnection());
	}

	@Override
	public boolean register(user ul) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			flag = this.dao.register(ul);
		} catch (Exception e) {
			throw e;
		} finally {
			this.dbc.close();
		}
		return flag;
	}

	@Override
	public boolean login(user ul) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			flag = this.dao.login(ul);
		} catch (Exception e) {
			throw e;
		} finally {
			this.dbc.close();
		}
		return flag;
	}

	@Override
	public boolean pay(user ul, double money) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			flag = this.dao.pay(ul, money);
		} catch (Exception e) {
			throw e;
		} finally {
			this.dbc.close();
		}
		return flag;
	}

	@Override
	public boolean accept(user ul, double money) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			flag = this.dao.accept(ul, money);
		} catch (Exception e) {
			throw e;
		} finally {
			this.dbc.close();
		}
		return flag;
	}

	@Override
	public double searchBalance(user ul) throws Exception {
		double balance;
		try{
			balance = this.dao.searchBalance(ul);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.close();
		}
		return balance;
	}

	@Override
	public String findAccepter(String hashedAccepter) throws Exception {
		String accepter;
		try{
			accepter = this.dao.findAccepter(hashedAccepter);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.close();
		}
		return accepter;
	}
}
