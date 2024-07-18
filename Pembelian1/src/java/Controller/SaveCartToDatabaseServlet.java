package Controller;

import Database.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/saveCartToDatabase")
public class SaveCartToDatabaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String cartData = (String) session.getAttribute("cart");

        if (cartData != null) {
            // Parsing cart data (assuming it's in JSON format)
            // You need to parse your JSON string to extract necessary details
            // For simplicity, let's assume it's parsed and stored in appropriate variables
            // Replace this with your actual logic to parse the cartData JSON string
            String name = "John Doe";  // Replace with actual name from buyer details
            String email = "john.doe@example.com";  // Replace with actual email from buyer details
            String phone = "1234567890";  // Replace with actual phone number from buyer details
            String address = "123 Main St, Anytown";  // Replace with actual address from buyer details
            String productName = "Product A";  // Replace with actual product name
            int quantity = 2;  // Replace with actual quantity purchased
            double unitPrice = 100.0;  // Replace with actual unit price
            double totalPrice = quantity * unitPrice;  // Replace with actual total price
            String paymentMethod = "Credit Card";  // Replace with actual payment method
            
            // Database connection setup
            Connection conn = null;
            PreparedStatement stmt = null;
            
            try {
                // Get database connection from DBConnection class
                conn = new DBConnection().setConnection();
                
                // Insert query to save cart data into database
                String insertQuery = "INSERT INTO purchases (name, email, phone, address, product_name, quantity, unit_price, total_price, payment_method) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(insertQuery);
                
                // Set parameters
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, address);
                stmt.setString(5, productName);
                stmt.setInt(6, quantity);
                stmt.setDouble(7, unitPrice);
                stmt.setDouble(8, totalPrice);
                stmt.setString(9, paymentMethod);
                
                // Execute query
                int rowsAffected = stmt.executeUpdate();
                
                // Handle result
                if (rowsAffected > 0) {
                    // Clear cart data from session after successful insertion
                    session.removeAttribute("cart");
                    
                    // Send success response to client
                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    out.write("{\"status\": \"success\"}");
                } else {
                    // Handle insertion failure
                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    out.write("{\"status\": \"error\", \"message\": \"Failed to save data to database.\"}");
                }
            } catch (SQLException ex) {
                // Handle database connection and SQL exceptions
                ex.printStackTrace();
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.write("{\"status\": \"error\", \"message\": \"Database error: " + ex.getMessage() + "\"}");
            } finally {
                // Close resources
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            // Handle case where cart data is not found in session
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.write("{\"status\": \"error\", \"message\": \"No cart data found in session.\"}");
        }
    }
}
