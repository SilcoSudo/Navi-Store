<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="DAO.ProductDAO" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="Model.Product" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
        <title>Import Products</title>
        <style>
            .warehouse-import {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
            }

            .warehouse-import h1 {
                color: #333;
                text-align: center;
                font-size: 30px;
                font-weight: 600;
                margin-bottom: 30px;
            }

            .container {
                background-color: #fff;
                padding: 35px;
                border-radius: 8px;
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
                max-width: 1600px;
                margin: 30px 30px auto 0; /* Add 10px space above the container */;
                border: 1px solid #ddd;
                box-sizing: border-box;
            }

            .warehouse-import .form-group {
                margin-bottom: 20px;
            }

            .warehouse-import label {
                display: block;
                margin-bottom: 8px;
                font-weight: bold;
                font-size: 14px;
                color: #555;
            }

            .warehouse-import input[type="text"],
            .warehouse-import input[type="number"],
            .warehouse-import select,
            .warehouse-import input[type="file"] {
                width: 100%;
                padding: 12px;
                border: 1px solid #ddd;
                border-radius: 6px;
                box-sizing: border-box;
                font-size: 14px;
                transition: border-color 0.3s ease-in-out;
            }

            .warehouse-import input[type="text"]:focus,
            .warehouse-import input[type="number"]:focus,
            .warehouse-import select:focus {
                border-color: #4CAF50;
                outline: none;
            }

            .warehouse-import input[type="submit"],
            .warehouse-import button {
                background-color: #4CAF50;
                color: white;
                padding: 14px 24px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 16px;
                font-weight: bold;
                margin-top: 20px;
            }

            .warehouse-import input[type="submit"]:hover,
            .warehouse-import button:hover {
                background-color: #45a049;
            }

            .warehouse-import .product-group {
                border: 1px solid #0046b8;
                padding: 20px;
                margin-bottom: 20px;
                border-radius: 8px;
                background-color: #f9f9f9;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            .warehouse-import .product-group h3 {
                margin-top: 0;
                background-color: #0046b8;
                color: #fff;
                padding: 10px;
                font-size: 18px;
                cursor: pointer;
                font-weight: 600;
                text-transform: uppercase;
            }

            .warehouse-import .product-details {
                display: none;
                padding-top: 15px;
                margin-top: 15px;
                border-top: 1px solid #ddd;
            }

            .warehouse-import .product-details.show {
                display: block;
            }

            .warehouse-import .product-group button {
                background-color: #e74c3c;
                padding: 10px 20px;
                border-radius: 6px;
                color: white;
                cursor: pointer;
                font-size: 14px;
                font-weight: bold;
                border: none;
            }

            .warehouse-import .product-group button:hover {
                background-color: #c0392b;
            }

            #addProductBtn {
                background-color: #3498db;
                color: white;
                font-size: 16px;
                font-weight: bold;
                padding: 14px 24px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
            }

            #addProductBtn:hover {
                background-color: #2980b9;
            }

            /* Mobile responsiveness */
            @media (max-width: 768px) {
                .container {
                    padding: 20px;
                }

                .warehouse-import h1 {
                    font-size: 24px;
                }

                .warehouse-import input[type="submit"],
                .warehouse-import button,
                #addProductBtn {
                    width: 100%;
                    padding: 12px;
                }

                .warehouse-import label {
                    font-size: 16px;
                }
            }
        </style>
    </head>
    <body class="warehouse-import">
        <div class="w-container">
            <jsp:include page="headers.jsp"></jsp:include>
            <div class="container">
                <h1>Import Products</h1>
                <form action="${pageContext.request.contextPath}/ImportWarehouse" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                    <h2>Product Information</h2>
                    <div id="productContainer"></div>
                    <button type="button" id="addProductBtn">Add Product</button>
                    <br><br>
                    <input type="submit" value="Import Products">
                </form>

                <div id="productTemplate" style="display: none;">
                    <div class="product-group">
                        <h3 onclick="toggleProductDetails(this)">Product <span class="product-number"></span></h3>
                        <div class="product-details">
                            <div class="form-group">
                                <label for="productName_">Product Name:</label>
                                <input type="text" name="productName[]" required>
                            </div>
                            <div class="form-group">
                                <label for="productPrice_">Price:</label>
                                <input type="number" name="productPrice[]" required>
                            </div>
                            <div class="form-group">
                                <label for="sku_">SKU:</label>
                                <input type="text" name="sku[]" required>
                            </div>
                            <div class="form-group">
                                <label for="productQuantity_">Quantity:</label>
                                <input type="number" name="productQuantity[]" required>
                            </div>
                            <<div class="container">
                <h1>Import Products</h1>
                <form action="${pageContext.request.contextPath}/ImportWarehouse" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                    <h2>Product Information</h2>
                    <div id="productContainer"></div>
                    <button type="button" id="addProductBtn">Add Product</button>
                    <br><br>
                    <input type="submit" value="Import Products">
                </form>

                div class="form-group">
                                <label for="productDescription_">Description:</label>
                                <input type="text" name="productDescription[]" required>
                            </div>
                            <div class="form-group">
                                <label for="productImage_">Image URL:</label>
                                <input type="text" name="productImage[]" required>
                            </div>
                            <div class="form-group">
                                <label for="categoryId_">Category ID:</label>
                                <input type="number" name="categoryId[]" required>
                            </div>
                            <button type="button" onclick="removeProductSection(this)">Remove Product</button>
                        </div>
                    </div>
                </div>

                <script>
                    let productCount = 0;

                    // Function to add a new product section
                    function addProductSection() {
                        if (productCount >= 30) {
                            alert("You can add up to 30 products.");
                            return;
                        }
                        productCount++;

                        // Clone the product template and make it visible
                        const productTemplate = document.getElementById("productTemplate");
                        const newProductSection = productTemplate.cloneNode(true);
                        newProductSection.style.display = "block";
                        newProductSection.id = "";

                        // Set the product number in the header
                        newProductSection.querySelector(".product-number").innerText = productCount;

                        // Append the new product section to the container
                        document.getElementById("productContainer").appendChild(newProductSection);
                    }

                    // Function to remove a product section
                    function removeProductSection(button) {
                        $(button).closest('.product-group').remove();
                        productCount--;
                    }

                    // Function to toggle product details visibility
                    function toggleProductDetails(header) {
                        const details = header.nextElementSibling;
                        details.classList.toggle("show");
                    }

                    function validateForm() {
                        const inputs = document.querySelectorAll('input[required], select[required]');
                        for (let input of inputs) {
                            if (input.offsetParent !== null && !input.value) {
                                alert(`Please fill in the field: ${input.name}`);
                                input.focus();
                                return false;
                            }
                        }
                        return true;
                    }

                    document.getElementById('addProductBtn').addEventListener('click', addProductSection);
                </script>
            </div>
        </div>
    </body>
</html>
