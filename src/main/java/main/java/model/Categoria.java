package model;

import dao.CategoriaDao;

import java.util.ArrayList;

public class Categoria {

    // Atributos de categoria
    private int id;
    private String nome;
    private String tamanho;
    private String embalagem;
    private CategoriaDao categoriaDao = new CategoriaDao();

    // NoArgs constructor
    public Categoria() {
    }
    // AllArgs constructor
    public Categoria(int id, String nome, String tamanho, String embalagem) {
        this.id = id;
        this.nome = nome;
        this.tamanho = tamanho;
        this.embalagem = embalagem;
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

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(String embalagem) {
        this.embalagem = embalagem;
    }

    // Retorna lista de categoria
    public ArrayList<Categoria> getListaDeCategoria(){
        return categoriaDao.getListaDeCategoria();
    }

    // Retorna o maior ID da base de dados
    public int getMaiorID(){
        return categoriaDao.getMaiorID();
    }

    // Cadastra nova categoria
    public boolean insertCategoriaDB(String nome, String tamanho, String embalagem){

        int id = this.getMaiorID() + 1;
        Categoria categoria = new Categoria(id, nome, tamanho, embalagem);
        categoriaDao.insertCategoriaDB(categoria);
        return true;
    }

    // Deleta uma categoria específica pelo campo ID
    public boolean deletarCategoriaDB(int id){
        categoriaDao.deletarCategoriaDB(id);
        return true;
    }

    // Altera uma categoria específica pelo campo ID
    public boolean alterarCategoriaDB(int id, String nome, String tamanho, String embalagem){

        Categoria categoria = new Categoria(id, nome, tamanho, embalagem);
        categoriaDao.alterarCategoriaDB(categoria);
        return true;
    }

    @Override
    public String toString(){
        return this.nome;  // Retorna apenas o nome para o JComboBox
    }
}
