package com.payment.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.payment.factory.DAOFactory;
import com.payment.vo.payhistory;
import com.payment.vo.user;

@WebServlet("/upload")
public class Upload extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		int money = Integer.parseInt(request.getParameter("money"));
		String payer = request.getParameter("userid").replaceAll("\r|\n", "");
		System.out.println("payer:" + payer);
		try {
            MultiFormatReader reader=new MultiFormatReader();
            File f=new File("D:\\eclipse_x86_workspace\\payment3\\WebContent\\image_accept\\to_admin.png");
            BufferedImage image=ImageIO.read(f);
            BinaryBitmap bb=new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            HashMap map =new HashMap();
            map.put(EncodeHintType.CHARACTER_SET, "utf-8");
            Result text = reader.decode(bb,map);
            System.out.println("解析结果："+text.toString());
            System.out.println("二维码格式类型："+text.getBarcodeFormat());
            System.out.println("二维码文本内容："+text.getText());
            String result = text.getText();
            String accepter = result.substring(0,5);//取收款人ID
            String tString = result.substring(5);//二维码生成时间
            System.out.println("hashed accepter:" + accepter);
            System.out.println("tString:" + tString);
            //判断二维码是否过期
            long t=System.currentTimeMillis()/(10*60*1000);//以十分钟为单位划分时间
    		String tNow = Long.toString(t);
    		
    		if (!tNow.equals(tString)) {
				out.println("out");
			}
            //开始计算收款人的userid
            try{
            	accepter = DAOFactory.getUserDAOInstance().findAccepter(accepter);
            }catch(Exception e){
            	e.printStackTrace();
            }
            
            System.out.println("after accepter:" + accepter);
            
            payhistory ph = new payhistory();
            user ul1 = new user(),ul2 = new user();
            
            System.out.println("payer:" + payer);
            System.out.println("accepter:" + accepter);
            System.out.println("money" + money);
            System.out.println("time:" + System.currentTimeMillis());
            ul1.setUsername(payer);
            ul2.setUsername(accepter);
            ph.setPayer(payer);
            ph.setAccepter(accepter);
            ph.setMoney(money);
            ph.setTime(new Timestamp(System.currentTimeMillis()));
            try{
            	//付款人减去金额
    			DAOFactory.getUserDAOInstance().pay(ul1, money);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
            try{
            	//收款人加上金额
    			DAOFactory.getUserDAOInstance().accept(ul2, money);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
            try{
            	//记录交易信息
    			DAOFactory.getPayhistoryDAOInstance().addHistory(ph);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
            
            
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}
}
