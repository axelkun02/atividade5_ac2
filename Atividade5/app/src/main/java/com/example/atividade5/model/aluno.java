package com.example.atividade5.model;

public class aluno {

    private  int id;
    private int ra;
    private String nome;
    private String cep;
    private String logadouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;


    public int getRa() {
        return ra;
    }

    public void setRa(int ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogadouro() {
        return logadouro;
    }

    public void setLogadouro(String logadouro) {
        this.logadouro = logadouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public aluno(int ra, String nome, String cep, String logadouro, String complemento, String bairro, String cidade, String uf, int id ) {
        this.ra = ra;
        this.nome = nome;
        this.logadouro = logadouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.id = id;
    }
    public aluno() {

        }

    }








