package model;

import dao.MovimentacaoDao;

import java.time.LocalDate;
import java.util.ArrayList;

public class Movimentacao {

    private int id;
    private String tipoMovimentacao;  // entrada ou saída
    private int quantidadeDaMovimentacao;
    private LocalDate dataMovimentacao;
    private String nomeProduto;
    private Produto produto;
    private MovimentacaoDao movimentacaoDao = new MovimentacaoDao();

    // NoArgs constructor
    public Movimentacao() {
    }

    // AllArgs constructor
    public Movimentacao(int id, String tipoMovimentacao, int quantidadeDaMovimentacao, LocalDate dataMovimentacao, Produto produto) {
        this.id = id;
        this.tipoMovimentacao = tipoMovimentacao;
        this.quantidadeDaMovimentacao = quantidadeDaMovimentacao;
        this.dataMovimentacao = dataMovimentacao;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public int getQuantidadeDaMovimentacao() {
        return quantidadeDaMovimentacao;
    }

    public void setQuantidadeDaMovimentacao(int quantidadeDaMovimentacao) {
        this.quantidadeDaMovimentacao = quantidadeDaMovimentacao;
    }

    public LocalDate getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDate dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    // Retorna a lista de movimentações
    public ArrayList<Movimentacao> getListaDeMovimentacao(){
        return movimentacaoDao.getListaDeMovimentacao();
    }

    // Retorna maior ID da base de dados
    public int getMaiorID(){
        return movimentacaoDao.getMaiorID();
    }

    // Cadastra nova movimentacao
    public boolean insertMovimentacaoDB(String tipoMovimentacao,
                                        int quantidadeDaMovimentacao,
                                        LocalDate dataMovimentacao,
                                        Produto produto){

        int id = this.getMaiorID() + 1; // a movimentação inserida vai ter seu id automaticamente configurado como o maiorID que tinha no banco de dados + 1

        Movimentacao movimentacao = new Movimentacao(id, tipoMovimentacao, quantidadeDaMovimentacao, dataMovimentacao, produto);
        movimentacaoDao.insertMovimentacaoDB(movimentacao);
        return true;
    }

    // Deleta uma movimentação específica pelo seu campo ID
    public boolean deletarMovimentaçãoDB(int id){
        movimentacaoDao.deletarMovimentacaoDB(id);
        return true;
    }

    // Edita uma movimentação específica pelo seu campo ID
    public boolean alterarMovimentaçãoDB(int id,
                                         String tipoMovimentacao,
                                         int quantidadeDaMovimentacao,
                                         LocalDate dataMovimentacao,
                                         Produto produto){

        Movimentacao movimentacao = new Movimentacao(id, tipoMovimentacao, quantidadeDaMovimentacao, dataMovimentacao, produto);
        movimentacaoDao.alterarMovimentacaoDB(movimentacao);
        return true;
    }

    @Override
    public String toString() {
        return "Movimentacao{" +
                "id=" + id +
                ", tipoMovimentacao='" + tipoMovimentacao + '\'' +
                ", quantidadeDaMovimentacao=" + quantidadeDaMovimentacao +
                ", dataMovimentacao=" + dataMovimentacao +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", produto=" + produto +
                ", movimentacaoDao=" + movimentacaoDao +
                '}';
    }


}
