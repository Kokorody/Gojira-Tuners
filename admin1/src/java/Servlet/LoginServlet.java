package Servlet;

import Database.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    static Connection conn;
    static PreparedStatement ps;
    static ResultSet rs;
    static String sql;
    
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String eMail = request.getParameter("eMail");
        String pWord = request.getParameter("pWord");

        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("SELECT * FROM tbl_admin WHERE email = ? AND password = ?");
            ps.setString(1, eMail);
            ps.setString(2, pWord);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("user", eMail);
                session.setAttribute("login", true);
                RequestDispatcher rd = request.getRequestDispatcher("manage-items.jsp");
                rd.forward(request, response);
                return;
            }
            response.sendRedirect("index.html?status=error");
        } catch (SQLException | ServletException e) {
            response.sendRedirect("index.html?status=error");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

