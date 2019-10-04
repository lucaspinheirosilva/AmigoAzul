package br.com.amigoazul.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.amigoazul.model.ListaAtividade;
import br.com.amigoazul.model.ListaComunicacao;


/**
 * Criado por Lucas Pinheiro on 29/09/2019.
 */
public class AtividadeDAO implements intfceAtividadeDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase ler;

    public AtividadeDAO(Context context) {

        DBHelper db = new DBHelper(context);
        escreve = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(ListaAtividade lista_atividade) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome_atividade", lista_atividade.getNomeAtividade());
        contentValues.put("nivel_atividade", lista_atividade.getNivelAtividade());
        contentValues.put("hora_atividade", lista_atividade.getHoraAtividade());
        contentValues.put("nome_usuario", lista_atividade.getNomeUsuario());
        contentValues.put("tipo_atividade", lista_atividade.getTipoAtividade());

        try {
            escreve.insert(DBHelper.TABELA_COMUNICACAO, null, contentValues);
            Log.e("INFO_DB_ATIVIDADE:", "ATIVIDADE GRAVADA COM SUCESSO");
        } catch (Exception erro) {
            Log.e("INFO_DB_ATIVIDADE:", "ERRO AO SALVAR ATIVIDADE" + erro.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean atualizar(ListaAtividade lista_atividade) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome_atividade", lista_atividade.getNomeAtividade());
        contentValues.put("nivel_atividade", lista_atividade.getNivelAtividade());
        contentValues.put("hora_atividade", lista_atividade.getHoraAtividade());
        contentValues.put("nome_usuario", lista_atividade.getNomeAtividade());
        contentValues.put("tipo_atividade", lista_atividade.getTipoAtividade());

        try {
            String[] args = {lista_atividade.getId().toString()};
            escreve.update(DBHelper.TABELA_COMUNICACAO, contentValues, "id=?", args);

            Log.e("INFO_DB_ATIVIDADE:", "ATIVIDADE ATUALIZADOS COM SUCESSO");
        } catch (Exception erro) {
            Log.e("INFO_DB_ATIVIDADE:", "ERRO AO ATUALIZAR ATIVIDADE" + erro.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(ListaAtividade lista_atividade) {
        //impossivel a exclusao de uma atividade ou registro gerado por uma atividade
        return true;
    }

    @Override
    public List <ListaAtividade> listar() {
        List <ListaAtividade> listaAtividades = new ArrayList <>();

        String sqlListar = "SELECT * FROM " + DBHelper.TABELA_COMUNICACAO + " ; ";
        Cursor cursor = ler.rawQuery(sqlListar, null);

        while (cursor.moveToNext()) {
            ListaAtividade listaatividade = new ListaAtividade();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nomeAtividade = cursor.getString(cursor.getColumnIndex("nome_atividade"));
            String nivelAtividade = cursor.getString(cursor.getColumnIndex("nivel_atividade"));
            String horaAtividade = cursor.getString(cursor.getColumnIndex("hora_atividade"));
            String nomeUsuario = cursor.getString(cursor.getColumnIndex("nome_usuario"));
            String tipoAtividade = cursor.getString(cursor.getColumnIndex("tipo_atividade"));


            listaatividade.setId(id);
            listaatividade.setHoraAtividade(horaAtividade);
            listaatividade.setNivelAtividade(nivelAtividade);
            listaatividade.setNomeAtividade(nomeAtividade);
            listaatividade.setTipoAtividade(tipoAtividade);
            listaatividade.setNomeUsuario(nomeUsuario);

            listaAtividades.add(listaatividade);
        }
        return listaAtividades;
    }
}