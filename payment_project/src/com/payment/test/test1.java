package com.payment.test;

public class test1 {
	public static void main(String[] args) {
		long t1=System.currentTimeMillis()/(10*60*1000);//以十分钟为单位划分时间
		String tString = Long.toString(t1);
		System.out.println(t1);
		System.out.println(tString);
	}
}
