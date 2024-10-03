package com.example.billingaccount.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class Reportexa {

    public Reportexa() {
        // Initialization if needed (e.g., setting up database connections)
    }

    public List<Map<String, Object>> getBillingByDate(Date date) throws SQLException {
        List<Map<String, Object>> billingDataList = new ArrayList<>();
        Connection conn = null;
        ResultSet resultSet = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sql = "SELECT customer_name, COUNT(customer_name) AS totalBills, SUM(total2) AS total2 " +
                         "FROM Customer " +
                         "WHERE date = ? " +
                         "GROUP BY customer_name";

            PreparedStatement psItem = conn.prepareStatement(sql);
            psItem.setDate(1, date);

            resultSet = psItem.executeQuery();

            // Retrieve data from the result set
            while (resultSet.next()) {
            	String customerName = resultSet.getString("customer_name"); // Correctly fetching customer name
                int totalBills = resultSet.getInt("totalBills"); // Ensure this column is indeed an integer
                double total2 = resultSet.getDouble("total2"); 
               
                Map<String, Object> customerData = new HashMap<>();
                customerData.put("customerName", customerName);
                customerData.put("totalBills", totalBills);
                customerData.put("total2", total2);
                customerData.put("date", date);

                billingDataList.add(customerData);
            }

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
        } finally {
            if (resultSet != null) resultSet.close();
            if (conn != null) conn.close();
        }

        return billingDataList;
    }
    
    public List<Map<String, Object>> getBillingByname(Date date,String customerName) throws SQLException {
        List<Map<String, Object>> billingDataList = new ArrayList<>();
        Connection conn = null;
        ResultSet resultSet = null;
        

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sql = "SELECT customer_name, COUNT(customer_name) AS totalBills, SUM(total2) AS total2 " +
                         "FROM Customer " +
                         "WHERE date = ? AND customer_name = ? " +
                         "GROUP BY customer_name";

            PreparedStatement psItem = conn.prepareStatement(sql);
            psItem.setDate(1, date);
            psItem.setString(2,customerName);
      
            resultSet = psItem.executeQuery();
     
            // Retrieve data from the result set
            while (resultSet.next()) {
            	String customerName1 = resultSet.getString("customer_name"); // Correctly fetching customer name
                int totalBills = resultSet.getInt("totalBills"); // Ensure this column is indeed an integer
                double total2 = resultSet.getDouble("total2"); 
              
                Map<String, Object> customerData = new HashMap<>();
                customerData.put("customerName", customerName1);
                customerData.put("totalBills", totalBills);
                customerData.put("total2", total2);
                customerData.put("date", date);

                billingDataList.add(customerData);
               
            }

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
        } finally {
            if (resultSet != null) resultSet.close();
            if (conn != null) conn.close();
        }

        return billingDataList;
    }
   
}
