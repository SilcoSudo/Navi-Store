/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AuthenDAO;
import DAO.CartDAO;
import DAO.CategoryDAO;
import Model.CategoryModel;
import Model.ProductModel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CategoryController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CategoryController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        String part[] = path.split("/");
        if (part[3].equalsIgnoreCase("Search")) {
            String searchTerm = request.getParameter("search");
            CategoryDAO categoryDAO = new CategoryDAO();
            List<ProductModel> searchProducts = categoryDAO.searchProducts(searchTerm);
            Integer userIdObj = (Integer) request.getSession().getAttribute("userID");
            int userId = (userIdObj != null) ? userIdObj : 0;
            List<ProductModel> productHaveInCart = categoryDAO.getProductInCartWhenSearch(searchProducts, userId);
            for (ProductModel e : searchProducts) {
                for (ProductModel j : productHaveInCart) {
                    if (j.getProductId() == e.getProductId()) {
                        e.setQuantityInCart(j.getQuantityInCart());
                    }
                }
            }
            HttpSession session = request.getSession();
            session.setAttribute("productList", searchProducts);

            Set<String> uniLabel = new HashSet<>();
            for (ProductModel e : searchProducts) {
                uniLabel.add(e.getLabelName());
            }

            Map<String, List<CategoryModel>> labelCategoryMap = new HashMap<>();

            for (String label : uniLabel) {
                List<CategoryModel> categoryList = categoryDAO.getCategoriesByLabelName(label);
                labelCategoryMap.put(label, categoryList);
            }
            session.setAttribute("labelCategoryMap", labelCategoryMap);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\":\"success\"}");

        }
        if (part[3].equalsIgnoreCase("View")) {
            AuthenDAO authenDAO = new AuthenDAO();
            HttpSession session = request.getSession(false);
            CartDAO cartDAO = new CartDAO();
            String username = (String) session.getAttribute("username");
            int userID = authenDAO.getUserIdByUsername(username);
            int totalCartItems = cartDAO.getTotalCartItems(userID);
            session.setAttribute("totalCartItems", totalCartItems);
            request.getRequestDispatcher("/View/category.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String part[] = path.split("/");

        if (part[3].equalsIgnoreCase("Search")) {
            int labelID = Integer.parseInt(request.getParameter("categoryId"));
            Integer userIdObj = (Integer) request.getSession().getAttribute("userID");
            int userId = (userIdObj != null) ? userIdObj : 0;
            HttpSession session = request.getSession();
            CategoryDAO categoryDAO = new CategoryDAO();
            List<ProductModel> productList;
            List<ProductModel> productHaveInCart;

            productList = categoryDAO.getProductByCategoryID(labelID);

            productHaveInCart = categoryDAO.getProductInCartWhenSearch(productList, userId);
            for (ProductModel e : productList) {
                for (ProductModel j : productHaveInCart) {
                    if (j.getProductId() == e.getProductId()) {
                        e.setQuantityInCart(j.getQuantityInCart());
                    }
                }
            }

            session.setAttribute("productList", productList);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\":\"success\"}");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
