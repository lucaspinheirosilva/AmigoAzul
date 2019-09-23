package br.com.amigoazul.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.helper.UsuarioDAO;
import br.com.amigoazul.model.ListaUsuario;
/**TUTORIAL TELA SE SPLASH**/
//https://www.devmedia.com.br/como-criar-telas-de-abertura-no-android/33256

/**TUTORIAL DOWNLOAD ARQUIVO FIREBASE P/ CELULAR**/
// https://www.youtube.com/watch?v=SmXGlv7QEO0
public class Splash_Activity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    /***VARIAVEIS FIREBASE*/
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;
    File meuDirectory = new File(Environment.getExternalStorageDirectory(), "AmigoAzul_Fotos");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        /**CRIAR PASTA PARA SALVAR AS FOTOS**/
        if(!meuDirectory.exists()) {
            meuDirectory.mkdirs();
            Toast.makeText(getApplicationContext(), "DIRETORIO CRIADO COM SUCESSO", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "DIRETORIO JA EXISTE", Toast.LENGTH_SHORT).show();
            meuDirectory.getAbsolutePath().toString();
        }

        DOWNLOAD();

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
    }

    /**
     * METODO TESTE PARA BAIXAR IMAGENS FIREBASE
     */
    public void DOWNLOAD() {
        storageReference = firebaseStorage.getInstance().getReference();
        ref = storageReference.child("AreaTest.jpg");

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener <Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                DOWNLOADFILES(Splash_Activity.this,
                        "AreaTest",
                        ".jpg",
                        meuDirectory.getAbsolutePath(),
                        url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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
}
