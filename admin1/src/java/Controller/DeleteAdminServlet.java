
package Controller;

import Model.AdminBean;
import Controller.AdminDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteAdminServlet", urlPatterns = {"/deleteAdminServlet"})
public class DeleteAdminServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Ambil ID barang yang akan dihapus dari parameter URL
        int id_barang = Integer.parseInt(request.getParameter("id"));

        // Panggil AdminDAO untuk menghapus barang dari database
        int status = AdminDAO.deleteItem(id_barang);

        if (status > 0) {
            // Redirect kembali ke manage-items.jsp jika berhasil delete
            response.sendRedirect("manage-items.jsp");
        } else {
            // Handle jika terjadi error
            response.getWriter().println("Failed to delete item.");
        }
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
