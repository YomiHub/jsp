package org.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {
	private static String url ="jdbc:mysql://127.0.0.1/mobileshop?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true";
	private static String username="root";
	private static String password="";
	private static String driverName="com.mysql.jdbc.Driver";
	
	// static��̬�����������룬��������ص��ڴ��ʱ������ִ��
	static {
		//1.ע������
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ����
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		//2.��ȡ����
		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}
	
	
	public static void close(Connection connection,PreparedStatement prepareStatement,ResultSet resultSet) {
		//5.�ͷ���Դ connection prepareStatement resultSet
		if(resultSet!=null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(prepareStatement!=null) {
			try {
				prepareStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void statementClose(Connection connection,Statement statement,ResultSet resultSet) {
		//5.�ͷ���Դ connection prepareStatement resultSet
		if(resultSet!=null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(statement!=null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
