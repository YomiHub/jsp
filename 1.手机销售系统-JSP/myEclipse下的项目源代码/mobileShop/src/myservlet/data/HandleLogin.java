package myservlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.utils.JdbcUtil;

import mybean.data.Login;

/**
 * Servlet implementation class HandleLogin
 */
@WebServlet("/HandleLogin")
public class HandleLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}
	
	 //���������ַ���
    public String handleString(String s) {
    	try {
    		byte bb[]=s.getBytes("ISO8859-1");
    		s=new String(bb,"UTF-8");
    	}catch(Exception e){}
    	
    	return s;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
       
	          
		String logname = request.getParameter("logname").trim();
		String password = request.getParameter("password").trim();
		logname = handleString(logname);
		password = handleString(password);
	          
        
		if(logname == null) {
			logname = "";
		}
		
		if(password == null) {
			password = "";
		}
      
	 	Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;
		String backnews="";
		boolean boo = false;
		
		boo = logname.length()>0&&password.length()>0;
		
		try {
			connection = JdbcUtil.getConnection();
			//3.��ȡstatement
			//String sql ="insert into user(logname,password,phone,address,realname) value(?,?,?,?,?) ";
			String sql ="select * from user where logname=? and password=?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, logname);
			prepareStatement.setString(2, password);
			
			if(boo) {
    			//4.ִ��sql
    			rs = prepareStatement.executeQuery();
    			boolean m = rs.next();
    			
    			if(m==true) {
    				//��¼�ɹ�
    				success(request,response,logname,password);
    				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/login.jsp");
    				dispatcher.forward(request, response);
    			}else {
    				backnews="��������û�������������";
    				fail(request,response,logname,backnews);
    			}
			}else {
				backnews="�������û���������";
				fail(request,response,logname,backnews);
			}
			
  
		} catch (Exception e) {
			backnews=""+e.toString();
			out.print(e.toString()); 
			e.printStackTrace();
			fail(request,response,logname,backnews);
		}finally {
			//5.�ͷ���Դ connection prepareStatement
			JdbcUtil.close(connection, prepareStatement, rs);
		}
	     out.flush();
	     out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void success(HttpServletRequest request, HttpServletResponse response,String logname,String password) {
		Login loginBean = null;
		HttpSession session = request.getSession(true);
		try {
			//����user��JavaBeanģ��
	        loginBean = (Login)session.getAttribute("loginBean");
	        if(loginBean == null) {
	        	loginBean = new Login();   //��δ��¼�������µ�ģ��
	        	session.setAttribute("loginBean", loginBean);
	        	loginBean = (Login)session.getAttribute("loginBean");
	        }
	        String name = loginBean.getLogname();
	        if(name.equals(logname)) {
	        	loginBean.setBacknews(logname+"�Ѿ���¼��!");
	        	loginBean.setLogname(logname);
	        }else {
	        	loginBean.setBacknews(logname+"��¼�ɹ�!");
	        	loginBean.setLogname(logname);
	        }
			 
		}catch(Exception e) {
			loginBean = new Login();   //��δ��¼�������µ�ģ��
        	session.setAttribute("loginBean", loginBean);
        	loginBean.setBacknews(logname+"��¼�ɹ�!"+e.toString());
        	loginBean.setLogname(logname);
		}
	}
	
	public void fail(HttpServletRequest request, HttpServletResponse response,String logname,String backnews) {
		response.setContentType("text/html;charset=utf-8");
		try {
			 PrintWriter out = response.getWriter();
			 String loginPath = request.getContextPath()+"/pages/login.jsp";
			 String registerPath = request.getContextPath()+"/index.jsp";
			 out.println("<html><body><div align=center>");
			 out.println("<h2>"+logname+"��¼�������<br>"+backnews+"</h2>");
			 out.println("���ص�¼ҳ�����ҳ<br>");
			 out.println("<a href="+loginPath+">��¼ҳ��</a>");
			 out.println("<a href="+registerPath+">��¼ҳ��</a>");
			 out.println("</div></body></html>");
		}catch(IOException e) {
			Login loginBean = null;
			HttpSession session = request.getSession(true);
			loginBean = (Login)session.getAttribute("loginBean");
			loginBean.setBacknews("�׳��쳣"+e.toString());
		}
	}

}
