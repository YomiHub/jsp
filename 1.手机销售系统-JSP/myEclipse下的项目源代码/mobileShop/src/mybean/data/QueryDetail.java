package mybean.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.utils.JdbcUtil;

public class QueryDetail {
	String backnews;
	public String getBacknews() {
		return backnews;
	}
	public void setBacknews(String backnews) {
		this.backnews = backnews;
	}
	
	public void queryById(String mobileID,String putGoodsPath,String picBasePath) {
		String detail;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;
		
		
    	detail = "<th>��Ʒ��"+mobileID;
    	if(mobileID==null){
    		detail = "û�в�Ʒ�ţ��޷��鿴ϸ��";
    		return;
    	}

		try {
			connection = JdbcUtil.getConnection();
			//3.��ȡstatement
			//String sql ="insert into user(logname,password,phone,address,realname) value(?,?,?,?,?) ";
			String sql ="select * from mobileForm where mobile_version = '"+mobileID+"'";
			prepareStatement = connection.prepareStatement(sql);
			
			//4.ִ��sql
			rs = prepareStatement.executeQuery();
			detail = detail+"<table border=2>"
					+ "<tr>"
					+ "<th>��Ʒ��</th><th>����</th><th>������</th><th>�۸�</th><th><font color=blue>���빺�ﳵ<font></th>"
					+ "</tr>";
			
			String picture = picBasePath+"welcome.jpg";
			String detailMess = "";
			
			//5.�����ѯ���
			while(rs.next()) {
				String number = rs.getString(1);
				String name = rs.getString(2);
				String maker = rs.getString(3);
				String price = rs.getString(4);
				detailMess = rs.getString(5);
				picture = picBasePath+"/"+rs.getString(6);
				
				String goods = "("+number+","+name+","+maker+","+price+")#"+price; //���ڹ��ﳵ����۸�β׺�ϡ�#�۸�ֵ��
				goods = goods.replaceAll("\\p{Blank}","");
				String button = "<form action = '"+putGoodsPath+"' method='post'>"+
						"<input type='hidden' name='java' value="+goods+">"+
						"<input type='submit' value='���빺�ﳵ'></form>";
				detail = detail+"<tr><td>"+number+"</td>"
						+ "<td>"+name+"</td>"
								+ "<td>"+maker+"</td>"
										+ "<td>"+price+"</td>"
												+ "<td>"+button+"</td></tr>";
			}
			
			detail = detail +"</table>��Ʒ���飺<br>"
					+ "<div align=center>"+detailMess+"</div>"
							+ "<img src='"+picture+"' width=260></img>";			
			
			this.backnews = detail;

		} catch (Exception e) {
			detail=""+e.toString();
			this.backnews = detail;
			
			e.printStackTrace();
		}finally {
			//5.�ͷ���Դ connection prepareStatement
			JdbcUtil.close(connection, prepareStatement, rs);
		}
	    
	}
}
