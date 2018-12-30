package com.payment.dao.impl;

import java.lang.ref.PhantomReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.payment.dao.payhistoryDAO;
import com.payment.vo.payhistory;

public class payhistoryDAOImpl implements payhistoryDAO{
	private Connection conn = null;
	private PreparedStatement ps = null;
	
	public payhistoryDAOImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean addHistory(payhistory ph) throws Exception {
		System.out.println("into impl");
		boolean flag = false;
		String sql1 = "insert into payhistory(payer,accepter,money,time) values(?,?,?,?)";
		this.ps = this.conn.prepareStatement(sql1);
		this.ps.setString(1, ph.getPayer());
		this.ps.setString(2, ph.getAccepter());
		this.ps.setInt(3, ph.getMoney());
		this.ps.setTimestamp(4, ph.getTime());
		System.out.println(sql1);
		if(this.ps.executeUpdate()>0){
			flag = true;
		}
		this.ps.close();
		return flag;
		
		
	}

	@Override
	public List<payhistory> findAll(String keyword) throws Exception {
		List<payhistory> all = new ArrayList<payhistory>();
		String sql = "select * from payhistory where payer = ? or accepter = ?";
		this.ps = this.conn.prepareStatement(sql);
		
		this.ps.setString(1, keyword);
		this.ps.setString(2, keyword);
		ResultSet rs = this.ps.executeQuery();
		payhistory ph = null;
		while(rs.next()){
			ph = new payhistory();
			ph.setAccepter(rs.getString("accepter"));
			ph.setPayer(rs.getString("payer"));
			ph.setMoney(rs.getInt("money"));
			ph.setTime(rs.getTimestamp("time"));
			all.add(ph);
		}
		this.ps.close();
		return all;
	}
	
}
