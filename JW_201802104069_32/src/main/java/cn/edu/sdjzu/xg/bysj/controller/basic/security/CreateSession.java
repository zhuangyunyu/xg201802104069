package cn.edu.sdjzu.xg.bysj.controller.basic.security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/createSession")
public class CreateSession extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException

    {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(10);
        response.getWriter().println("10秒内session 有效");
    }
}
