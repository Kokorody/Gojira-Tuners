<%@ page import="Model.AdminBean" %>
<%@ page import="Controller.AdminDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if(session.getAttribute("login") == null){
        response.sendRedirect("index.html");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Item</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        #file-upload {
            display: none;
        }
        #file-container {
            margin-top: 20px;
        }
        #file-container img {
            max-width: 100%;
            height: auto;
            margin: 20px auto;
            display: block;
        }
    </style>
</head>
<body>
    <div class="admin-container">
        <nav class="sidebar">
            <div class="logo">
                <h1><span class="highlight">Gojira</span>Tuners</h1>
            </div>
            <ul class="nav-list">
                <li class="nav-item"><a href="manage-items.jsp">Manage Items</a></li>
                <li class="nav-item"><a href="add-items.jsp">Add Items</a></li>
                <li class="nav-item"><a href="history.jsp">History</a></li>
            </ul>
            <a href="LogoutServlet">
            <button class="logout-button">LOG OUT</button>
            </a>
        </nav>
        <div class="main-content">
            <div class="content-header">
                <h2>Edit Item</h2>
            </div>
            <div class="content-body">
                <% 
                    // Mendapatkan ID barang dari parameter URL
                    int id = Integer.parseInt(request.getParameter("id"));
                    
                    // Memuat data barang berdasarkan ID
                    AdminDAO dao = new AdminDAO();
                    AdminBean item = dao.getItemById(id);
                %>
                <form action="updateAdminServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="id_barang" value="<%= item.getId_barang() %>">
                    <input type="hidden" name="current_image" value="<%= item.getBase64Image() %>"> <!-- Menyimpan gambar saat ini -->
                    <label for="file-upload" class="custom-file-upload">Choose File</label>
                    <input id="file-upload" type="file" name="images" accept=".jpg, .jpeg, .png" />
                    <div id="file-container">
                        <%-- Menampilkan gambar saat ini --%>
                        <img src="data:image/png;base64, <%= item.getBase64Image() %>" alt="Current Image" />
                    </div>
                    <input type="text" name="nama_barang" value="<%= item.getNama_barang() %>" required>
                    <input type="text" name="desc_barang" value="<%= item.getDesc_barang() %>" required>
                    <input type="text" name="harga_barang" value="<%= item.getHarga_barang() %>" required>
                    <input type="text" name="stok" value="<%= item.getStok() %>" required>
                    <button type="submit">Update</button>
                </form>
            </div>
        </div>
    </div>
    <script>
        const fileInput = document.getElementById('file-upload');
        const fileContainer = document.getElementById('file-container');

        fileInput.addEventListener('change', () => {
            const file = fileInput.files[0];
            const reader = new FileReader();

            reader.onload = () => {
                const imageData = reader.result;
                const img = document.createElement('img');
                img.src = imageData;
                img.style.maxWidth = '30%'; // Display at 50% of web size
                img.style.height = 'auto'; // Maintain aspect ratio
                fileContainer.innerHTML = ''; // Clear the container
                fileContainer.appendChild(img); // Add the image to the container
            };

            reader.readAsDataURL(file);
        });
    </script>
</body>
</html>
