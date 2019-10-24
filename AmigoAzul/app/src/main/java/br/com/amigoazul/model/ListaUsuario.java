package br.com.amigoazul.model;

import java.io.Serializable;

/**
 * Criado por Lucas Pinheiro on 12/09/2019.
  */

//**
// CLASSE CRIADA APANAS PARA CONTER OS GETTER AND SETTERS
//

public class ListaUsuario implements Serializable {

    Long id;
    String nomeUsuario;
    String dataNasc;
    String grauTEA;
    String excluido;
    String email;
    String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getGrauTEA() {
        return grauTEA;
    }

    public void setGrauTEA(String grauTEA) {
        this.grauTEA = grauTEA;
    }

    public String getExcluido() {
        return excluido;
    }

    public void setExcluido(String excluido) { this.excluido = excluido;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
