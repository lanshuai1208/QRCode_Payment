package com.payment.dao;

import com.payment.vo.user;
import com.payment.vo.payhistory;

public interface UserDAO {
	public boolean register(user ul) throws Exception;
	
	public boolean login(user ul) throws Exception;
	
	public boolean pay(user ul,double money) throws Exception;
	
	public boolean accept(user ul,double money) throws Exception;
	
	public double searchBalance(user ul) throws Exception;
	
	public String findAccepter(String hashedAccepter) throws Exception;
}
