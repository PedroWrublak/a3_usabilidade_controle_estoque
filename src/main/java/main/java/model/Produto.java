package model;

import dao.ProdutoDao;

import java.util.ArrayList;

public class Produto {

    // Atributos de produto
    private int id;
    private String nome;
    private double precoUnitario;
    private String unidadeMedida;
    private int quantidadeEmEstoque;
    private int quantidadeMinimaEmEstoque;
    private int quantidadeMaximaEmEstoque;
    private Categoria categoria;
    private String nomeCategoria;
    private int quantidadeDeProdutoPorCategoria;
    private double valorTotalDeUmProduto;
    private double valorGeralDoEstoque;
    private ProdutoDao produtoDao = new ProdutoDao();

    // NoArgs constructor
    public Produto() {
    }

    public Produto(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // AllArgs constructor
    public Produto(int id, String nome, double precoUnitario, String unidadeMedida, int quantidadeEmEstoque, int quantidadeMinimaEmEstoque, int quantidadeMaximaEmEstoque, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.unidadeMedida = unidadeMedida;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.quantidadeMinimaEmEstoque = quantidadeMinimaEmEstoque;
        this.quantidadeMaximaEmEstoque = quantidadeMaximaEmEstoque;
        this.categoria = categoria;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getUnidadeMedida(){
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida){
        this.unidadeMedida = unidadeMedida;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public int getQuantidadeMinimaEmEstoque() {
        return quantidadeMinimaEmEstoque;
    }

    public void setQuantidadeMinimaEmEstoque(int quantidadeMinimaEmEstoque) {
        this.quantidadeMinimaEmEstoque = quantidadeMinimaEmEstoque;
    }

    public int getQuantidadeMaximaEmEstoque() {
        return quantidadeMaximaEmEstoque;
    }

    public void setQuantidadeMaximaEmEstoque(int quantidadeMaximaEmEstoque) {
        this.quantidadeMaximaEmEstoque = quantidadeMaximaEmEstoque;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public int getQuantidadeDeProdutoPorCategoria() {
        return quantidadeDeProdutoPorCategoria;
    }

    public void setQuantidadeDeProdutoPorCategoria(int quantidadeDeProdutoPorCategoria) {
        this.quantidadeDeProdutoPorCategoria = quantidadeDeProdutoPorCategoria;
    }

    public double getValorTotalDeUmProduto() {
        return valorTotalDeUmProduto;
    }

    public void setValorTotalDeUmProduto(double valorTotalDeUmProduto) {
        this.valorTotalDeUmProduto = valorTotalDeUmProduto;
    }

    public double getValorGeralDoEstoque() {
        return valorGeralDoEstoque;
    }

    public void setValorGeralDoEstoque(double valorGeralDoEstoque) {
        this.valorGeralDoEstoque = valorGeralDoEstoque;
    }

    // Retorna a lista de produtos
    public ArrayList<Produto> getListaDeProduto(){
        return produtoDao.getListaDeProduto();

    }

    // Retorna lista de preço para o relatório
    public ArrayList<Produto> getRelatorioListaDePreco(){
        return produtoDao.getRelatorioListaDePreco();
    }

    // Retorna lista da quantidade de produto distintos por categoria
    public ArrayList<Produto> getRelatorioQuantidadeDeProdutoPorCategoria(){
        return produtoDao.getRelatorioQuantidadeDeProdutoPorCategoria();
    }

    // Retorna o balanço físico financeiro do estoque
    public ArrayList<Produto> getBalancoFisicoFinanceiro(){
        return produtoDao.getBalancoFisicoFinanceiro();
    }

    // Retorna os produtos com a quantidade em estoque abaixo da quantidade mínima aceitável
    public ArrayList<Produto> getQuantidadeEstoqueAbaixoQuantidadeMinima(){
        return produtoDao.getQuantidadeEstoqueAbaixoQuantidadeMinima();
    }

    // Retorna os produtos com a quantidade em estoque acima da quantidade máxima aceitável
    public ArrayList<Produto> getListaAcimaQuantidadeMaxima(){
        return produtoDao.getListaAcimaQuantidadeMaxima();
    }

    // Retorna maior ID da base de dados
    public int getMaiorID(){
        return produtoDao.getMaiorID();
    }

    // Cadastra novo produto
    public boolean insertProdutoDB(String nome,
                                   double precoUnitario,
                                   String unidadeMedida,
                                   int quantidadeEmEstoque,
                                   int quantidadeMinimaEmEstoque,
                                   int quantidadeMaximaEmEstoque,
                                   Categoria categoria){

        int id = this.getMaiorID() + 1; // o produto inserido vai ter seu id automaticamente configurado como o maiorID que tinha no banco de dados + 1

        Produto produto = new Produto(id, nome, precoUnitario, unidadeMedida, quantidadeEmEstoque, quantidadeMinimaEmEstoque, quantidadeMaximaEmEstoque, categoria);
        produtoDao.insertProdutoDB(produto);
        return true;
    }

    // Deleta um produto específico pelo seu campo ID
    public boolean deletarProdutoDB(int id){
        produtoDao.deletarProdutoDB(id);
        return true;
    }

    // Edita um produto específico pelo seu campo ID
    public boolean alterarProdutoDB(int id,
                                    String nome,
                                    double precoUnitario,
                                    String unidadeMedida,
                                    int quantidadeEmEstoque,
                                    int quantidadeMinimaEmEstoque,
                                    int quantidadeMaximaEmEstoque,
                                    Categoria categoria){

        Produto produto = new Produto(id, nome, precoUnitario, unidadeMedida, quantidadeEmEstoque, quantidadeMinimaEmEstoque, quantidadeMaximaEmEstoque, categoria);
        produtoDao.alterarProdutoDB(produto);
        return true;
    }

    @Override
    public String toString() {
        return this.nome;
    }

}
