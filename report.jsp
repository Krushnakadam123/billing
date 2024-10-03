<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
        }
        .button-gap {
            margin-top: 20px; /* Adjust the value as needed */
        }
        .input-field {
            margin-bottom: 20px; /* Adjust to add space between the input and the button */
        }
        @media print {
    .button-gap {
        display: none;
    }
}
        
    </style>
</head>
<body>
    <h1>Billing Report</h1>
	
	   <p><strong>From Date:</strong> ${formdate}</p>
       <p><strong>To Date:</strong> ${todate}</p>
       <div class="button-gap">
      <a href="<%=request.getContextPath() %>/generateReport1">Export EXcel</a>
       </div>  
       
    <table id="billingTable">
        <thead>
            <tr>
                <th>Customer Name</th>
                <th>No of Bills</th>
                <th>Total Amount</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Map<String, Object>> reportDataList = (List<Map<String, Object>>) request.getAttribute("reportDataList");
                if (reportDataList != null && !reportDataList.isEmpty()) {
                    for (Map<String, Object> customerData : reportDataList) {
                        List<Map<String, Object>> billingData = (List<Map<String, Object>>) customerData.get("billingData");
                        if (billingData != null) {
                            for (Map<String, Object> billing : billingData) {
            %>
                                <tr>
                                    <td><%= billing.get("customerName") %></td>
                                    <td><%= billing.get("totalBills") %></td>
                                    <td><%= billing.get("total2") %></td>
                                    <td><%= billing.get("date1") %></td>
                                </tr>
            <% 
                            }
                        }
                    }
                } else { 
            %>
                    <tr>
                        <td colspan="4">No data available</td>
                    </tr>
            <% 
                } 
            %>
        </tbody>
    </table>
	
    <div class="input-field">
        <label>All Total:</label>
        <input type="number" id="Alltotal" step="0.01" min="0" readonly>
        
         <button class="print-button button-gap" onclick="window.print()">Print</button>
    </div>
    
    <div class="button-gap">
        <button onclick="location.href='billing_form.jsp'" class="btn btn-primary">Go back to Billing</button>
    </div>

    <script>
    function exportToExcel() {
        const formDate = document.getElementById("formdate").value;
        const toDate = document.getElementById("todate").value;
        const customerName = document.getElementById("customer_name").value; // Get the customer name

        if (formDate && toDate) {
            const url = `/generateReport1?formdate=${formDate}&todate=${toDate}}`;
            window.location.href = url;
        } else {
            alert("Please select both dates.");
        }
    }


    function calculateTotal() {
        let Alltotal = 0;
        // Iterate over table rows
        document.querySelectorAll('#billingTable tbody tr').forEach(row => {
            const total = parseFloat(row.cells[2].textContent) || 0; // Assuming Total Amount is in the 3rd column
            Alltotal += total;
        });
        document.getElementById('Alltotal').value = Alltotal.toFixed(2);
    }

    // Calculate the total once the DOM is fully loaded
    document.addEventListener('DOMContentLoaded', calculateTotal);
    </script>
</body>
</html>
