package br.com.amigoazul.helper;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import br.com.amigoazul.model.ListaComunicacao;
import br.com.amigoazul.model.ListaUsuario;

/**
 * Criado por Lucas Pinheiro on 27/10/2019.
 */
public class InserirDadosBD {
    public void INSERIR_DADOS_BANCO(Context contexto) {
        /**ESTANCIAR OUTRAS CLASSES*/
        ListaComunicacao listaComunicacao = new ListaComunicacao();
        ListaUsuario listaUsuario = new ListaUsuario();

        //verifica se existe comunicação cadastrado no BD
        ComunicacaoDAO comunicacaoDAO = new ComunicacaoDAO(contexto);
        UsuarioDAO usuarioDAO = new UsuarioDAO(contexto);

        List <ListaUsuario> SPLASH_listarUsuario;
        List <ListaComunicacao> SPLASH_listComunicSentimentos;
        List <ListaComunicacao> SPLASH_listComunicObjetos;
        List <ListaComunicacao> SPLASH_listComunicMontarFrase;
        //se a listagem for igual a 0 é inserido 2 registros na tabela COMUNICACAO por default
        SPLASH_listComunicSentimentos = comunicacaoDAO.listar_sentimentos();
        SPLASH_listComunicObjetos = comunicacaoDAO.listar_objetos();
        SPLASH_listComunicMontarFrase = comunicacaoDAO.listar_montarFrases();
        SPLASH_listarUsuario = usuarioDAO.listar();

        if (SPLASH_listarUsuario.size() == 0) {
            //INSERE UM USUARIO POR DEFAULT PARA TESTE
            listaUsuario.setNomeUsuario("LUCAS");
            listaUsuario.setDataNasc("24/03/1993");
            listaUsuario.setGrauTEA("NIVEL 2");
            listaUsuario.setEmail(null);
            listaUsuario.setSenha(null);
            listaUsuario.setExcluido("N");

            usuarioDAO.salvar(listaUsuario);
        }
        if (SPLASH_listComunicSentimentos.size() == 0) {
            //INSERE SENTIMENTOS
            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Sentimentos/AZ-22-10-2019-19:55:00.JPG");
            listaComunicacao.setTextoFalar("ESTOU COM DOR");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("SENTIMENTOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Sentimentos/AZ-22-10-2019-19:55:42.JPG");
            listaComunicacao.setTextoFalar("ESTOU COM FOME");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("SENTIMENTOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);
        }

        if (SPLASH_listComunicObjetos.size() == 0) {
            //INSERE OBJETOS
            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Objetos/AZ-22-10-2019-19:56:11.JPG");
            listaComunicacao.setTextoFalar("quero brincar com a bola");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("objetos");
            listaComunicacao.setExcluido("n");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Objetos/AZ-22-10-2019-19:56:42.JPG");
            listaComunicacao.setTextoFalar("QUERO VER TELEFISÃO");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("OBJETOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);
        }
        if (SPLASH_listComunicMontarFrase.size() == 0) {
            //INSERE MONTAR FRASE
            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-27-10-2019-10:07:24.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("EU");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-29-10-2019-14:14:15.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("NÃO");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-27-10-2019-10:09:28.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("ESTOU");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-27-10-2019-10:10:27.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("COMIDA");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-27-10-2019-11:26:12.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("FOME");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);
        }
        Toast.makeText(contexto, "DADOS PARA TESTES INSERIDOS COM SUCESSO", Toast.LENGTH_SHORT).show();
    }
}



