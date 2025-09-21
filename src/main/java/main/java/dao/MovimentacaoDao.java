package dao;

import model.Movimentacao;
import model.Produto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MovimentacaoDao {

    public ArrayList<Movimentacao> listaDeMovimentacao = new ArrayList<>();

    // Método para conexão com banco de dados
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

    public ArrayList<Movimentacao> getListaDeMovimentacao(){

        listaDeMovimentacao.clear(); // Limpa a lista

        try{
            Statement statement = this.getConnection().createStatement();

            // Executa o comando SQL
            ResultSet resultSet = statement.executeQuery("SELECT m.id_movimentacao, m.tipo_movimentacao, " +
                    "m.quantidade_da_movimentacao, m.data_movimentacao, " +
                    "p.nome_produto " +
                    "FROM tb_movimentacao m JOIN tb_produto p ON m.id_produto = p.id_produto");

            /* Recebe valores da tb_movimentacao, adiciona
               cada objeto movimentacao a lista de movimentacao e percorre toda a tabela até
               a última linha
             */
            while(resultSet.next()){
                int id = resultSet.getInt("id_movimentacao");
                String tipoMovimentacao = resultSet.getString("tipo_movimentacao");
                int quantidadeDaMovimentacao = resultSet.getInt("quantidade_da_movimentacao");
                LocalDate dataMovimentacao = resultSet.getDate("data_movimentacao").toLocalDate(); // usa o .toLocalDate() para retornar um java.time.LocalDate ao inves de um java.sql.LocalDate
                String nomeProduto = resultSet.getString("nome_produto");

                Movimentacao movimentacao = new Movimentacao();
                // É necessário setar todas as variáveis para não retornar um erro de NullPointerException
                movimentacao.setId(id);
                movimentacao.setTipoMovimentacao(tipoMovimentacao);
                movimentacao.setQuantidadeDaMovimentacao(quantidadeDaMovimentacao);
                movimentacao.setDataMovimentacao(dataMovimentacao);
                movimentacao.setNomeProduto(nomeProduto);

                listaDeMovimentacao.add(movimentacao);


            }
            resultSet.close(); // fecha o recurso
            statement.close(); // fecha o recurso
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return listaDeMovimentacao;
    }

    // Método para inserção dados
    public boolean insertMovimentacaoDB(Movimentacao movimentacao){

        // Comando SQL para inserir dados na tb_movimentacao
        String sql = "INSERT INTO tb_movimentacao(id_movimentacao, tipo_movimentacao, quantidade_da_movimentacao, data_movimentacao, id_produto) VALUES (?,?,?,?,?)";

        try{
            PreparedStatement statement = this.getConnection().prepareStatement(sql);

            statement.setInt(1, movimentacao.getId());
            statement.setString(2, movimentacao.getTipoMovimentacao());
            statement.setInt(3, movimentacao.getQuantidadeDaMovimentacao());
            statement.setDate(4, java.sql.Date.valueOf(movimentacao.getDataMovimentacao())); // Converte a data para um java.sql.Date que é a data que o banco de dados entende
            statement.setInt(5, movimentacao.getProduto().getId());  // Nesse caso, é preciso colocar o getId() para extrair apenas o id do produto que é o que precisamos


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
       a próxima linha inserida já venha com o id_movimentacao = maiorID + 1 (isso vai ser feito na classe Movimentacao)
    */
    public int getMaiorID(){
        int maiorID = 0;

        try {
            Statement statement = this.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(id_movimentacao) from tb_movimentacao");
            resultSet.next();
            maiorID = resultSet.getInt(1); // Usa-se o índice 1 para indicar que é para buscar na primeira coluna, ou seja, a coluna de ID
            statement.close(); // fecha o recurso
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return maiorID;
    }

    // Método para deletar uma movimentação por meio do seu ID
    public boolean deletarMovimentacaoDB(int id){
        try{
            Statement statement = this.getConnection().createStatement();

            statement.executeUpdate("DELETE FROM tb_movimentacao WHERE id_movimentacao = " + id);

            statement.close();
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return true;
    }

    // Método para alterar dados de uma movimentacao por meio do id_movimentacao
    public boolean alterarMovimentacaoDB(Movimentacao movimentacao){

        String sql = "UPDATE tb_movimentacao SET tipo_movimentacao = ?, "
                + "quantidade_da_movimentacao = ?, "
                + "data_movimentacao = ?, "
                + "id_produto = ? WHERE id_movimentacao = ?";


        try{
            PreparedStatement statement = this.getConnection().prepareStatement(sql);

            statement.setString(1, movimentacao.getTipoMovimentacao());
            statement.setInt(2, movimentacao.getQuantidadeDaMovimentacao());
            statement.setDate(3, java.sql.Date.valueOf(movimentacao.getDataMovimentacao()));
            statement.setInt(4, movimentacao.getProduto().getId());
            statement.setInt(5, movimentacao.getId());

            statement.execute(); // consolida a instrução SQL
            statement.close();  // fecha o recurso

            return true;
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
            throw new RuntimeException(erro);
        }
    }

}