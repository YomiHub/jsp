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
 * Servlet implementation class HandleChangePass
 */
@WebServlet("/HandleChangePass")
public class HandleChangePass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleChangePass() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
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
       
	          
		String oldPassword = request.getParameter("oldPassword").trim();
		String newPassword = request.getParameter("newPassword").trim();
	          
        
		if(oldPassword == null) {
			oldPassword = "";
		}
		
		if(newPassword == null) {
			newPassword = "";
		}
		
		Login loginBean = null;
		HttpSession session = request.getSession(true);
	 	Connection connection = null;
		PreparedStatement prepareStatement = null;
		PreparedStatement prepareStatement2 = null;
		ResultSet rs = null;
		ResultSet rs2 =null;
		
		String backnews="";
		String logname = "";
		boolean boo = false;
		
		boo = oldPassword.length()>0&&newPassword.length()>0;
		
		try {
			connection = JdbcUtil.getConnection();
			loginBean = (Login)session.getAttribute("loginBean");
			logname = loginBean.getLogname();
			//3.��ȡstatement
			//String sql ="insert into user(logname,password,phone,address,realname) value(?,?,?,?,?) ";
			String sql ="select * from user where logname=? and password=?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, logname);
			prepareStatement.setString(2, oldPassword);
			
			if(boo) {
    			//4.ִ��sql
    			rs = prepareStatement.executeQuery();
    			boolean m = rs.next();
    			
    			if(m==true) {
    				//��ѯ�ɹ�
    				String changeSql = "update user set password=? where logname=?";
    				prepareStatement2 = connection.prepareStatement(changeSql);
    				prepareStatement2.setString(1, newPassword);
    				prepareStatement2.setString(2, logname);
    				
    			    prepareStatement2.execute();
    				backnews="�����޸ĳɹ�";
    				
    			}else {
    				backnews="���������������";
    			}
			}else {
				backnews="�������û���������";
			}
			loginBean.setChangePassBacknews(backnews);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/changePassword.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			backnews="�޸�ʧ��"+e.toString();
			out.print(e.toString()); 
			e.printStackTrace();
		
		}finally {
			//5.�ͷ���Դ connection prepareStatement
			JdbcUtil.close(connection, prepareStatement, rs);
			JdbcUtil.close(connection, prepareStatement2, rs2);
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
