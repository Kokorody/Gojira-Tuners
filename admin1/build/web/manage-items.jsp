<%@page import="java.util.List"%>
<%@page import="Model.AdminBean"%>
<%@page import="Controller.AdminDAO"%>
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
    <title>Manage Items</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="admin-container">
        <nav class="sidebar">
            <div class="logo">
                <h1><span class="highlight">Gojira</span>Tuners</h1>
            </div>
            <ul class="nav-list">
                <li class="nav-item active"><a href="manage-items.jsp">Manage Items</a></li>
                <li class="nav-item"><a href="add-items.jsp">Add Items</a></li>
                <li class="nav-item"><a href="history.jsp">History</a></li>
            </ul>
            <a href="LogoutServlet">
            <button class="logout-button">LOG OUT</button>
            </a>
        </nav>
        <div class="main-content">
            <div class="content-header">
                <h2>Manage Items</h2>
            </div>
            <div class="content-body">
                <table class="item-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Stock</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            // Import statements and initialization if needed
                            AdminDAO dao = new AdminDAO();
                            List<AdminBean> items = dao.getAllItems();
                            
                            for (int i = 0; i < items.size(); i++) {
                                AdminBean item = items.get(i);
                        %>
                        <tr>
                            <td><%= item.getId_barang() %></td>
                            <td><%= item.getNama_barang() %></td>
                            <td><%= item.getDesc_barang() %></td>
                            <td>Rp <%= item.getHarga_barang() %></td>
                            <td><%= item.getStok() %></td>
                            <td>
                                <a href="edit-items.jsp?id=<%= item.getId_barang() %>" class="edit">edit</a>
                                <a href="deleteAdminServlet?id=<%= item.getId_barang() %>" class="delete">delete</a>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
