<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <title>Add Item</title>
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
                <li class="nav-item active"><a href="add-items.jsp">Add Items</a></li>
                <li class="nav-item"><a href="history.jsp">History</a></li>
            </ul>
            <a href="LogoutServlet">
            <button class="logout-button">LOG OUT</button>
            </a>
        </nav>
        <div class="main-content">
            <div class="content-header">
                <h2>Add Item</h2>
            </div>
            <div class="content-body">
                <form action="addAdminServlet" method="post" enctype="multipart/form-data">
                <label for="file-upload" class="custom-file-upload">Choose File</label>
                <input id="file-upload" name="images" type="file" accept=".jpg, .jpeg, .png" />
                <div id="file-container"></div>
                <p id="file-name" style="text-align: center;"></p>
                <input type="text" name="nama_barang" placeholder="Item Name" required>
                <input type="text" name="desc_barang" placeholder="Description" required>
                <input type="text" name="harga_barang" placeholder="Price" required>
                <input type="text" name="stok" placeholder="Stock" required>
                <button type="submit">Post</button>
                </form>
            </div>
        </div>
    </div>
    <script>
        const fileInput = document.getElementById('file-upload');
        const fileContainer = document.getElementById('file-container');
        const fileName = document.getElementById('file-name');

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

                fileName.textContent = file.name; // Display the file name
            };

            reader.readAsDataURL(file);
        });
    </script>
</body>
</html>
