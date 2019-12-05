package cn.edu.sdjzu.xg.bysj.controller.basic.security;

import cn.edu.sdjzu.xg.bysj.domain.User;

import cn.edu.sdjzu.xg.bysj.service.UserService;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login.ctl")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        JSONObject message = new JSONObject();
        User user = null;
        try {
            user = UserService.getInstance().login(username,password);
            if (user != null){
                message.put("message","Login Successfully!");
                response.getWriter().println(message);
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60 * 10);//set login time with 10 mins
                session.setAttribute("currentUser",user);
                response.getWriter().println("Create a session with 10 mins");
            }else {
                message.put("message","Error with username or password");
                response.getWriter().println(message);
            }

        } catch (SQLException e) {

            message.put("message", "数据库操作异常");
            e.printStackTrace();
            response.getWriter().println(message);
        } catch (Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }
    }

}
