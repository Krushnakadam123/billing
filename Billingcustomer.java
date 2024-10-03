package com.example.billingaccount.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.example.billingaccount.Entity.Customer;



@Repository
public class Billingcustomer {

	public  void processCustomer( int customerId, String customerName,Date date, double total2) throws SQLException {
		
		 Connection conn = null;
	        PreparedStatement ps = null;
	      
	      

	        try {
	            conn = DBUtil.getConnection();
	            conn.setAutoCommit(false); // Start transaction

	           
	            // Insert into Customer table
	            String customerSql = "INSERT INTO Customer (customer_id,customer_name, date, total2) VALUES (?,?, ?, ?)";
	            ps = conn.prepareStatement(customerSql);
	            ps.setInt(1, customerId);
	            ps.setString(2, customerName);
	            ps.setDate(3, date);
	            ps.setDouble(4, total2);
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
	            throw e;
	            
	        }

	}
	
	
	//using select query form database

	public static Customer billingcustomer(int customerId) throws SQLException {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Customer customer = null;

	    try {
	        conn = DBUtil.getConnection(); // Get a connection to the database

	        // Create the SQL SELECT query to fetch customer details
	        String selectSql = "SELECT customer_id, customer_name, date, total2 FROM Customer WHERE customer_id = ?";

	        // Prepare the statement
	        ps = conn.prepareStatement(selectSql);
	        ps.setInt(1, customerId); // Set the customer ID parameter

	        // Execute the query and get the result set
	        rs = ps.executeQuery();

	        // If the result set contains data, process it
	        if (rs.next()) {
	            customer = new Customer(); // Assuming you have a Customer model class
	            customer.setCustomerId(rs.getInt("customer_id"));
	            customer.setCustomerName(rs.getString("customer_name"));
	            customer.setDate(rs.getDate("date"));
	            customer.setTotal(rs.getDouble("total2"));
	        }

	    } finally {
	        // Close resources
	        if (rs != null) rs.close();
	        if (ps != null) ps.close();
	        if (conn != null) conn.close();
	    }

	    return customer; // Return the customer object
	}

	
}

