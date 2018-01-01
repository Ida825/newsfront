package cn.et.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DbUtils {
	//����Properties�������洢�����ļ���Ϣ
	static Properties p = new Properties();
	static{
		InputStream is = DbUtils.class.getResourceAsStream("/jdbc.properties");
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();  
		}
	}
	
	public static Connection getConnection() throws SQLException{
		String url = p.getProperty("url");
		String driverClass = p.getProperty("driverClass");
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String username = p.getProperty("username");
		String password = p.getProperty("password");
		Connection con = DriverManager.getConnection(url, username, password);
		return con;
	}
	
	public static List<Map> query(String sql) throws SQLException{
		//��ȡ���ݿ�����
		Connection con = getConnection();
		//Ԥ����SQL���
		PreparedStatement ps = con.prepareStatement(sql);
		//��ȡ��ѯ�����
		ResultSet rs = ps.executeQuery();
		//��ȡ������
		ResultSetMetaData rsm = rs.getMetaData();	
		int count = rsm.getColumnCount();
		
		//����List���ϴ洢��ѯ������
		List list = new ArrayList();
		while(rs.next()){
			Map map = new HashMap();
			for(int i=1;i<=count;i++){
				String key = rsm.getColumnName(i);
				String value = rs.getString(i);
				map.put(key, value);
			}			
			list.add(map);
		}
		con.close();
		ps.close();
		rs.close();
		return list;
	}
	/*public static void main(String[] args) throws SQLException {
		List<Map> list = query("select * from cc");
		System.out.println(list);
	}*/
	
	
	/**
	 * ִ����ɾ��sql���ķ���
	 * @param sql
	 * @return
	 * @throws SQLException 
	 */
	public static int execute(String sql) throws SQLException{
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		int i = ps.executeUpdate();
		return i;
	}
	

}
