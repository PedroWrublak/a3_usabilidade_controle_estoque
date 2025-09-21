package dao;

import model.Categoria;
import model.Produto;
import java.sql.*;
import java.util.ArrayList;

public class CategoriaDao {

    public ArrayList<Categoria> listaDeCategoria = new ArrayList<>();

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

    public ArrayList<Categoria> getListaDeCategoria(){

        listaDeCategoria.clear(); // Limpa a lista

        try{
            Statement statement = this.getConnection().createStatement();

            // Executa o comando SQL
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tb_categoria");

            /* Recebe valores a partir do nome da coluna na tb_produto, adiciona
               cada objeto produto a lista de produto e percorre toda a tabela até
               a última linha
             */
            while(resultSet.next()){
                int id = resultSet.getInt("id_categoria");
                String nome = resultSet.getString("nome_categoria");
                String tamanho = resultSet.getString("tamanho");
                String embalagem = resultSet.getString("material_embalagem");

                Categoria categoria = new Categoria(id, nome, tamanho, embalagem);
                listaDeCategoria.add(categoria);
            }

            resultSet.close(); // fecha o recurso
            statement.close(); // fecha o recurso
        }

        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return listaDeCategoria;
    }

    public boolean insertCategoriaDB(Categoria categoria){

        // Comando SQL para inserir dados na tb_produto
        String sql = "INSERT INTO tb_categoria(id_categoria, nome_categoria, tamanho, material_embalagem) VALUES (?,?,?,?)";

        try{
            PreparedStatement statement = this.getConnection().prepareStatement(sql);

            statement.setInt(1, categoria.getId());
            statement.setString(2, categoria.getNome());
            statement.setString(3, categoria.getTamanho());
            statement.setString(4, categoria.getEmbalagem());

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
       a próxima linha inserida já venha com o id_categoria = maiorID + 1 (isso vai ser feito na classe Categoria)
    */
    public int getMaiorID(){
        int maiorID = 0;

        try {
            Statement statement = this.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(id_categoria) from tb_categoria");
            resultSet.next();
            maiorID = resultSet.getInt(1);
            statement.close(); // fecha o recurso
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return maiorID;
    }

    // Método para deletar uma categoria por meio do seu ID
    public boolean deletarCategoriaDB(int id){
        try{
            Statement statement = this.getConnection().createStatement();

            statement.executeUpdate("DELETE FROM tb_categoria WHERE id_categoria = " + id);

            statement.close();
        }
        catch(SQLException erro){
            System.out.println("Erro: " + erro);
        }
        return true;
    }

    // Método para alterar dados de uma categoria por meio do id_categoria
    public boolean alterarCategoriaDB(Categoria categoria){

        String sql = "UPDATE tb_categoria SET nome_categoria = ?, "
                + "tamanho = ?, "
                + "material_embalagem = ? WHERE id_categoria = ?";


        try{
            PreparedStatement statement = this.getConnection().prepareStatement(sql);

            statement.setString(1, categoria.getNome());
            statement.setString(2, categoria.getTamanho());
            statement.setString(3, categoria.getEmbalagem());
            statement.setInt(4, categoria.getId());


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