<%@page import="Controller.AdminDAO"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="javax.servlet.ServletException"%>
<%@page import="javax.servlet.annotation.MultipartConfig"%>
<%@page import="javax.servlet.http.Part"%>
<jsp:useBean id="wb" class="Model.AdminBean"></jsp:useBean>
<jsp:setProperty property="*" name="wb"/>

<%
    Part filePart = request.getPart("images");
    InputStream inputStream = null;
    if (filePart != null) {
        inputStream = filePart.getInputStream();
    }

    byte[] bytes = null;
    if (inputStream != null) {
        bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
    }

    wb.setImages(bytes);

    int i = AdminDAO.simpan(wb);
    if (i > 0) {
        response.sendRedirect("addAdminSuccess.jsp");
    } else {
        response.sendRedirect("addAdminError.jsp");
    }
%>
