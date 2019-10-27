package br.com.amigoazul.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.helper.InserirDadosBD;
import br.com.amigoazul.helper.UsuarioDAO;
import br.com.amigoazul.model.ListaComunicacao;
import br.com.amigoazul.model.ListaUsuario;


public class Splash_Activity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    final static int Const_WRITE_EXTERNAL_STORAGE = 001;


    /***VARIAVEIS FIREBASE*/
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    /**
     * VARIAVEL PROGRESSBAR
     */
    ProgressDialog progressDialog;


    /**
     * CRIAR DIRETORIO NO CELULAR DO USUARIO
     */
    public final File meuDiretorio = new File(Environment.getExternalStorageDirectory(), "AmigoAzul_Fotos");
    public final File meuDirSentimentos = new File(Environment.getExternalStorageDirectory(), "AmigoAzul_Fotos/Sentimentos");
    public final File meuDirObjetos = new File(Environment.getExternalStorageDirectory(), "AmigoAzul_Fotos/Objetos");
    public final File meuDirMontarFreses = new File(Environment.getExternalStorageDirectory(), "AmigoAzul_Fotos/Montar_Frase");

    /***ALERTDIALOG*/
    private AlertDialog alerta;

    /**
     * ESTANCIAR OUTRAS CLASSES
     */
    ListaComunicacao listaComunicacao = new ListaComunicacao();
    InserirDadosBD inserirDadosAutomaticos = new InserirDadosBD();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("PERMISSAO", "permissao ja foi concedida para criar pastas para as fotos na memoria do Dispositivo");

                /**CRIAR PASTA PARA SALVAR AS FOTOS**/
                if (!meuDiretorio.exists()) {
                    meuDiretorio.mkdirs();

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
                inserirDadosAutomaticos.INSERIR_DADOS_BANCO(getApplicationContext());

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
                            Toast.makeText(getApplicationContext(), "NENHUM USUARIO ENCONTRADO", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Splash_Activity.this, Introducao_Activity.class);
                            startActivity(i);
                            // Fecha esta activity
                            finish();
                        }
                    }
                }, SPLASH_TIME_OUT);

                /**chama o metodo para download*/
                //DOWNLOAD();

            } else {
                requestPermission();
            }
        }
    }

    /**
     * METODO TESTE PARA BAIXAR IMAGENS FIREBASE
     */
    /*public void DOWNLOAD() {
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef;
        StorageReference imageReference;
        imageReference = FirebaseStorage.getInstance().getReference().child("Sentimentos");


        //todo:continuar apartir daqui
        ComunicacaoDAO comunicacaoDAO = new ComunicacaoDAO(getApplicationContext());
        List <ListaComunicacao> listaComunicacaos = new ArrayList <>();
        listaComunicacaos = comunicacaoDAO.listar();


        //Percorre o Vetor e pega o nome do campo "CAMINHO FIRABASE" e unificar com o URL de download
        //que vem do FIREBASE, e baixar as imagen no laço FOR
        int i;
        for (i = 0; i < listaComunicacaos.size(); i++) {
            Log.e("FIREBASE", "Nome da Imagem no Banco de dados do Dispositivo " + listaComunicacaos.get(i).getCaminhoFirebase());
            fileRef = imageReference.child(listaComunicacaos.get(i).getCaminhoFirebase());
            Log.e("FIREBASE", "Caminho da Imagem no Firebase " + fileRef.toString());
            if (fileRef != null) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Baixando Imagens.");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("Baixando " + (i = i + 1) + " de " + listaComunicacaos.size());
                progressDialog.show();
                try {
                    final File file = new File(meuDirSentimentos,listaComunicacaos.get(i).getCaminhoFirebase());
                    final StorageReference finalFileRef = fileRef;
                    fileRef.getFile(file).addOnSuccessListener(new OnSuccessListener <FileDownloadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                           //TODO: ANALIZAR ESSE TRECHO E IMPLEMENTAR NO DOWNLOAD DAS IMAGENS
                           DownloadManager downloadmanager =(DownloadManager) getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
                           Uri uri = Uri.parse(finalFileRef.toString());
                           DownloadManager.Request request = new DownloadManager.Request(uri);

                           request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                           request.setDestinationInExternalFilesDir(getApplicationContext(),meuDirObjetos.getAbsolutePath(),null);
                           downloadmanager.enqueue(request);
                           //https://www.youtube.com/watch?v=SmXGlv7QEO0
                         /* public void downloadFile(Context context,String arquivoNome,String extensaoArquivo,String diretorio,String url){
                               DownloadManager downloadmanager =(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                               Uri uri = Uri.parse(url);
                               DownloadManager.Request request = new DownloadManager.Request(uri);

                               request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                               request.setDestinationInExternalFilesDir(context,diretorio,null);
                               downloadmanager.enqueue(request);

                           }

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getApplicationContext(),"EERRRROO",Toast.LENGTH_LONG).show();

                       }
                   });

                }catch (Exception erro){

                }


            }

        }

    }*///metodo DOWNLOAD testar futuramente
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Const_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    /**CRIAR PASTA PARA SALVAR AS FOTOS**/
                    if (!meuDiretorio.exists()) {
                        meuDiretorio.mkdirs();

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

                                //enviar uma String com uma mensagem para outra tela informando que esta bloqueado a
                                //edição ou exclusao
                                Intent i = new Intent(Splash_Activity.this, ListarUsuario.class);
                                String blockSplash = "bloqueadoSplash";
                                i.putExtra("BLOQUEIO_SPLASH", blockSplash);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "NENHUM USUARIO ENCONTRADO", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Splash_Activity.this, Introducao_Activity.class);
                                startActivity(i);
                                // Fecha esta activity
                                finish();
                            }
                        }
                    }, SPLASH_TIME_OUT);

                    /**chama o metodo para download*/
                    //  DOWNLOAD();
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


}

