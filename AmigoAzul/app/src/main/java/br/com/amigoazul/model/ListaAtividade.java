package br.com.amigoazul.model;

import java.io.Serializable;

/**
 * Criado por Lucas Pinheiro on 29/09/2019.
 */
public class ListaAtividade implements Serializable {

    Long id;
    String nomeAtividade;
    String nivelAtividade;
    String horaAtividade;
    String nomeUsuario;
    String tipoAtividade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public String getNivelAtividade() {
        return nivelAtividade;
    }

    public void setNivelAtividade(String nivelAtividade) {
        this.nivelAtividade = nivelAtividade;
    }

    public String getHoraAtividade() {
        return horaAtividade;
    }

    public void setHoraAtividade(String horaAtividade) {
        this.horaAtividade = horaAtividade;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

}
