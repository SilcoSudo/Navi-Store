package Controller;

import DAO.ProductDAO;
import Model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ImportWarehouseController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Get product data from the form
        String[] productNames = request.getParameterValues("productName");
        String[] productPrices = request.getParameterValues("productPrice");
        String[] productSkus = request.getParameterValues("sku");
        String[] productQuantities = request.getParameterValues("productQuantity");
        String[] productDescriptions = request.getParameterValues("productDescription");
        String[] productImages = request.getParameterValues("productImage");
        String[] categoryIds = request.getParameterValues("categoryId");

        if (productNames == null || productNames.length == 0) {
            request.setAttribute("errorMessage", "At least one product must be added.");
            request.getRequestDispatcher("/importProduct.jsp").forward(request, response);
            return;
        }

        ProductDAO productDAO = new ProductDAO();

        try {
            for (int i = 0; i < productNames.length; i++) {
                if (productNames[i] == null || productNames[i].isEmpty()) {
                    continue; // Skip empty product names
                }

                Product product = new Product();
                product.setProductName(productNames[i]);
                product.setProductPrice(new BigDecimal(productPrices[i]));
                product.setProductSku(productSkus[i]);
                product.setProductQuantity(Integer.parseInt(productQuantities[i]));
                product.setProductDescription(productDescriptions[i]);
                product.setImgUrl(productImages[i]);
                product.setCategoryId(Integer.parseInt(categoryIds[i]));
                product.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Set createdAt to current time

                productDAO.addProduct(product); // Add product to DB

                // The trigger will handle the addition of the inventory_report automatically
            }

            response.sendRedirect("/productsList.jsp"); // Redirect to product list after success
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while importing products.");
            request.getRequestDispatcher("/importProduct.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    private boolean isInvalidInput(String input) {
        return (input == null || input.trim().isEmpty());
    }
}
