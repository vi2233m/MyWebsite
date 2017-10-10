package com.jdbctest;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.jdbctest.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        SqlCl db = SqlCl.getInstance();
        db.getConnection();
        String sql = "select user_id,user_password,user_name,user_email from user";
        List<User> reslist = new ArrayList<User>();
        List<Object> list = new ArrayList<Object>();
        //list.add(2014303342);

        try {
            reslist = db.executeQueryByRef(sql,list,User.class);
            
            for(User ac:reslist){
                System.out.println("----->"+ ac.toString());
                
                System.out.println(ac.getId());
                System.out.println(ac.getUser_name());
            }
            
        
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            db.close(db.rs, db.ps, db.ct);
        }

    }
	

}
