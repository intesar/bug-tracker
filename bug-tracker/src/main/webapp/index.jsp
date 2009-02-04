<html>
    <body>
        <%
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        String newLocn = "view/task.jsp";
        response.setHeader("Location",newLocn);
        %>
    </body>
</html>
