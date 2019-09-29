package br.com.amigoazul.model;

import java.io.Serializable;

/**
 * Criado por Lucas Pinheiro on 29/09/2019.
 */
public class ListaComunicacao implements Serializable {

    Long id;
    String caminhoFirebase;
    String tipoComunic;
    String textoFalar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaminhoFirebase() {
        return caminhoFirebase;
    }

    public void setCaminhoFirebase(String caminhoFirebase) {
        this.caminhoFirebase = caminhoFirebase;
    }

    public String getTipoComunic() {
        return tipoComunic;
    }

    public void setTipoComunic(String tipoComunic) {
        this.tipoComunic = tipoComunic;
    }

    public String getTextoFalar() {
        return textoFalar;
    }

    public void setTextoFalar(String textoFalar) {
        this.textoFalar = textoFalar;
    }

}
