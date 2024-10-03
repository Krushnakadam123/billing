package com.example.billingaccount.serviceport;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.billingaccount.Dao.Reportexa;


@Service
public class BillingD {

    @Autowired
    private Reportexa reportexa;

    // Constructor for dependency injection
    public BillingD(Reportexa reportexa) {
        this.reportexa = reportexa;
    }

    public List<Map<String, Object>> getReportData(Date formdate, Date toDate,String customerName) throws SQLException {
        List<Map<String, Object>> reportData = new ArrayList<>();

        // Iterate over each date in the range
        for (long currentTime = formdate.getTime(); currentTime <= toDate.getTime(); 
                currentTime += 86400000) { // Increment by one day (86400000 ms)
               Date currentDate = new Date(currentTime);
               
           
               if (customerName != null && !customerName.isEmpty()) {
            	    List<Map<String, Object>> billingDatalList = reportexa.getBillingByname(currentDate,customerName);
            	   Map<String, Object> dailyData = new HashMap<>();
            	
            	  
            	   if (billingDatalList != null && !billingDatalList.isEmpty()) {
            		   dailyData.put("billingData",billingDatalList);
                       
                   }else {
                	   reportData.add(dailyData);
				}
			}else {
               
            // Fetch billing data for the current date
            List<Map<String, Object>> billingDatalList = reportexa.getBillingByDate(currentDate);
          
            // Prepare a new map for each date's data
            Map<String, Object> dailyData = new HashMap<>();
            

            if (billingDatalList != null && !billingDatalList.isEmpty()) {
            	
            	dailyData.put("billingData",billingDatalList); // Add actual billing data if present
            } else {
              //  dailyData.put("billingData", "No data available for this date"); // No data placeholder
            }

            // Add to the final list
            reportData.add(dailyData);

            // Move to the next day
           // currentDate.setTime(currentDate.getTime() + 86400000); // Increment by one day (86400000 ms)
        }
        }
        return reportData;
    }

}
