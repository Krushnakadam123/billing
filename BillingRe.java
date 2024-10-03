package com.example.billingaccount.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;



@Repository
public class BillingRe {
	
	  
    public void processBilling0(int billingId,int[] itemId,int customerId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
      
      
       
        
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // Start transaction

           
            
            // Insert into Billing table
            String billingSql = "INSERT INTO Billing (billing_id,customer_id, iditem) VALUES (?,?, ?)";
            ps = conn.prepareStatement(billingSql);
            
            ps.setInt(1,billingId);
            ps.setInt(2, customerId);
           
            ps.executeUpdate();

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    throw new SQLException("Rollback failed", rollbackEx);
                }
            }
            throw e; // Re-throw the original exception
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
