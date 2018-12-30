package com.payment.dao.proxy;

import java.util.List;

import com.payment.dao.payhistoryDAO;
import com.payment.dao.impl.payhistoryDAOImpl;
import com.payment.dbc.DatabaseConnection;
import com.payment.vo.payhistory;

public class payhistoryDAOProxy implements payhistoryDAO{
	private DatabaseConnection dbc = null;
	private payhistoryDAO dao = null;
	public payhistoryDAOProxy() throws Exception{
		this.dbc = new DatabaseConnection();
		this.dao = new payhistoryDAOImpl(this.dbc.getConnection());
	}
	@Override
	public boolean addHistory(payhistory ph) throws Exception {
		boolean flag = false;
		try{
			flag = this.dao.addHistory(ph);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.close();
		}
		return flag;
	}
	@Override
	public List<payhistory> findAll(String keyword) throws Exception {
		List<payhistory> all = null;
		try{
			all = this.dao.findAll(keyword);
		}catch(Exception e){
			throw e;
		}finally {
			this.dbc.close();
		}
		return all;
	}
}
