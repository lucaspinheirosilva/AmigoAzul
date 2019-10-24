package br.com.amigoazul.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.amigoazul.model.ListaUsuario;

/**
 * Criado por Lucas Pinheiro on 12/09/2019.
 */
public class UsuarioDAO implements intfceUsuarioDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase ler;

    public UsuarioDAO(Context context) {

        DBHelper db = new DBHelper(context);
        escreve = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }


    @Override
    public boolean salvar(ListaUsuario lista_usuario) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("nomeuser", lista_usuario.getNomeUsuario());
        contentValues.put("dataNasc", lista_usuario.getDataNasc());
        contentValues.put("grauTEA", lista_usuario.getGrauTEA());
        contentValues.put("excluido", lista_usuario.getExcluido());
        contentValues.put("email", lista_usuario.getEmail());
        contentValues.put("senha", lista_usuario.getSenha());

        try {
            escreve.insert(DBHelper.TABELA_USUARIO, null, contentValues);
            Log.i("INFO_DB_USUARIO:", "USUARIO GRAVADO COM SUCESSO");
        } catch (Exception erro) {
            Log.i("INFO_DB_USUARIO:", "ERRO AO SALVAR USUARIO" + erro.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean atualizar(ListaUsuario lista_usuario) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("nomeuser", lista_usuario.getNomeUsuario());
        contentValues.put("dataNasc", lista_usuario.getDataNasc());
        contentValues.put("grauTEA", lista_usuario.getGrauTEA());
        contentValues.put("excluido", lista_usuario.getExcluido());
        contentValues.put("email", lista_usuario.getEmail());
        contentValues.put("senha", lista_usuario.getSenha());
        try {
            String[] args = {lista_usuario.getId().toString()};
            escreve.update(DBHelper.TABELA_USUARIO, contentValues, "id=?", args);

            Log.e("INFO_DB_USUARIO:", "USUARIO ATUALIZADOS COM SUCESSO");
        } catch (Exception erro) {
            Log.e("INFO_DB_USUARIO:", "ERRO AO ATUALIZAR USUARIO" + erro.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(ListaUsuario lista_usuario) {
        Long idUser = lista_usuario.getId();
        String nomeUser = lista_usuario.getNomeUsuario();
        String GrauTea = lista_usuario.getGrauTEA();

        try {
            String[] args = {lista_usuario.getId().toString()};
            escreve.delete(DBHelper.TABELA_USUARIO, "id=?", args);

            Log.e("INFO_DB_USUARIO:", "USUARIO EXCLUIDO COM SUCESSO -->" + " ID: " + idUser + " NOME: " + nomeUser + " MARCA: " + GrauTea);
        } catch (Exception erro) {
            Log.e("INFO_DB_USUARIO:", "ERRO AO EXCLUIR USUARIO -->" + " ID: " + idUser + " NOME: " + nomeUser + " MARCA: " + GrauTea + "-->" + erro.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List <ListaUsuario> listar() {
        List <ListaUsuario> listaruser = new ArrayList <>();

        String sqlListar = "SELECT * FROM " + DBHelper.TABELA_USUARIO + " where excluido='n' ; ";
        Cursor cursor = ler.rawQuery(sqlListar, null);

        while (cursor.moveToNext()) {
            ListaUsuario listaUsuario = new ListaUsuario();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nomeuser = cursor.getString(cursor.getColumnIndex("nomeuser"));
            String excluido = cursor.getString(cursor.getColumnIndex("excluido"));
            String dataNasc = cursor.getString(cursor.getColumnIndex("dataNasc"));
            String GrauTea = cursor.getString(cursor.getColumnIndex("grauTEA"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String senha = cursor.getString(cursor.getColumnIndex("senha"));

            listaUsuario.setId(id);
            listaUsuario.setNomeUsuario(nomeuser);
            listaUsuario.setDataNasc(dataNasc);
            listaUsuario.setGrauTEA(GrauTea);
            listaUsuario.setExcluido(excluido);
            listaUsuario.setEmail(email);
            listaUsuario.setSenha(senha);

            listaruser.add(listaUsuario);
        }
        return listaruser;
    }



}
