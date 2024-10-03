<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Billing Success</title>
    <style>
        .print-area {
            width: 100%;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .print-button {
            margin-top: 20px;
            display: block;
            width: 100%;
            text-align: center;
            padding: 10px;
            font-size: 16px;
            border: none;
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
        }
         .button-gap {
        margin-top: 10px; /* Adjust the value as needed */
        margin-bottom: 10px;
    }
        @media print {
            .print-button {
                display: none;
            }
        }
    </style>
</head>
<body>

    <div class="print-area">
        <h2>Billing Information Submitted Successfully!</h2>

        <p><strong>Customer Name:</strong> ${customerName}</p>
        <p><strong>Customer ID:</strong> ${customerId}</p>
        <p><strong>Date:</strong> ${date}</p>
        <p><strong>Total Amount:</strong> ${total2}</p>

        <!-- Optional: Display detailed billing information here -->

        <button class="print-button button-gap" onclick="window.print()">Print</button>
        <br>
        <button onclick="location.href='billing_form.jsp'" class="btn btn-primary button-gap">Go back to Billing</button>
    </div>

</body>
</html>

  


