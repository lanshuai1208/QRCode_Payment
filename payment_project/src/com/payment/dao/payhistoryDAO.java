package com.payment.dao;

import java.util.List;

import com.payment.vo.payhistory;
import com.payment.vo.payhistory;

public interface payhistoryDAO {
	public boolean addHistory(payhistory ph) throws Exception;
	
	public List<payhistory> findAll(String keyword) throws Exception;
}
