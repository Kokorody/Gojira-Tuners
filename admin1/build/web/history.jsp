<%@ page import="java.util.List" %>
<%@ page import="Model.HistoryBean" %>
<%@ page import="Controller.HistoryDAO" %>
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
    <title>History</title>
    <link rel="stylesheet" href="styles.css">
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
                <li class="nav-item active"><a href="history.jsp">History</a></li>
            </ul>
            <a href="LogoutServlet">
            <button class="logout-button">LOG OUT</button>
            </a>
        </nav>
        <div class="main-content">
            <div class="content-header">
                <h2>History</h2>
            </div>
            <div class="content-body">
                <form action="SearchHistoryServlet" method="post">
                    <input type="text" name="nama" placeholder="Search by name..." value="<%= request.getParameter("nama") != null ? request.getParameter("nama") : "" %>">
                    <input type="text" name="phone" placeholder="Search by phone..." value="<%= request.getParameter("phone") != null ? request.getParameter("phone") : "" %>">
                    <button type="submit" id="Search">Search</button>
                </form>
                <table class="history-table">
                    <thead>
                        <tr>
                            <th>id_pembeli</th>
                            <th>nama</th>
                            <th>email</th>
                            <th>phone</th>
                            <th>alamat</th>
                            <th>id_barang</th>
                            <th>nama_barang</th>
                            <th>jumlah_beli</th>
                            <th>harga_satuan</th>
                            <th>total_harga</th>
                            <th>method_pembayaran</th>
                            <th>purchase_date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            List<HistoryBean> historyList = (List<HistoryBean>) request.getAttribute("historyList");
                            if (historyList == null) {
                                historyList = new HistoryDAO().getAllHistory();
                            }
                            
                            for (HistoryBean history : historyList) {
                        %>
                        <tr>
                            <td><%= history.getId_pembeli() %></td>
                            <td><%= history.getNama() %></td>
                            <td><%= history.getEmail() %></td>
                            <td><%= history.getPhone() %></td>
                            <td><%= history.getAlamat() %></td>
                            <td><%= history.getId_barang() %></td>
                            <td><%= history.getNama_barang() %></td>
                            <td><%= history.getJumlah_beli() %></td>
                            <td><%= history.getHarga_satuan() %></td>
                            <td><%= history.getTotal_harga() %></td>
                            <td><%= history.getMethod_pembayaran() %></td>
                            <td><%= history.getPurchase_date() %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
