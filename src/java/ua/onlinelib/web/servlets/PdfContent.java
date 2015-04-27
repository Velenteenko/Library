/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.onlinelib.web.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
//import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.onlinelib.web.controllers.BookListController;
import ua.onlinelib.web.db.DataHelper;

/**
 *
 * @author velenteenko
 */
public class PdfContent extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("application/pdf");
//        OutputStream out = response.getOutputStream();
//        try {
            //params
            Long id = Long.valueOf(request.getParameter("id"));
            boolean save = Boolean.valueOf(request.getParameter("save"));
            String filename = request.getParameter("filename");
            ///
            BookListController searchController = (BookListController) request.getSession(false).getAttribute("bookListController");
//            byte[] content = searchController.getContent(id);
            byte[] content = DataHelper.getInstance().getContent(id);
            if (content.length == 0) {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter outp = response.getWriter();
                try {
                    /* TODO output your page here. You may use following sample code. */
                    outp.println("<!DOCTYPE html>");
                    outp.println("<html>");
                    outp.println("<head>");
                    outp.println("<title>Error!!!</title>");
                    outp.println("</head>");
                    outp.println("<body>");
                    outp.println("<h1>Документ " + filename + " не существует или не был прикреплен! </h1>");
                    outp.println("</body>");
                    outp.println("</html>");
                } finally {
                    outp.close();
                }
            } else {
                response.setContentType("application/pdf");
                OutputStream out = response.getOutputStream();
                response.setContentLength(content.length);
            if (save) {
//                String urlparm = filename.replace("+", "%2B");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8") + ".pdf");
            }
            out.write(content);
            }
//        }
//        } catch (NumberFormatException ex) {
//        } catch (IOException ex) {
//        } finally {
//            out.close();
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
