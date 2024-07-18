/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import Model.HistoryBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchHistoryServlet")
public class SearchHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nama = request.getParameter("nama");
        String phone = request.getParameter("phone");
        
        HistoryDAO dao = new HistoryDAO();
        List<HistoryBean> historyList;
        
        if ((nama == null || nama.isEmpty()) && (phone == null || phone.isEmpty())) {
            historyList = dao.getAllHistory();
        } else {
            historyList = dao.searchHistory(nama, phone);
        }
        
        request.setAttribute("historyList", historyList);
        request.getRequestDispatcher("history.jsp").forward(request, response);
    }
}

