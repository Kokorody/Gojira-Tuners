package Controller;

import Model.AdminBean;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "UpdateAdminServlet", urlPatterns = {"/updateAdminServlet"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 20, // 20MB
                maxRequestSize = 1024 * 1024 * 50, // 50MB
                fileSizeThreshold = 1024 * 1024) // 1MB
public class UpdateAdminServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Ambil parameter dari form edit-items.jsp
        int id_barang = Integer.parseInt(request.getParameter("id_barang"));
        String nama_barang = request.getParameter("nama_barang");
        String desc_barang = request.getParameter("desc_barang");
        int harga_barang = Integer.parseInt(request.getParameter("harga_barang"));
        int stok = Integer.parseInt(request.getParameter("stok"));
        Part filePart = request.getPart("images");
        String currentImage = request.getParameter("current_image");

        // Ambil gambar
        byte[] imageData = null;
        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream inputStream = filePart.getInputStream();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                imageData = outputStream.toByteArray();
            } catch (IOException e) {
                System.out.println("Error converting image to byte array: " + e.getMessage());
            }
        } else {
            // Jika tidak ada gambar baru, gunakan gambar yang ada
            if (currentImage != null && !currentImage.isEmpty()) {
                imageData = Base64.getDecoder().decode(currentImage);
            }
        }

        // Update data barang
        AdminBean item = new AdminBean();
        item.setId_barang(id_barang);
        item.setNama_barang(nama_barang);
        item.setDesc_barang(desc_barang);
        item.setHarga_barang(harga_barang);
        item.setStok(stok);
        item.setImages(imageData); // Set gambar baru jika ada

        // Panggil AdminDAO untuk melakukan update
        int status = AdminDAO.updateItem(item);

        if (status > 0) {
            // Redirect kembali ke manage-items.jsp jika berhasil update
            response.sendRedirect("manage-items.jsp");
        } else {
            // Handle jika terjadi error
            response.getWriter().println("Failed to update item.");
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
        return "Update Admin Servlet";
    }
}
