package br.com.amigoazul.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

        try {
            escreve.insert(DBHelper.TABELA_COMUNICACAO, null, contentValues);
            Log.e("INFO_DB_COMUNICACAO:", "DADOS GRAVADOS COM SUCESSO");
        } catch (Exception erro) {
            Log.e("INFO_DB_COMUNICACAO:", "ERRO AO SALVAR DADOS" + erro.getMessage());
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
        String textoFalar = lista_comunic.getTextoFalar();

        try {
            String[] args = {lista_comunic.getId().toString()};
            escreve.delete(DBHelper.TABELA_COMUNICACAO, "id=?", args);

            Log.e("INFO_DB_COMUNICACAO:", "COMUNICACAO EXCLUIDO COM SUCESSO -->"+" ID: "+idUser+" NOME: "+caminhoFirebase+ " PASTA: "+tipo_comunic+" TEXTO: "+textoFalar);
        } catch (Exception erro) {
            Log.e("INFO_DB_COMUNICACAO:", "ERRO AO EXCLUIR COMUNICACAO -->"+" ID: "+idUser+" NOME: "+caminhoFirebase+" TEXTO: "+textoFalar+" PASTA: "+tipo_comunic+" -->"+erro.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List <ListaComunicacao> listar() {
        List <ListaComunicacao> listarcomunicacao = new ArrayList <>();

        String sqlListar = "SELECT * FROM " + DBHelper.TABELA_COMUNICACAO + " ; ";
        Cursor cursor = ler.rawQuery(sqlListar, null);

        while (cursor.moveToNext()) {
            ListaComunicacao listaComunicacao = new ListaComunicacao();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String caminhoFirebase = cursor.getString(cursor.getColumnIndex("caminho_fire"));
            String tipo_comunic = cursor.getString(cursor.getColumnIndex("tipo_comunic"));
            String textofalar = cursor.getString(cursor.getColumnIndex("texto_falar"));


            listaComunicacao.setId(id);
            listaComunicacao.setCaminhoFirebase(caminhoFirebase);
            listaComunicacao.setTipoComunic(tipo_comunic);
            listaComunicacao.setTextoFalar(textofalar);

            listarcomunicacao.add(listaComunicacao);
        }
        return listarcomunicacao;
    }
}
