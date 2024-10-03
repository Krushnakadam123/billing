package com.example.billingaccount.controller;


import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.billingaccount.Dao.Billingcustomer;
import com.example.billingaccount.Dao.Billingservice;
import com.example.billingaccount.serviceport.BillingD;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import ch.qos.logback.core.joran.action.Action;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;





@Controller
public class BillingaccountControlller {
	@Autowired
	private Billingservice billingservice;
	@Autowired
	private Billingcustomer processcustomer;
	@Autowired
	private  BillingD billingSer;

	@PostMapping("/billing")
	public String handleBilling(@RequestParam("customer_name") String customerName,
			@RequestParam("customer_id") int customerId, @RequestParam("date") Date date,
			@RequestParam(value = "total", defaultValue = "0.0") double[] total,
			@RequestParam(value = "price", defaultValue = "0.0") double[] price,
			@RequestParam("item_name") String[] itemName, @RequestParam("iditem") int[] itemId,
			@RequestParam(value = "total2", defaultValue = "0.0") double total2,
			@RequestParam(value = "discount", defaultValue = "0.0") double[] discount,
			@RequestParam(value = "quantity", defaultValue = "0.0") int[] quantity,
			// @RequestParam("billing_id") int billingId
			Model model) {

		try {

			for (int i = 0; i < itemId.length; i++) {
				billingservice.processBilling(itemId[i], price[i], itemName[i], discount[i], quantity[i], total[i]);
			}

			// biilingsample.processBilling0(billingId,itemId, customerId);

			processcustomer.processCustomer(customerId, customerName, date, total2);

			// List<Customer> customer=(List<Customer>)
			// Billingcustomer.billingcustomer(customerId);
			model.addAttribute("customerName", customerName);
			model.addAttribute("customerId", customerId);
			model.addAttribute("date", date);
			model.addAttribute("total2", total2);

			System.out.println("Billing Information Submitted Successfully!");
			model.addAttribute("reportDataList1", "success");

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "There was an error processing the billing.");
			return "error";
		}
	}

	@GetMapping("/billing")
	public void handlebilling2(@RequestParam("customer_name") String customerName,
			@RequestParam("customer_id") int customerId, @RequestParam("date") Date date,
			@RequestParam(value = "total", defaultValue = "0.0") double[] total,
			@RequestParam(value = "price", defaultValue = "0.0") double[] price,
			@RequestParam("item_name") String[] itemName, @RequestParam("iditem") int[] itemId,
			@RequestParam(value = "total2", defaultValue = "0.0") double total2,
			@RequestParam(value = "discount", defaultValue = "0.0") double[] discount,
			@RequestParam(value = "quantity", defaultValue = "0.0") int[] quantity, Model model) throws SQLException {

		 Billingcustomer.billingcustomer(customerId);
	}

	// Constructor for dependency injection
	@Autowired
	public void Billingcontroller(BillingD billingSer) {
		this.billingSer = billingSer;
	}

	
	@GetMapping("/generateReport")
	public String generateReport(
			@RequestParam("formdate") Date formdate,
			@RequestParam("todate") Date toDate,
			@RequestParam(value = "customer_name", required = false) String customerName,
			Model model) throws SQLException {
		
			
		    try {
		        // Fetch report data from the service
		    	 List<Map<String, Object>> reportDataList = billingSer.getReportData(formdate, toDate,customerName);

		    	 model.addAttribute("formdate", formdate);
		    	 model.addAttribute("todate", toDate);
		    	 model.addAttribute("reportDataList",reportDataList);
		    	  
		    	  
		    	  System.out.print( reportDataList);
		    	  
		    	
		    	 
		    	  
		    	  
		        return "report"; // View name for successful data retrieval
		       

		    } 
		    catch (SQLException e) {
		        e.printStackTrace();
		       
		        model.addAttribute("errorMessage", "Error fetching report data.");
		       return "error"; // View name for SQL error

		    } catch (Exception e) {
		        e.printStackTrace();
		        
		        model.addAttribute("errorMessage", "An unexpected error occurred.");
		        return"error"; // View name for other errors
		    
		
	}
	}
	
	
	@GetMapping("/generateReport2")
	public String generateExcel(
			@RequestParam("formdate") Date formdate,
			@RequestParam("todate") Date toDate,
			@RequestParam(value = "customer_name", required = false) String customerName,
			HttpServletResponse response,
			Model model) throws SQLException, IOException {
	    	

	    try {
	        // Fetch report data from the service
	    	 List<Map<String, Object>> reportDataList = billingSer.getReportData(formdate, toDate,customerName);

	    	 //model.addAttribute("formdate", formdate);
	    	// model.addAttribute("todate", toDate);
	    	 //model.addAttribute("reportDataList",reportDataList);
	    	  
	    	  
	    	  System.out.print( reportDataList);
	    	  
	    	
	    
	         Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("billing_report");
	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("Customer Name");
	        headerRow.createCell(1).setCellValue("No of Bills");
	        headerRow.createCell(2).setCellValue("Total Amount");
	        headerRow.createCell(3).setCellValue("Date");

	        int rowNum = 1;
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        for (Map<String, Object> customerData : reportDataList) {
	            List<Map<String, Object>> billingData = (List<Map<String, Object>>) customerData.get("billingData");
	            if (billingData != null) {
	                for (Map<String, Object> billing : billingData) {
	                    Row row = sheet.createRow(rowNum++);
	                    row.createCell(0).setCellValue((String) billing.get("customerName"));
	                    row.createCell(1).setCellValue((Integer) billing.get("totalBills"));
	                    row.createCell(2).setCellValue((Double) billing.get("total2"));
	                    Date billingDate = (Date) billing.get("date");
	                    if (billingDate != null) {
	                        row.createCell(3).setCellValue(dateFormat.format(billingDate)); // Format date as String
	                    } else {
	                        row.createCell(3).setCellValue(""); // Handle null case
	                    }
	                }
	            }
	        }

	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=billing_report.xlsx");

	        workbook.write(response.getOutputStream());
	        
	        System.out.println("downloaded");
	        return "excel";
	    }finally {
			System.err.println("error");
		}
	   
	}


	
