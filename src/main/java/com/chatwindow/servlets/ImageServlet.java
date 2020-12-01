package com.chatwindow.servlets;
import com.chatwindow.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Code that was used to try to work with the encryption system.
@WebServlet(urlPatterns = "/image", loadOnStartup = 1)
public class ImageServlet extends HttpServlet{
    @Autowired
    private ImageService imageGetter;

    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        doGet(request,response);

    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        try {
            imageGetter.getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("/*");
    }
}