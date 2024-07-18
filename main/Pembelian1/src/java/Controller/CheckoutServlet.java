package Controller;

import Controller.CheckoutDAO;
import Database.DBConnection;
import Model.CartItem;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout1"})
@MultipartConfig
public class CheckoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ambil data form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String paymentMethod = request.getParameter("paymentMethod");
        String cartJson = request.getParameter("cart");

        // Log data untuk debugging
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Address: " + address);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Cart JSON: " + cartJson);

        // Cek apakah data diterima dengan benar
        if (name == null || email == null || phone == null || address == null || paymentMethod == null || cartJson == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing parameters");
            return;
        }

        Gson gson = new Gson();
        CartItem[] cartItems = gson.fromJson(cartJson, CartItem[].class);

        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.setConnection();

        if (conn != null) {
            try {
                conn.setAutoCommit(false);

                CheckoutDAO checkoutDAO = new CheckoutDAO();
                checkoutDAO.saveOrder(conn, name, email, phone, address, paymentMethod, cartItems);
                checkoutDAO.updateStock(conn, cartItems);

                conn.commit();
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Checkout successful!");
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error during checkout: " + e.getMessage());
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to connect to database!");
        }
    }
}