//	  public static void main(String[] args) throws SQLException {
//	  
//	 // Convert string dates to java.sql.Date Date fromDate =
//	  Date fromDate=Date.valueOf("2024-09-11"); 
//	  Date toDate = Date.valueOf("2024-09-16"); //
//	 // Create a mock or real instance of Reportexa (assuming you have a proper
//	   Reportexa reportexa = new Reportexa(); // Replace with actual
//	 //nitialization if needed
//	  
//	  // Create an instance of BillingD with the Reportexa instance BillingD
//	  billingSer = new BillingD(reportexa);
//	 
//	  Billingcontroller nn= new Billingcontroller(billingSer);
//	  List<Map<String, Object>> ll= nn.generateReport(fromDate, toDate);
//	  
//	  System.out.print("print something"+ll);
//	 }
//	  
	

}

/*
 * @GetMapping("billingForm") public ModelAndView showForm() { return new
 * ModelAndView("billingForm"); // Assuming you're using JSP or Thymeleaf with
 * this view }
 * 
 * 
 * @Autowired public BillingDao billingDao;
 * 
 * @PostMapping("/submitBilling") public ModelAndView
 * submitBilling(@ModelAttribute Customer customer) throws
 * ClassNotFoundException, SQLException { String resultMessage =
 * billingDao.insertBillingData(customer); // Insert billing data
 * 
 * ModelAndView modelAndView = new ModelAndView("confirmation");
 * modelAndView.addObject("message", resultMessage);
 * modelAndView.addObject("customer", customer); // Pass customer data for
 * confirmation
 * 
 * return modelAndView; }
 */
