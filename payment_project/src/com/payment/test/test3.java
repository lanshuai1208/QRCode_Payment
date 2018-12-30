package com.payment.test;

public class test3 {
	package com.payment.main;

	import java.awt.image.BufferedImage;
import java.io.File;
	import java.io.IOException;
	import java.io.PrintWriter;
	import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
	import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

	import javax.activation.ActivationDataFlavor;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

	import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.payment.factory.DAOFactory;
	import com.payment.hash.md5;
import com.payment.vo.payhistory;
import com.payment.vo.user;
	import com.payment.main.MatrixToImageWriter;
	import com.payment.main.MatrixToImageConfig;


	public class test3 extends HttpServlet{
		private static final long serialVersionUID = 1L;

		public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
			response.setContentType("text/html");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			int money = Integer.parseInt(request.getParameter("money"));
			String payer = request.getParameter("userid").replaceAll("\r|\n", "");
			System.out.println("payer:" + payer);
			try {
	            MultiFormatReader reader=new MultiFormatReader();
	            File f=new File("D:\\eclipse_x86_workspace\\payment3\\WebContent\\image_accept\\to_admin1.png");
	            BufferedImage image=ImageIO.read(f);
	            BinaryBitmap bb=new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
	            HashMap map =new HashMap();
	            map.put(EncodeHintType.CHARACTER_SET, "utf-8");
	            Result text = reader.decode(bb,map);
	           
	            String result = text.getText();
	            String accepter = result.substring(0,5);//取收款人ID
	            String tString = result.substring(5);//二维码生成时间
	          
	            //判断二维码是否过期
	            long t=System.currentTimeMillis()/(10*60*1000);//以十分钟为单位划分时间
	    		String tNow = Long.toString(t);
	    		
	    		if (!tNow.equals(tString)) {
					out.println("out");
				}
	            //计算收款人的userid
	            try{
	            	accepter = DAOFactory.getUserDAOInstance().findAccepter(accepter);
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	            payhistory ph = new payhistory();
	            user ul1 = new user(),ul2 = new user();
	            ul1.setUsername(payer);
	            ul2.setUsername(accepter);
	            ph.setPayer(payer);ph.setAccepter(accepter);ph.setMoney(money);
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


}
