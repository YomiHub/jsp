package myservlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.utils.JdbcUtil;

import mybean.data.Register;

/**
 * Servlet implementation class HandleRegister
 */
@WebServlet("/HandleRegister")
public class HandleRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleRegister() {
        super();
        // TODO Auto-generated constructor stub
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException {
    		response.setContentType("text/html;charset=utf-8");
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            
            //����user��JavaBeanģ��
            Register userBean = new Register();  
            request.setAttribute("userBean", userBean);
    	          
			String logname = request.getParameter("logname").trim();
			String password = request.getParameter("password").trim();
			String again_password = request.getParameter("again_password").trim();
			String phone = request.getParameter("phone").trim();
			String address = request.getParameter("address").trim();
			String realname = request.getParameter("realname").trim();
//			out.print(logname+"--"+password);           
            
			if(logname == null) {
				logname = "";
			}
			
			if(password == null) {
				password = "";
			}
			
			if(!password.equals(again_password)) {
				userBean.setBacknews("�����������벻һ�£�ע��ʧ��");
				/*ת�����ı��ַ���ҿ��Խ����ݱ��浽request*/
				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/inputRegister.jsp");
				dispatcher.forward(request, response);
				return;
				
				/*�ض����ı��ַ����ַ���Ҳ����ܽ����ݱ��浽request*/
//				response.sendRedirect(request.getContextPath()+"/login.jsp");
//				return ;
			}
          
    	 	Connection connection = null;
    		PreparedStatement prepareStatement = null;
    		String backnews="";
    		boolean boo = false;
    		boolean isLeagal = true;
    		for(int i=0; i<logname.length(); i++) {
    			char c = logname.charAt(i);
    			if(!((c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0')))
    				isLeagal = false;
    		}
    		
    		boo = logname.length()>0&&password.length()>0&&isLeagal;
    		
    		try {
    			connection = JdbcUtil.getConnection();
    			//3.��ȡstatement
    			//String sql ="insert into user(logname,password,phone,address,realname) value(?,?,?,?,?) ";
    			String sql ="insert into user values(?,?,?,?,?) ";
    			prepareStatement = connection.prepareStatement(sql);
    			
    			if(boo) {
    				prepareStatement.setString(1, logname);
        			prepareStatement.setString(2, password);
        			prepareStatement.setString(3, phone);
        			prepareStatement.setString(4, address);
        			prepareStatement.setString(5, realname);
        			
        			//4.ִ��sql
        			int m = prepareStatement.executeUpdate();
        			
        			if(m!=0) {
        				backnews = "register succeeded!";
        				userBean.setBacknews(backnews);
        				
        				userBean.setLogname(logname);
        				userBean.setPhone(phone);
        				userBean.setAddress(address);
        				userBean.setRealname(handleString(realname));
        				out.print("register succeeded!");
        			}else {
        				backnews="ע��ʧ��";
        				userBean.setBacknews(backnews);
        			}
    			}else {
    				backnews="��Ϣ��д���������������зǷ��ַ�";
    				userBean.setBacknews(backnews);
    			}
    			
      
    		} catch (Exception e) {
    			backnews="�û�Ա�����ѱ�ʹ�ã������������";
				userBean.setBacknews(backnews);
    			out.print(e.toString()); 
    			e.printStackTrace();
    		}finally {
    			//5.�ͷ���Դ connection prepareStatement
    			JdbcUtil.close(connection, prepareStatement, null);
    			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/inputRegister.jsp");
				dispatcher.forward(request, response);
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

}
