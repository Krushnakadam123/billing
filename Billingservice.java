package com.example.billingaccount.Dao;

import java.sql.Connection;


import java.sql.SQLException;
import org.springframework.stereotype.Repository;




import java.sql.PreparedStatement;


@Repository
public class Billingservice {

	public void processBilling(int itemId,double price, String itemName, double discount, int quantity, double total) throws SQLException {
		// TODO Auto-generated method stub
		
		  Connection conn = null;
	     
	      
	      
	        try {
	            conn = DBUtil.getConnection();
	            conn.setAutoCommit(false);
	        PreparedStatement psItem = null;
        String itemsql1= "INSERT INTO Item ( iditem,item_name,price,discount,quantity,total) VALUES (?,?,?,?,?,?)";
        psItem=conn.prepareStatement(itemsql1);
        
        
        psItem.setInt(1, itemId);
        psItem.setString(2, itemName);
        psItem.setDouble(3, price);
        psItem.setDouble(4, discount);
        psItem.setInt(5, quantity);
        psItem.setDouble(6, total);
       
       
        psItem.executeUpdate();
        conn.commit(); 
	} catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback(); // Rollback transaction on error
            } catch (SQLException rollbackEx) {
                throw new SQLException("Rollback failed", rollbackEx);
            }
        }
        throw e; // Re-throw the original exception
    }

	
	}

	

	
	
}
