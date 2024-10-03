<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Billing Form</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
 
    <link rel="stylesheet" href="styles.css">
    <style>
         body {
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1, h2 {
            color: #333;
        }
        .form-container {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background: #fafafa;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
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
        label {
            display: block;
            margin: 10px 0 5px;
        }
        input[type="text"], input[type="number"], input[type="date"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            padding: 10px 15px;
            margin-top: 10px;
            border: none;
            border-radius: 4px;
            color: #fff;
            background-color: #007BFF;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
        .report-form {
            border-top: 2px solid #007BFF;
            padding-top: 20px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container my-5">
        <!-- Billing Form -->
        <div class="card p-4 mb-5 shadow-sm">
            <h1 class="mb-4">Billing Form</h1>
            <form action="billing" method="post">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="customer_name" class="form-label">Customer Name:</label>
                        <input type="text" class="form-control" name="customer_name" id="customer_name" required>
                    </div>
                    <div class="col-md-6 align-self-end">
                        <button type="button" class="btn btn-secondary" onclick="showCustomerName()">Show Customer Name</button>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="customer_id" class="form-label">Customer ID:</label>
                        <input type="number" class="form-control" name="customer_id" required>
                    </div>
                    
                </div>

                <div class="mb-3">
                    <label for="date" class="form-label">Date:</label>
                    <input type="date" class="form-control" name="date" required>
                </div>

                <table id="itemsTable" class="table table-bordered table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>Item ID</th>
                            <th>Item Name</th>
                            <th>Price</th>
                            <th>Discount (%)</th>
                            <th>Quantity</th>
                            <th>Total</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
            <tr>
                            <td><input type="number" class="form-control" name="iditem" required></td>
                            <td><input type="text" class="form-control" name="item_name" required></td>
                            <td><input type="number" class="form-control" name="price" step="0.01" min="0" required></td>
                            <td><input type="number" class="form-control" name="discount" step="0.01" min="0" max="100" required></td>
                            <td><input type="number" class="form-control" name="quantity" min="1" required></td>
                            <td><input type="number" class="form-control" name="total" step="0.01" min="0" readonly></td>
                            <td><button type="button" class="btn btn-danger" onclick="removeRow(this)">Remove</button></td>
                        </tr>
                    </tbody>
                </table>
	
                <div class="mb-3">
                    <label>Total2:</label>
                    <input type="number" class="form-control" name="total2" id="total2" step="0.01" min="0" readonly>
                </div>

                <div class="d-flex">
                    <button type="button" class="btn btn-success me-2" onclick="addRow()">Add Item</button>
                    <button type="button" class="btn btn-warning me-2" onclick="calculateTotal2()">Calculate Total2</button>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </div>
        

      <!-- Report Generation Form -->
<div class="card p-4 shadow-sm">
    <h2 class="mb-4">Generate Report</h2>
    <form action="generateReport" method="get">
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="formdate" class="form-label">From Date:</label>
                <input type="date" id="formdate" name="formdate" class="form-control" required>
            </div>
            <div class="col-md-6">
                <label for="todate" class="form-label">To Date:</label>
                <input type="date" id="todate" name="todate" class="form-control" required>
                
            </div>
        	
        <label for="customer_name">Customer Name:</label>
        <input type="text" id="customer_name" name="customer_name">
        	
        </div>
        
        <button type="submit" class="btn btn-primary">Show Report</button>
            
    </form>
</div>
</div> 

<script>
	//date format
    function formatDates() {
        // Get the date inputs
        const formdateInput = document.getElementById('formdate');
        const todateInput = document.getElementById('todate');
        
        // Convert the date to yyyymmdd format
        formdateInput.value = formdateInput.value.replace(/-/g, '');
        todateInput.value = todateInput.value.replace(/-/g, '');
    }
</script>
    

    <script>
        function showCustomerName() {
            const customerName = document.getElementById('customer_name').value;
            alert(`Customer Name: ${customerName}`);
        }

        function calculateTotal(row) {
            const priceInput = row.querySelector('input[name="price"]');
            const quantityInput = row.querySelector('input[name="quantity"]');
            const discountInput = row.querySelector('input[name="discount"]');
            const totalInput = row.querySelector('input[name="total"]');

            const price = parseFloat(priceInput.value) || 0;
            const quantity = parseFloat(quantityInput.value) || 0;
            const discount = parseFloat(discountInput.value) || 0;

            if (price < 0 || quantity < 0 || discount < 0 || discount > 100) {
                totalInput.value = "Invalid input";
                return;
            }

            const total = price * quantity * (1 - discount / 100);
            totalInput.value = total.toFixed(2);
        }

        function addRow() {
            const tableBody = document.querySelector('#itemsTable tbody');
            const newRow = document.createElement('tr');
            newRow.innerHTML = `
                <td><input type="number" name="iditem" required></td>
                <td><input type="text" name="item_name" required></td>
                <td><input type="number" name="price" step="0.01" min="0" required></td>
                <td><input type="number" name="discount" step="0.01" min="0" max="100" required></td>
                <td><input type="number" name="quantity" min="1" required></td>
                <td><input type="number" name="total" step="0.01" min="0" readonly></td>
                <td><button type="button" onclick="removeRow(this)">Remove</button></td>
            `;
            tableBody.appendChild(newRow);
            Array.from(newRow.querySelectorAll('input')).forEach(input => {
                input.addEventListener('input', () => {
                    calculateTotal(newRow);
                    calculateTotal2(); // Update grand total whenever a row is updated
                });
            });
        }

        function removeRow(button) {
            const row = button.closest('tr');
            row.remove();
            calculateTotal2(); // Update grand total after removing a row
        }

        function calculateTotal2() {
            let total2 = 0;
            document.querySelectorAll('#itemsTable tbody tr').forEach(row => {
                const total = parseFloat(row.querySelector('input[name="total"]').value) || 0;
                total2 += total;
            });
            document.getElementById('total2').value = total2.toFixed(2);
        }

        // Attach event listeners to existing rows
        document.querySelectorAll('#itemsTable tbody input').forEach(input => {
            input.addEventListener('input', function() {
                const row = this.closest('tr');
                calculateTotal(row);
                calculateTotal2();
            });
        });
    </script>
</body>
</html>
