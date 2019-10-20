package br.com.amigoazul.model;

import java.io.Serializable;

/**
 * Criado por Lucas Pinheiro on 29/09/2019.
 */
public class ListaComunicacao implements Serializable {

    private Long id;
    private String caminhoFirebase;
    private String tipoComunic;
    private String textoFalar;
    private String textoFalar_MontarFrase;
    private byte[] foto;
    private String excluido;

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

    public String getTextoFalar_MontarFrase() {
        return textoFalar_MontarFrase;
    }

    public void setTextoFalar_MontarFrase(String textoFalar_MontarFrase) {
        this.textoFalar_MontarFrase = textoFalar_MontarFrase;
    }

    public String getExcluido() {
        return excluido;
    }

    public void setExcluido(String excluido) {
        this.excluido = excluido;
    }

}
