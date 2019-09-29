package br.com.amigoazul.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Criado por Lucas Pinheiro on 12/09/2019.
 * <p>
 * CLASSE SERVE PARA CRIAR O BANCO DE DADOS
 * E AS TABELAS E TBM SERVB PARA FAZER UPDATES DE CAMPOS
 */
public class DBHelper extends SQLiteOpenHelper {

    Context contextToast;

    public static int VERSION = 1;
    public static String NOME_DB = "db_AMIGO_AZUL";
    public static String TABELA_USUARIO = "tb_CAD_USUARIO";
    public static String TABELA_COMUNICACAO = "tb_CAD_COMUNIC";

    public DBHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //UTILIZAR PARA CRIAR A 1º VEZ O BANCO DE DADOS

        /**CRIAR TABELA DE USUARIO*/
        String sql_cadUser = "CREATE TABLE IF NOT EXISTS " + TABELA_USUARIO
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nomeuser TEXT NOT NULL, " +
                " dataNasc TEXT NOT NULL, " +
                " grauTEA TEXT NOT NULL, " +
                " email TEXT, " +
                "senha TEXT ) ";

        /**CRIAR TABELA DE COMUNICACAO*/
        String sql_cadComunic = "CREATE TABLE IF NOT EXISTS " + TABELA_COMUNICACAO
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " caminho_fire TEXT NOT NULL, " +
                " tipo_comunic TEXT NOT NULL, " +
                " texto_falar TEXT NOT NULL) ";

        try {
            db.execSQL(sql_cadUser);
            Log.i("DB_INFO:", "CADASTRO USUARIO ---BANCO CRIADO COM SUCESSO");
            db.execSQL(sql_cadComunic);
            Log.i("DB_INFO:", "CADASTRO COMUNICACAO ---BANCO CRIADO COM SUCESSO");

        } catch (Exception erro) {
            Toast.makeText(contextToast, "ERRO AO CRIAR BANCO DE DADOS:" + erro.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //UTILIZADO para atualizar tabelas ou APP

    }
}
