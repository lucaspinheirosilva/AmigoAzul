package br.com.amigoazul.helper;

import java.util.List;

import br.com.amigoazul.model.ListaUsuario;

/**
 * Criado por Lucas Pinheiro on 12/09/2019.
 */
//CRIA A INTERFACE PARA FAZER O DAO(Data Acess Object)
public interface intfceUsuarioDAO {
    public boolean salvar(ListaUsuario lista_usuario);
    public boolean atualizar(ListaUsuario lista_usuario);
    public boolean deletar(ListaUsuario lista_usuario);
    public List <ListaUsuario> listar();
}
