package Controller;

import Database.DBConnection;
import Model.Product;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        List<Product> productList = new ArrayList<>();
        try (Connection conn = new DBConnection().setConnection()) {
            String query = "SELECT * FROM tbl_barang";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Product product = new Product();
                        product.setId_barang(rs.getInt("id_barang"));
                        product.setNama_barang(rs.getString("nama_barang"));
                        product.setDesc_barang(rs.getString("desc_barang"));
                        product.setHarga_barang(rs.getInt("harga_barang"));
                        product.setStok(rs.getInt("stok"));
                        // Load image from blob
                        product.setImages(rs.getBytes("images"));

                        productList.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to fetch products from database.");
            return;
        }

        // Convert productList to JSON and send as response
        String json = new Gson().toJson(productList);
        response.getWriter().write(json);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
