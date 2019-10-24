package br.com.amigoazul.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import br.com.amigoazul.model.ListaComunicacao;


/**
 * Criado por Lucas Pinheiro on 29/09/2019.
 */
public class ComunicacaoDAO implements intfceComunicacaoDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase ler;

    public ComunicacaoDAO(Context context) {

        DBHelper db = new DBHelper(context);
        escreve = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(ListaComunicacao lista_comunic) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("caminho_fire", lista_comunic.getCaminhoFirebase());
        contentValues.put("tipo_comunic", lista_comunic.getTipoComunic());
        contentValues.put("texto_falar", lista_comunic.getTextoFalar());
        contentValues.put("texto_falar_montarfrase", lista_comunic.getTextoFalar_MontarFrase());
        contentValues.put("excluido", lista_comunic.getExcluido());

        try {
            escreve.insert(DBHelper.TABELA_COMUNICACAO, null, contentValues);
            Log.e("INFO_DB_COMUNICACAO:", "COMUNICACAO GRAVADA COM SUCESSO");
        } catch (Exception erro) {
            Log.e("INFO_DB_COMUNICACAO:", "ERRO AO SALVAR COMUNICACAO" + erro.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean atualizar(ListaComunicacao lista_comunic) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("caminho_fire", lista_comunic.getCaminhoFirebase());
        contentValues.put("tipo_comunic", lista_comunic.getTipoComunic());
        contentValues.put("texto_falar", lista_comunic.getTextoFalar());
        contentValues.put("texto_falar_montarfrase", lista_comunic.getTextoFalar_MontarFrase());
        contentValues.put("excluido", lista_comunic.getExcluido());

        try {
            String[] args = {lista_comunic.getId().toString()};
            escreve.update(DBHelper.TABELA_COMUNICACAO, contentValues, "id=?", args);

            Log.e("INFO_DB_COMUNICACAO:", "COMUNICACAO ATUALIZADOS COM SUCESSO");
        } catch (Exception erro) {
            Log.e("INFO_DB_COMUNICACAO:", "ERRO AO ATUALIZAR COMUNICACAO" + erro.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(ListaComunicacao lista_comunic) {
        Long idUser = lista_comunic.getId();
        String caminhoFirebase = lista_comunic.getCaminhoFirebase();
        String tipo_comunic = lista_comunic.getTipoComunic();

        try {
            String[] args = {lista_comunic.getId().toString()};
            escreve.delete(DBHelper.TABELA_COMUNICACAO, "id=?", args);

            Log.e("INFO_DB_COMUNICACAO:", "COMUNICACAO EXCLUIDO COM SUCESSO -->"+" ID: "+idUser+" NOME: "+caminhoFirebase+ " PASTA: "+tipo_comunic+" TEXTO: ");
        } catch (Exception erro) {
            Log.e("INFO_DB_COMUNICACAO:", "ERRO AO EXCLUIR COMUNICACAO -->"+" ID: "+idUser+" NOME: "+caminhoFirebase+"PASTA: "+tipo_comunic+" -->"+erro.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List <ListaComunicacao> listar_sentimentos() {
        List <ListaComunicacao> listarSentimentos = new ArrayList <>();

        String sqlListar = "SELECT * FROM " + DBHelper.TABELA_COMUNICACAO + " where excluido = 'n' and tipo_comunic='sentimentos' ";
        Cursor cursor = ler.rawQuery(sqlListar, null);

        while (cursor.moveToNext()) {
            ListaComunicacao listaComunicacao = new ListaComunicacao();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String caminhoFirebase = cursor.getString(cursor.getColumnIndex("caminho_fire"));
            String tipo_comunic = cursor.getString(cursor.getColumnIndex("tipo_comunic"));
            String textofalar = cursor.getString(cursor.getColumnIndex("texto_falar"));
            String textofalarMontarFrase = cursor.getString(cursor.getColumnIndex("texto_falar_montarfrase"));
            String excluido = cursor.getString(cursor.getColumnIndex("excluido"));


            listaComunicacao.setId(id);
            listaComunicacao.setCaminhoFirebase(caminhoFirebase);
            listaComunicacao.setTipoComunic(tipo_comunic);
            listaComunicacao.setTextoFalar(textofalar);
            listaComunicacao.setTextoFalar_MontarFrase(textofalarMontarFrase);
            listaComunicacao.setExcluido(excluido);

            listarSentimentos.add(listaComunicacao);
        }
        return listarSentimentos;
    }

    @Override
    public List <ListaComunicacao> listar_objetos() {
        List <ListaComunicacao> listarObjeto = new ArrayList <>();

        String sqlListar = "SELECT * FROM " + DBHelper.TABELA_COMUNICACAO + " where excluido = 'n' and tipo_comunic='objetos'";
        Cursor cursor = ler.rawQuery(sqlListar, null);

        while (cursor.moveToNext()) {
            ListaComunicacao listaComunicacao = new ListaComunicacao();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String caminhoFirebase = cursor.getString(cursor.getColumnIndex("caminho_fire"));
            String tipo_comunic = cursor.getString(cursor.getColumnIndex("tipo_comunic"));
            String textofalar = cursor.getString(cursor.getColumnIndex("texto_falar"));
            String textofalarMontarFrase = cursor.getString(cursor.getColumnIndex("texto_falar_montarfrase"));
            String excluido = cursor.getString(cursor.getColumnIndex("excluido"));


            listaComunicacao.setId(id);
            listaComunicacao.setCaminhoFirebase(caminhoFirebase);
            listaComunicacao.setTipoComunic(tipo_comunic);
            listaComunicacao.setTextoFalar(textofalar);
            listaComunicacao.setTextoFalar_MontarFrase(textofalarMontarFrase);
            listaComunicacao.setExcluido(excluido);

            listarObjeto.add(listaComunicacao);
        }
        return listarObjeto;
    }

    @Override
    public List <ListaComunicacao> listar_montarFrases() {
        List <ListaComunicacao> listarMontarFrase = new ArrayList <>();

        String sqlListar = "SELECT * FROM " + DBHelper.TABELA_COMUNICACAO + " where excluido = 'n' and tipo_comunic='montar frase' ";
        Cursor cursor = ler.rawQuery(sqlListar, null);

        while (cursor.moveToNext()) {
            ListaComunicacao listaComunicacao = new ListaComunicacao();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String caminhoFirebase = cursor.getString(cursor.getColumnIndex("caminho_fire"));
            String tipo_comunic = cursor.getString(cursor.getColumnIndex("tipo_comunic"));
            String textofalar = cursor.getString(cursor.getColumnIndex("texto_falar"));
            String textofalarMontarFrase = cursor.getString(cursor.getColumnIndex("texto_falar_montarfrase"));
            String excluido = cursor.getString(cursor.getColumnIndex("excluido"));


            listaComunicacao.setId(id);
            listaComunicacao.setCaminhoFirebase(caminhoFirebase);
            listaComunicacao.setTipoComunic(tipo_comunic);
            listaComunicacao.setTextoFalar(textofalar);
            listaComunicacao.setTextoFalar_MontarFrase(textofalarMontarFrase);
            listaComunicacao.setExcluido(excluido);

            listarMontarFrase.add(listaComunicacao);
        }
        return listarMontarFrase;
    }



}
