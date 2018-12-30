package com.payment.dao.impl;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.xml.soap.SAAJMetaFactory;

import org.apache.tomcat.util.log.SystemLogHandler;

import com.payment.dao.UserDAO;
import com.payment.hash.md5;
import com.payment.vo.user;

public class UserDAOImpl implements UserDAO{
	private Connection conn = null;
	private PreparedStatement ps = null;
	public UserDAOImpl(Connection conn) {
		this.conn = conn;
	}
	@Override
	public boolean register(user ul) throws Exception {
		System.out.println("into impl");
		boolean flag = false;
		String sql1 = "insert into user(userid,password,money) values(?,?,10000)";
		this.ps = this.conn.prepareStatement(sql1);
		this.ps.setString(1, ul.getUsername());
		this.ps.setString(2, ul.getPassword());
		System.out.println(sql1);
		if(this.ps.executeUpdate()>0){
			flag = true;
		}
		this.ps.close();
		return flag;
	}

	@Override
	public boolean login(user ul) throws Exception {
		boolean flag = false;
		String sql = "select * from user where userid = ? and password = ?";
		this.ps = this.conn.prepareStatement(sql);
		this.ps.setString(1, ul.getUsername());
		this.ps.setString(2, ul.getPassword());
		System.out.println(sql);
		ResultSet rs = this.ps.executeQuery();
		if(rs.next()){
			flag = true;
		}
		this.ps.close();
		return flag;
	}
	@Override
	//付款人减去交易金额
	public boolean pay(user ul,double money) throws Exception {
		boolean flag = false;
		String sql = "update user set balance = balance - ? where userid = ? ";
		this.ps = this.conn.prepareStatement(sql);
		this.ps.setDouble(1, money);
		this.ps.setString(2, ul.getUsername());
		System.out.println("ul.getBalance() - money:" + (ul.getBalance() - money));
		System.out.println("ul.getUsername():" + ul.getUsername());
		System.out.println(sql);
		this.ps.executeUpdate();
		this.ps.close();
		return flag;
	}
	@Override
	//收款人加上交易金额
	public boolean accept(user ul, double money) throws Exception {
		boolean flag = false;
		String sql = "update user set balance = balance + ? where userid = ? ";
		this.ps = this.conn.prepareStatement(sql);
		this.ps.setDouble(1, money);
		this.ps.setString(2, ul.getUsername());
		System.out.println(sql);
		this.ps.executeUpdate();
		this.ps.close();
		return flag;
	}
	
	@Override
	public double searchBalance(user ul) throws Exception {
		double balance = 0;
		String sql = "select * from user where userid = ? ";
		this.ps = this.conn.prepareStatement(sql);
		String userid = ul.getUsername();
		//System.out.println("ul.getUsername():" + ul.getUsername());
		this.ps.setString(1, ul.getUsername().replaceAll("\n|\r", ""));
		ResultSet rs = this.ps.executeQuery();
		while(rs.next()){
			System.out.println("i am here");
			balance = rs.getDouble("balance");
		}
		this.ps.close();
		return balance;

	}
	boolean equal(String a,String b){
		int i = 0;
		boolean flag = true;
		for(i=0;i<5;i++){
			if(a.charAt(i)!=b.charAt(i)){
				flag = false;
			}
		}
		return flag;
	}
	@Override
	//计算收款人ID
	public String findAccepter(String hashedAccepter) throws Exception {
		String sql = "select * from user";
		this.ps = this.conn.prepareStatement(sql);
		ResultSet rs = this.ps.executeQuery();
		String accepter = null;
		while(rs.next()){
			String tempid = rs.getString("userid");
			String hashed = new md5().hashCode(tempid).substring(0, 5);
			//System.out.println(tempid+"->"+hashed+" "+equal(hashed, hashedAccepter));
			if(equal(hashed, hashedAccepter)){
				accepter = tempid;
			}
		}
		System.out.println("found accepter :" + accepter);
		return accepter;
	}

}
