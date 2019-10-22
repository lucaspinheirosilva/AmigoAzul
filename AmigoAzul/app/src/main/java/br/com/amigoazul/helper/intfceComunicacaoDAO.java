package br.com.amigoazul.helper;

import java.util.List;

import br.com.amigoazul.model.ListaComunicacao;
import br.com.amigoazul.model.ListaUsuario;

/**
 * Criado por Lucas Pinheiro on 29/09/2019.
 */
public interface intfceComunicacaoDAO {
    public boolean salvar(ListaComunicacao lista_comunic);
    public boolean atualizar(ListaComunicacao lista_comunic);
    public boolean deletar(ListaComunicacao lista_comunic);
    public List <ListaComunicacao> listar_sentimentos();
    public List <ListaComunicacao> listar_objetos();
    public List <ListaComunicacao> listar_montarFrases();
}
