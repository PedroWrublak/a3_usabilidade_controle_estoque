<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Produto" %>
<%@ page import="java.util.ArrayList" %>

<%
    Produto produtoModel = new Produto();
    ArrayList<Produto> lista = produtoModel.getListaDeProduto();
%>

<html>
<head>
    <title>Lista de Produtos</title>
    <style>
        table { border-collapse: collapse; width: 70%; }
        th, td { border: 1px solid black; padding: 8px; text-align: left; }
        th { background-color: #ddd; }
    </style>
</head>
<body>
    <h2>Produtos</h2>
    <a href="./produto.jsp">produtos</a>

    <table>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Preço Unitário</th>
            <th>Unidade</th>
            <th>Quantidade</th>
            <th>Categoria</th>
        </tr>
        <%
            if (lista != null && !lista.isEmpty()) {
                for (Produto p : lista) {
        %>
        <tr>
            <td><%= p.getId() %></td>
            <td><%= p.getNome() %></td>
            <td><%= p.getPrecoUnitario() %></td>
            <td><%= p.getUnidadeMedida() %></td>
            <td><%= p.getQuantidadeEmEstoque() %></td>
            <td><%= p.getNomeCategoria() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="6">Nenhum produto encontrado</td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>
