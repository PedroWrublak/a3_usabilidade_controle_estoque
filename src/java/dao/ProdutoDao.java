package dao;

import model.Produto;
import java.sql.*;
import java.util.ArrayList;

public class ProdutoDao {

    public ArrayList<Produto> listaDeProduto = new ArrayList<>();
    public ArrayList<Produto> listaDePreco = new ArrayList<>();
    public ArrayList<Produto> listaQuantidadeDeProdutoPorCategoria = new ArrayList<>();
    public ArrayList<Produto> listaBalancoFisicoFinanceiro = new ArrayList<>();
    public ArrayList<Produto> listaQuantidadeEstoqueAbaixoQuantidadeMinima = new ArrayList<>();
    public ArrayList<Produto> listaAcimaQuantidadeMaxima = new ArrayList<>();

    // Método para conexão com o banco de dados
    public Connection getConnection(){

        // Instância da conexão
        Connection connection = null;

        try {
            // Carregamento jdbc Driver
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);

            // Configurar conexão
            String server = "localhost";
            String database = "db_produto";
            String url = "jdbc:mysql://" + server + ":3306/" + database + "?useTimezone=true&serverTimezone=UTC";
            String user = "root";
            String password = "root";

            // Conectando
            connection = DriverManager.getConnection(url, user, password);

            // Teste
            if(connection != null){
                System.out.println("Status: Conectado");
            }
            else
            {
                System.out.println("Status: Não conectado");
            }

            return connection;
        }
        // Driver não encontrado
        catch(ClassNotFoundException erro){
            System.out.println("O driver não foi encontrado");
            return null;
        }
        catch(SQLException erro){
            System.out.println("Não foi possível conectar!");
            return null;
        }

    }

    // Método que retorna uma Lista de produto dando join na tabela categoria para ter acesso ao nome da categoria
    public ArrayList<Produto> getListaDeProduto(){

        listaDeProduto.clear(); // Limpa a lista

        try{
            Statement statement = this.getConnection().createStatement();

            // Executa o comando SQL
            ResultSet resultSet = statement.executeQuery("SELECT p.id_produto, p.nome_produto, p.preco_unitario, p.unidade_de_medida, " +
                    "p.quantidade_estoque, p.quantidade_minima, p.quantidade_maxima, c.nome_categoria " +
                    "FROM tb_produto p JOIN tb_categoria c ON p.id_categoria = c.id_categoria");

            /* Recebe valores a partir do nome da coluna na tb_produto, adiciona
               cada objeto produto a lista de produto e percorre toda a tabela até
               a última linha
             */
            while(resultSet.next()){
                int id = resultSet.getInt("id_produto");
                String nome = resultSet.getString("nome_produto");
                double precoUnitario = resultSet.getDouble("preco_unitario");
                String unidadeDeMedida = resultSet.getString("unidade_de_medida");
                int quantidadeEmEstoque = resultSet.getInt("quantidade_estoque");
                int quantidadeMinimaEmEstoque = resultSet.getInt("quantidade_minima");
                int quantidadeMaximaEmEstoque = resultSet.getInt("quantidade_maxima");
                String nomeCategoria = resultSet.getString("nome_categoria");

                Produto produto = new Produto();
                // É necessário setar todas as variáveis para não retornar um erro de NullPointerException
                produto.setId(id);
                produto.setNome(nome);
                produto.setPrecoUnitario(precoUnitario);
                produto.setUnidadeMedida(unidadeDeMedida);
                produto.setQuantidadeEmEstoque(quantidadeEmEstoque);
                produto.setQuantidadeMinimaEmEstoque(quantidadeMinimaEmEstoque);
                produto.setQuantidadeMaximaEmEstoque(quantidadeMaximaEmEstoque);
                produto.setNomeCategoria(nomeCategoria);
                listaDeProduto.add(produto);


            }
            resultSet.close(); // fecha o recurso
            statement.close(); // fecha o recurso
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return listaDeProduto;
    }

    public ArrayList<Produto> getRelatorioListaDePreco(){

        listaDePreco.clear(); // Limpa a lista de preços

        try{
            Statement statement = this.getConnection().createStatement();

            // Executa o comando SQL
            ResultSet resultSet = statement.executeQuery("select p.id_produto, p.nome_produto, p.preco_unitario, p.unidade_de_medida, c.nome_categoria "
                    + "from tb_produto p join tb_categoria c on p.id_categoria = c.id_categoria order by p.nome_produto;");

            while(resultSet.next()){

                int id = resultSet.getInt("id_produto");
                String nome = resultSet.getString("nome_produto");
                double precoUnitario = resultSet.getDouble("preco_unitario");
                String unidadeDeMedida = resultSet.getString("unidade_de_medida");
                String nomeCategoria = resultSet.getString("nome_categoria");

                Produto produto = new Produto();
                // É necessário setar todas as variáveis para não retornar um erro de NullPointerException
                produto.setId(id);
                produto.setNome(nome);
                produto.setPrecoUnitario(precoUnitario);
                produto.setUnidadeMedida(unidadeDeMedida);
                produto.setNomeCategoria(nomeCategoria);
                listaDePreco.add(produto);
            }
            resultSet.close(); // fecha o recurso
            statement.close(); // fecha o recurso
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return listaDePreco;
    }

    public ArrayList<Produto> getRelatorioQuantidadeDeProdutoPorCategoria(){

        listaQuantidadeDeProdutoPorCategoria.clear(); // Limpa a lista

        try{
            Statement statement = this.getConnection().createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT c.nome_categoria, COUNT(p.id_produto) AS quantidade_de_produto FROM tb_categoria c LEFT JOIN tb_produto p ON p.id_categoria = c.id_categoria GROUP BY c.nome_categoria");

            while(resultSet.next()){
                String nomeCategoria = resultSet.getString("nome_categoria");
                int quantidadeDeProduto = resultSet.getInt("quantidade_de_produto");

                Produto produto = new Produto();
                produto.setNomeCategoria(nomeCategoria);
                produto.setQuantidadeDeProdutoPorCategoria(quantidadeDeProduto);
                listaQuantidadeDeProdutoPorCategoria.add(produto);
            }
            resultSet.close(); // fecha o recurso
            statement.close(); // fecha o recurso
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return listaQuantidadeDeProdutoPorCategoria;
    }

    public ArrayList<Produto> getBalancoFisicoFinanceiro(){

        listaBalancoFisicoFinanceiro.clear(); // Limpa a lista

        try{
            Statement statement = this.getConnection().createStatement();

            ResultSet resultSet = statement.executeQuery("select p.nome_produto, p.quantidade_estoque, p.preco_unitario," +
                    "(p.preco_unitario * p.quantidade_estoque) as valor_total_produto, " +
                    "(select sum(preco_unitario * quantidade_estoque) from tb_produto) " +
                    "as valor_total_geral from tb_produto p order by p.nome_produto");

            while(resultSet.next()) {
                String nome = resultSet.getString("nome_produto");
                int quantidadeEmEtoque = resultSet.getInt("quantidade_estoque");
                double precoUnitario = resultSet.getDouble("preco_unitario");
                double valorTotalDeUmProduto = resultSet.getDouble("valor_total_produto");
                double valorGeralDoEstoque = resultSet.getDouble("valor_total_geral");
                
                
                Produto produto = new Produto();
                produto.setNome(nome);
                produto.setQuantidadeEmEstoque(quantidadeEmEtoque);
                produto.setPrecoUnitario(precoUnitario);
                produto.setValorTotalDeUmProduto(valorTotalDeUmProduto);
                produto.setValorGeralDoEstoque(valorGeralDoEstoque);
                listaBalancoFisicoFinanceiro.add(produto);
            }
            resultSet.close(); // fecha o recurso
            statement.close(); // fecha o recurso
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return listaBalancoFisicoFinanceiro;
    }
    
      public double getValorGeralDoEstoque() {
        double total = 0.0;

        try {
            Statement statement = this.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(
                "SELECT SUM(preco_unitario * quantidade_estoque) AS total FROM tb_produto"
            );

            if (rs.next()) {
                total = rs.getDouble("total");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    // Retorna uma lista com os produtos que estão com a quantidade em estoque abaixo da quantidade mínima
    public ArrayList<Produto> getQuantidadeEstoqueAbaixoQuantidadeMinima(){

        listaQuantidadeEstoqueAbaixoQuantidadeMinima.clear(); // Limpa a lista

        try{
            Statement statement = this.getConnection().createStatement();

            ResultSet resultSet = statement.executeQuery("select nome_produto, quantidade_minima, " +
                    "quantidade_estoque from tb_produto where " +
                    "quantidade_estoque < quantidade_minima order by nome_produto;");

            while(resultSet.next()){
                String nome = resultSet.getString("nome_produto");
                int quantidadeMinimaEmEstoque = resultSet.getInt("quantidade_minima");
                int quantidadeEmEstoque = resultSet.getInt("quantidade_estoque");

                Produto produto = new Produto();
                produto.setNome(nome);
                produto.setQuantidadeMinimaEmEstoque(quantidadeMinimaEmEstoque);
                produto.setQuantidadeEmEstoque(quantidadeEmEstoque);
                listaQuantidadeEstoqueAbaixoQuantidadeMinima.add(produto);
            }
            resultSet.close(); // fecha o recurso
            statement.close(); // fecha o recurso
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return listaQuantidadeEstoqueAbaixoQuantidadeMinima;
    }

    public ArrayList<Produto> getListaAcimaQuantidadeMaxima(){

        listaAcimaQuantidadeMaxima.clear(); // Limpa a lista

        try{
            Statement statement = this.getConnection().createStatement();

            ResultSet resultSet = statement.executeQuery("select nome_produto, quantidade_maxima, quantidade_estoque "
                    + "from tb_produto where quantidade_estoque > quantidade_maxima order by nome_produto");

            while(resultSet.next()){

                String nome = resultSet.getString("nome_produto");
                int quantidadeMaximaEmEstoque = resultSet.getInt("quantidade_maxima");
                int quantidadeEmEstoque = resultSet.getInt("quantidade_estoque");

                Produto produto = new Produto();
                produto.setNome(nome);
                produto.setQuantidadeMaximaEmEstoque(quantidadeMaximaEmEstoque);
                produto.setQuantidadeEmEstoque(quantidadeEmEstoque);
                listaAcimaQuantidadeMaxima.add(produto);
            }
            resultSet.close();
            statement.close();
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return listaAcimaQuantidadeMaxima;
    }

    // Método para inserção dados
    public boolean insertProdutoDB(Produto produto){

        // Comando SQL para inserir dados na tb_produto
        String sql = "INSERT INTO tb_produto(id_produto, nome_produto, preco_unitario, unidade_de_medida, quantidade_estoque, quantidade_minima, quantidade_maxima, id_categoria) VALUES (?,?,?,?,?,?,?,?)";

        try{
            PreparedStatement statement = this.getConnection().prepareStatement(sql);

            statement.setInt(1, produto.getId());
            statement.setString(2, produto.getNome());
            statement.setDouble(3, produto.getPrecoUnitario());
            statement.setString(4, produto.getUnidadeMedida());
            statement.setInt(5, produto.getQuantidadeEmEstoque());
            statement.setInt(6, produto.getQuantidadeMinimaEmEstoque());
            statement.setInt(7, produto.getQuantidadeMaximaEmEstoque());
            statement.setInt(8, produto.getCategoria().getId());  // Nesse caso, é preciso colocar o getId() para extrair apenas o id da categoria que é o que precisamos


            statement.execute();
            statement.close();

            return true;
        }
        catch (SQLException erro){
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
        catch (NullPointerException erro) {
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    /* Método para retornar o maior ID na tabela para que automaticamente
       a próxima linha inserida já venha com o id_produto = maiorID + 1 (isso vai ser feito na classe Produto)
    */
    public int getMaiorID(){
        int maiorID = 0;

        try {
            Statement statement = this.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(id_produto) from tb_produto");
            resultSet.next();
            maiorID = resultSet.getInt(1); // Usa-se o índice 1 para indicar que é para buscar na primeira coluna, ou seja, a coluna de ID
            statement.close(); // fecha o recurso
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return maiorID;
    }

    // Método para deletar um produto por meio do seu ID
    public boolean deletarProdutoDB(int id){
        try{
            Statement statement = this.getConnection().createStatement();

            statement.executeUpdate("DELETE FROM tb_produto WHERE id_produto = " + id);

            statement.close();
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return true;
    }

    // Método para alterar dados de um produto por meio do id_produto
    public boolean alterarProdutoDB(Produto produto){

        String sql = "UPDATE tb_produto SET nome_produto = ?, "
                + "preco_unitario = ?, "
                + "unidade_de_medida = ?, "
                + "quantidade_estoque = ?, "
                + "quantidade_minima = ?, "
                + "quantidade_maxima = ?, "
                + "id_categoria = ? WHERE id_produto = ?";

        try{
            PreparedStatement statement = this.getConnection().prepareStatement(sql);

            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getPrecoUnitario());
            statement.setString(3, produto.getUnidadeMedida());
            statement.setInt(4, produto.getQuantidadeEmEstoque());
            statement.setInt(5, produto.getQuantidadeMinimaEmEstoque());
            statement.setInt(6, produto.getQuantidadeMaximaEmEstoque());
            statement.setInt(7, produto.getCategoria().getId());
            statement.setInt(8, produto.getId());

            statement.execute(); // consolida a instrução SQL
            statement.close();  // fecha o recurso

            return true;
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

    // Método para atualizar o estoque a partir de uma entrada ou saída
    public boolean atualizarQuantidadeEstoque(Produto produto){

        String sql = "UPDATE tb_produto SET quantidade_estoque = ? WHERE id_produto = ?";

        try{
            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            statement.setInt(1, produto.getQuantidadeEmEstoque());
            statement.setInt(2, produto.getId());

            statement.executeUpdate();
            statement.close();

            return true;
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }


}
