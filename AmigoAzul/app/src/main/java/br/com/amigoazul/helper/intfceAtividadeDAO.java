package br.com.amigoazul.helper;

import java.util.List;

import br.com.amigoazul.model.ListaAtividade;
import br.com.amigoazul.model.ListaUsuario;

/**
 * Criado por Lucas Pinheiro on 12/09/2019.
 */
//CRIA A INTERFACE PARA FAZER O DAO(Data Acess Object)
public interface intfceAtividadeDAO {
    public boolean salvar(ListaAtividade lista_atividade);
    public boolean atualizar(ListaAtividade lista_atividade);
    public boolean deletar(ListaAtividade lista_atividade);
    public List <ListaAtividade> listar();
}
