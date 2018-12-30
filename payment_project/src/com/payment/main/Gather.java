package com.payment.main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.activation.ActivationDataFlavor;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.payment.factory.DAOFactory;
import com.payment.hash.md5;
import com.payment.vo.user;
import com.payment.main.MatrixToImageWriter;
import com.payment.main.MatrixToImageConfig;


public class Gather extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String userid = request.getParameter("userid"); 
		String money = request.getParameter("money"); 
		System.out.println("useid:" + userid.replaceAll("\n|\r", ""));
		String hash_userid = new md5().hashCode(userid.replaceAll("\n|\r", "")).substring(0, 5);
		System.out.println("hash:" + hash_userid);
		System.out.println("money:" + money);
		
		//获取当前时间
		long t=System.currentTimeMillis()/(2*60*1000);//以十分钟为单位划分时间
		String tString = Long.toString(t);
		
		String text = hash_userid + tString;//二维码的内容
		text = text.replaceAll("\r|\n", "");
        int width = 400;
        int height = 400;
        String format = "png";
        Hashtable hints= new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        
        BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuffer buf = new StringBuffer();
		buf.append("D:\\eclipse_x86_workspace\\payment3\\WebContent\\image\\to_");
		buf.append(userid);
		//buf.append("_");
		//buf.append(aDate.getTime()/(120*1000));
		buf.append(".png");
		String pathname = buf.toString();
		System.out.println(pathname);
		pathname = pathname.replaceAll("\r|\n", "");//去掉可能出现的回车和换行
		System.out.println("换行第一次出现的地方："+pathname.indexOf("\n"));
        File outputFile = new File(pathname);
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile); 
        String result = pathname.substring(45);
        System.out.println(result);
        out.println(result);
        System.out.println("It is ok!");
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}

