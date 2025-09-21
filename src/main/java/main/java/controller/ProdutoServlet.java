package controller;

import dao.ProdutoDao;
import model.Produto;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProdutoDao dao = new ProdutoDao();
        ArrayList<Produto> lista = dao.getListaDeProduto();

        request.setAttribute("produtos", lista);

        RequestDispatcher dispatcher = request.getRequestDispatcher("produtos.jsp");
        dispatcher.forward(request, response);
    }
}
