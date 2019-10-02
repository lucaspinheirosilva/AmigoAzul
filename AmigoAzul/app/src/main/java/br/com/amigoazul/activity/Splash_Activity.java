package br.com.amigoazul.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.helper.ComunicacaoDAO;
import br.com.amigoazul.helper.UsuarioDAO;
import br.com.amigoazul.model.ListaComunicacao;
import br.com.amigoazul.model.ListaUsuario;

/**
 * TUTORIAL TELA SE SPLASHT
 * TUTORIAL DOWNLOAD ARQUIVO FIREBASE P/ CELULAR
 * TUTORIAL DOWNLOAD ARQUIVO FIREBASE P/ CELULAR
 */
//https://www.devmedia.com.br/como-criar-telas-de-abertura-no-android/33256

/**TUTORIAL DOWNLOAD ARQUIVO FIREBASE P/ CELULAR*/
// https://www.youtube.com/watch?v=SmXGlv7QEO0
// https://grokonez.com/android/firebase-storage-get-list-files-display-image-firebase-ui-database-android
// https://grokonez.com/android/firebase-storage-download-files-to-memory-local-file-android
// https://firebase.google.com/docs/storage/android/list-files

/**PERMISSOES DO ANDROID**/
// https://www.androidauthority.com/android-app-permissions-explained-642452/
// https://developer.android.com/training/permissions/requesting?hl=pt-br
public class Splash_Activity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    final static int Const_WRITE_EXTERNAL_STORAGE = 001;

    /***VARIAVEIS FIREBASE*/
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;


    /**CRIAR DIRETORIO NO CELULAR DO USUARIO*/
    File meuDiretorio = new File(Environment.getExternalStorageDirectory(), "AmigoAzul_Fotos");
    File meuDirSentimentos = new File(Environment.getExternalStorageDirectory(), "AmigoAzul_Fotos/Sentimentos");
    File meuDirObjetos = new File(Environment.getExternalStorageDirectory(), "AmigoAzul_Fotos/Objetos");
    File meuDirMontarFreses = new File(Environment.getExternalStorageDirectory(), "AmigoAzul_Fotos/Montar_Frase");

    /***ALERTDIALOG*/
    private AlertDialog alerta;

    /**ESTANCIAR OUTRAS CLASSES*/
    ListaComunicacao listaComunicacao = new ListaComunicacao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("permissao", "permissao ja esta liberada");

                /**CRIAR PASTA PARA SALVAR AS FOTOS**/
                if (!meuDiretorio.exists()) {
                    meuDiretorio.mkdirs();
                    meuDirMontarFreses.mkdirs();
                    meuDirObjetos.mkdirs();
                    meuDirSentimentos.mkdirs();
                    Toast.makeText(getApplicationContext(), "DIRETORIO CRIADO COM SUCESSO", Toast.LENGTH_SHORT).show();

                    if (!meuDirSentimentos.exists()) {
                        meuDirSentimentos.mkdirs();
                        Toast.makeText(getApplicationContext(), "DIRETORIO SENTIMENTOS CRIADO COM SUCESSO", Toast.LENGTH_SHORT).show();
                    }
                    if (!meuDirObjetos.exists()) {
                        meuDirObjetos.mkdirs();
                        Toast.makeText(getApplicationContext(), "DIRETORIO OBJETOS CRIADO COM SUCESSO", Toast.LENGTH_SHORT).show();
                    }
                    if (!meuDirMontarFreses.exists()) {
                        meuDirMontarFreses.mkdirs();
                        Toast.makeText(getApplicationContext(), "DIRETORIO MONTAR FRASES CRIADO COM SUCESSO", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "DIRETORIO JA EXISTE", Toast.LENGTH_SHORT).show();
                    meuDiretorio.getAbsolutePath().toString();
                }

                //INSERIR DADOS DE COMUNICACAO NO BD
                INSERIR_DADOS_BANCO();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        /**verifica se existe usuario cadastrado no BD**/
                        List <ListaUsuario> SPLASH_listusers = new ArrayList <>();
                        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());

                        SPLASH_listusers = usuarioDAO.listar();
                        if (SPLASH_listusers.size() > 0) {
                            Toast.makeText(getApplicationContext(), "USUARIO ENCONTRADO", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(Splash_Activity.this, ListarUsuario.class);
                            String blockSplash = "bloqueadoSplash";
                            i.putExtra("BLOQUEIO_SPLASH", blockSplash);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "NADA ENCONTRADO", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Splash_Activity.this, Introducao_Activity.class);
                            startActivity(i);
                            // Fecha esta activity
                            finish();
                        }
                    }
                }, SPLASH_TIME_OUT);

                /**chama o metodo para download*/
                DOWNLOAD();
            } else {
                requestPermission();
            }
        }
    }

    /**
     * METODO TESTE PARA BAIXAR IMAGENS FIREBASE
     */
    public void DOWNLOAD() {
        storageReference = firebaseStorage.getInstance().getReference();
        //ref = storageReference.child("AreaTest.jpg");

        StorageReference imageReference;
        imageReference = FirebaseStorage.getInstance().getReference().child("Sentimentos");
        StorageReference fileRef;
        fileRef = null;
        ListaComunicacao listaComunicacao = new ListaComunicacao();
        fileRef = imageReference.child("TESTE.jpg");
        //todo:continuar apartir daqui
        ComunicacaoDAO comunicacaoDAO = new ComunicacaoDAO(getApplicationContext());


        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener <Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                DOWNLOADFILES(Splash_Activity.this,
                        "AreaTest",
                        ".jpg",
                        meuDiretorio.getAbsolutePath(),
                        url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), "Erro ao baixar Image:" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void DOWNLOADFILES(Context contexto,
                              String nomeArquivo,
                              String extencaoArquivo,
                              String diretorioDestino,
                              String url) {

        DownloadManager downloadManager =
                (DownloadManager) contexto.getSystemService(contexto.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(diretorioDestino, nomeArquivo + extencaoArquivo);

        downloadManager.enqueue(request);
    }

    private boolean checkPermission() {

        int result_write = ContextCompat.checkSelfPermission(Splash_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (result_write == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Splash_Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Const_WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Const_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    /**CRIAR PASTA PARA SALVAR AS FOTOS**/
                    if (!meuDiretorio.exists()) {
                        meuDiretorio.mkdirs();
                        Toast.makeText(getApplicationContext(), "DIRETORIO CRIADO COM SUCESSO", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "DIRETORIO JA EXISTE", Toast.LENGTH_SHORT).show();
                        meuDiretorio.getAbsolutePath().toString();
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List <ListaUsuario> SPLASH_listusers = new ArrayList <>();
                            UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());

                            /**verifica se existe usuario cadastrado no BD**/
                            SPLASH_listusers = usuarioDAO.listar();
                            if (SPLASH_listusers.size() > 0) {
                                Toast.makeText(getApplicationContext(), "USUARIO ENCONTRADO", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(Splash_Activity.this, ListarUsuario.class);
                                String blockSplash = "bloqueadoSplash";
                                i.putExtra("BLOQUEIO_SPLASH", blockSplash);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "NADA ENCONTRADO", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Splash_Activity.this, Introducao_Activity.class);
                                startActivity(i);
                                // Fecha esta activity
                                finish();
                            }
                        }
                    }, SPLASH_TIME_OUT);

                    /**chama o metodo para download*/
                    DOWNLOAD();
                } else {
                    /**Cria o gerador do AlertDialog*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(Splash_Activity.this);
                    /**define o titulo*/
                    builder.setTitle("Por Favor!");
                    /**define a imagen icone*/
                    builder.setIcon(R.drawable.por_favor);
                    /**define a mensagem*/
                    builder.setMessage("Precisamos que você nos permita salvar fotos no seu dispositivo.\n A não autorização impossibilita o uso do aplicativo.");
                    /**define um botão como positivo*/
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finishAffinity();
                        }
                    });
                    /**cria o AlertDialog*/
                    alerta = builder.create();
                    /**Exibe*/
                    alerta.show();
                }
                break;
            }
        }
    }

    public void INSERIR_DADOS_BANCO() {
        /**verifica se existe comunicação cadastrado no BD**/
        ComunicacaoDAO comunicacaoDAO = new ComunicacaoDAO(getApplicationContext());
        List <ListaComunicacao> SPLASH_listComunic;
        //se a listagem for igual a 0 é inserido 2 registros na tabela COMUNICACAO por default
        SPLASH_listComunic = comunicacaoDAO.listar();
        if (SPLASH_listComunic.size() == 0) {
            listaComunicacao.setCaminhoFirebase("sentimento 1.png");
            listaComunicacao.setTextoFalar("eu te amo");
            listaComunicacao.setTipoComunic("sentimentos");
            comunicacaoDAO.salvar(listaComunicacao);

            listaComunicacao.setCaminhoFirebase("sentimento 2.jpg");
            listaComunicacao.setTextoFalar("estou triste");
            listaComunicacao.setTipoComunic("sentimentos");
            comunicacaoDAO.salvar(listaComunicacao);
            Toast.makeText(getApplicationContext(), "SENTIMENTOS INSERIDOS COM SUCESSO", Toast.LENGTH_SHORT).show();
        }

    }
}


