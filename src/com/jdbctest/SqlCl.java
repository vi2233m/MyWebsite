package com.jdbctest;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

public class SqlCl {
	

	//�Ĵ���
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/test";
	String username = "root";
	String password = "123456";
	//������
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	private static SqlCl per = null;
	//�������ݿ�
	public Connection getConnection(){
//		try {
//			Class.forName(driver);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			ct = (Connection) DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ct;
	}
	
	//�ر����ݿ�
	public void close(ResultSet rs,PreparedStatement ps,Connection ct){
		if (rs != null){
			try {
				ct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rs = null;
		if(ps != null){
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ps = null;
		
		if (ct != null){
			try {
				ct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ct = null;
	}
	
    /**
    * ִ�в�ѯ
    *
    * @param sql
    *            �����Ԥ��� sql���
    * @param params
    *            �ʺŲ����б�
    * @return ��ѯ��Ľ��
    */
	public List<Map <String, Object>> execQuery(String sql,Object []params){
		
		//��ȡ����
		try{
			this.getConnection();
			ps = (PreparedStatement) ct.prepareStatement(sql);
			
			if (params != null){
				for(int i = 0; i< params.length; i++){
					ps.setObject(i+1, params[i]+"");
				}
			}
			  					
			rs = ps.executeQuery();
			
			List<Map<String, Object>> al = new ArrayList<Map<String, Object>>();
			// ��ý����Ԫ���ݣ�Ԫ���ݾ����������ݵ����ݣ�����ѱ����������������Ϊ���ݣ�
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			// ����е�����
			int columnCount = rsmd.getColumnCount();			
			//���������
			while(rs.next()){
				Map<String, Object> hm = new HashMap<String, Object>();
				for(int i=0;i<columnCount;i++){
					// ����������ȡ��ÿһ�е�����,������1��ʼ
					String columnName =rsmd.getColumnName(i+1);
					// �������������ֵ
					Object columnValue = rs.getObject(columnName);
					// ��������Ϊkey����ֵ��Ϊֵ������ hm�У�ÿ�� hm�൱��һ����¼
					hm.put(columnName, columnValue);
				}
				// ��ÿ�� hm��ӵ�al��, al�൱����������ÿ�� hm�������һ����¼
				al.add(hm);
			}
			return al;
			
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				this.close(rs,ps,ct);
			}
		
		return null;		
	}
	
	
    /**
     * jdbc�ķ�װ�����÷����������װ,�Ѵ����ݿ��л�ȡ�����ݷ�װ��һ����Ķ�����
     * 
     * @param sql
     * @param params
     * @param cls
     * @return
     * @throws Exception
     */
    public <T> List<T> executeQueryByRef(String sql, List<Object> params,
            Class<T> cls) throws Exception {
        List<T> list = new ArrayList<T>();
        int index = 1;
        ps = (PreparedStatement) ct.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(index++, params.get(i));
            }
        }
        rs = ps.executeQuery();
        ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (rs.next()) {
            T resultObject = cls.newInstance();  // ͨ��������ƴ���ʵ��
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = rs.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); // ��javabean�ķ���privateȨ��
                field.set(resultObject, cols_value);
            }
            list.add(resultObject);
        }
        return list;

    }
	
	
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//		//User user = new User();
//		SqlCl sqlcl = new SqlCl();
//		
//		String sql = "select * from user limit 1";
//		List<Map<String,Object>> list = sqlcl.execQuery(sql, null);
//
//	    List<User> user = new ArrayList<User>();
//	    for (Map<String, Object> map : list) {
//	        User s = new User();
//	        for (Map.Entry<String, Object> entry : map.entrySet()) {
//	            try {
//					User.class.getField(entry.getKey()).set(s, entry.getValue());
//				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
//						| SecurityException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	        }
//	       
//	    }
//
//		for (int i = 0; i < user.size(); i++){
//		System.out.println(user);
//		
//		}
//	}


    public static SqlCl getInstance() {
        if (per == null) {
            per = new SqlCl();
            per.registeredDriver();
        }
        return per;
    }
    
    private void registeredDriver() {
        // TODO Auto-generated method stub
        try {
            Class.forName(driver);
            System.out.println("ע�������ɹ���");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
