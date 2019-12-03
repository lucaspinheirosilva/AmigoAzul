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

       /* if (SPLASH_listarUsuario.size() == 0) {
            //INSERE UM USUARIO POR DEFAULT PARA TESTE
            listaUsuario.setNomeUsuario("LUCAS");
            listaUsuario.setDataNasc("24/03/1993");
            listaUsuario.setGrauTEA("NIVEL 2");
            listaUsuario.setEmail(null);
            listaUsuario.setSenha(null);
            listaUsuario.setExcluido("N");

            usuarioDAO.salvar(listaUsuario);
        }*/
        if (SPLASH_listComunicSentimentos.size() == 0) {
            //INSERE SENTIMENTOS
            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Sentimentos/AZ-02-12-2019-09:50:10.JPG");
            listaComunicacao.setTextoFalar("ESTE SOM ME INCOMODA");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("SENTIMENTOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Sentimentos/AZ-02-12-2019-09:50:45.JPG");
            listaComunicacao.setTextoFalar("ESTOU COM DOR DE CABEÇA");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("SENTIMENTOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Sentimentos/AZ-02-12-2019-09:51:38.JPG");
            listaComunicacao.setTextoFalar("ESTOU COM SONO");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("SENTIMENTOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Sentimentos/AZ-02-12-2019-09:51:55.JPG");
            listaComunicacao.setTextoFalar("ESTOU TRISTE");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("SENTIMENTOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Sentimentos/AZ-02-12-2019-09:52:10.JPG");
            listaComunicacao.setTextoFalar("EU TE AMO");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("SENTIMENTOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Sentimentos/AZ-02-12-2019-09:52:24.JPG");
            listaComunicacao.setTextoFalar("FELIZ NATAL");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("SENTIMENTOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Sentimentos/AZ-02-12-2019-09:52:40.JPG");
            listaComunicacao.setTextoFalar("ISSO FOI HILÁRIO");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("SENTIMENTOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);
        }

        if (SPLASH_listComunicObjetos.size() == 0) {
            //INSERE OBJETOS
            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Objetos/AZ-02-12-2019-11:31:43.JPG");
            listaComunicacao.setTextoFalar("BRASIL");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("OBJETOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Objetos/AZ-02-12-2019-11:32:02.JPG");
            listaComunicacao.setTextoFalar("CAMINHÃO");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("OBJETOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Objetos/AZ-02-12-2019-11:34:49.JPG");
            listaComunicacao.setTextoFalar("CASA");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("OBJETOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Objetos/AZ-02-12-2019-11:35:11.JPG");
            listaComunicacao.setTextoFalar("HOSPITAL");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("OBJETOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Objetos/AZ-02-12-2019-11:35:31.JPG");
            listaComunicacao.setTextoFalar("ITÁLIA");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("OBJETOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Objetos/AZ-02-12-2019-11:35:48.JPG");
            listaComunicacao.setTextoFalar("LIVRO");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("OBJETOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Objetos/AZ-02-12-2019-11:34:33.JPG");
            listaComunicacao.setTextoFalar("CANADÁ");
            listaComunicacao.setTextoFalar_MontarFrase(null);
            listaComunicacao.setTipoComunic("OBJETOS");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

        }
        if (SPLASH_listComunicMontarFrase.size() == 0) {
            //INSERE MONTAR FRASE
            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-02-12-2019-12:33:48.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("DORMIR");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-02-12-2019-12:34:05.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("ESCOVAR O DENTE");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-02-12-2019-12:34:22.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("ESTUDAR");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-02-12-2019-12:34:41.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("EU QUERO");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-02-12-2019-12:35:11.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("JANTAR");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-02-12-2019-12:35:32.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("NÃO QUERO");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("/storage/emulated/0/AmigoAzul_Fotos/Montar_Frase/AZ-02-12-2019-12:35:52.JPG");
            listaComunicacao.setTextoFalar(null);
            listaComunicacao.setTextoFalar_MontarFrase("PASSEAR");
            listaComunicacao.setTipoComunic("MONTAR FRASE");
            listaComunicacao.setExcluido("N");

            comunicacaoDAO.salvar(listaComunicacao);
        }
       // Toast.makeText(contexto, "DADOS PARA TESTES INSERIDOS COM SUCESSO", Toast.LENGTH_SHORT).show();
    }
}



