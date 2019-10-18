package br.com.amigoazul.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.adapter.ListarObjetosAdapter;
import br.com.amigoazul.helper.SALVAR_FOTO;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class Objetos extends AppCompatActivity {
    FloatingActionButton FAB_cameraObjetos;

    //pega a data e hora do dispositivo
    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
    Date diaData = new Date();
    String dataFormatada = formataData.format(diaData);

    //instanciar outras classes
    Splash_Activity splash_activity = new Splash_Activity();
    SALVAR_FOTO salvar_foto = new SALVAR_FOTO();

    List <File> listaArquivos = new ArrayList <>();

    //AlertDialog
    AlertDialog alerta;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.objetos);

        FabSpeedDial FAB_camera_Objetos = (FabSpeedDial) findViewById(R.id.fab_speed_dial_objetos);
        CARREGAR_FOTOS_OBJETOS();

        //FloatActionButton para menu de SENTIMENTOS
        FAB_camera_Objetos.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.FAB_acao_addImagem:
                        TIRAR_FOTO_ou_GALERIA();
                        break;
                    case R.id.FAB_acao_excluirImagem:
                        Toast.makeText(getApplicationContext(), "EXCLUIR IMAGEM", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    public void CARREGAR_FOTOS_OBJETOS() {
        File diretorio = new File(splash_activity.meuDirObjetos.getAbsolutePath());
        if (diretorio.exists()) {
            Log.e("OBJETOS", "a pasta é " + diretorio.getAbsolutePath());
            File[] files = diretorio.listFiles();
            Log.e("OBJETOS", "total de arquivos no diretorio: " + files.length);
            listaArquivos.clear();
            for (int i = 0; i < files.length; i++) {
                Log.e("OBJETOS *****", (i + 1) + "**** ====> diretorio completo: " + diretorio + "/" + files[i].getName());
                listaArquivos.add(files[i]);
            }
            //https://acomputerengineer.wordpress.com/2018/04/15/display-image-grid-in-recyclerview-in-android/
            RecyclerView recyclerView = findViewById(R.id.rcrtvw_listarObjetos);

            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(sglm);

            ListarObjetosAdapter listarObjetosAdapter = new ListarObjetosAdapter(Objetos.this, listaArquivos);
            recyclerView.setAdapter(listarObjetosAdapter);

        }

    }


    public void TIRAR_FOTO_ou_GALERIA() {
        //alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(Objetos.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogCameraGaleriaview = inflater.inflate(R.layout.dialog_camera_galeria, null);
        builder.setView(dialogCameraGaleriaview);

        ImageButton camera = dialogCameraGaleriaview.findViewById(R.id.imgbtn_alerDialcamera);
        ImageButton galeria = dialogCameraGaleriaview.findViewById(R.id.imgbtn_alerDialgaleria);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(imageIntent, 2);
            }
        });
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPegaFoto = new Intent(Intent.ACTION_PICK);
                intentPegaFoto.setType("image/*");
                startActivityForResult(intentPegaFoto, 22);
                alerta.cancel();
            }
        });
        alerta = builder.create();
        alerta.show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {//tirar foto
            Bundle extra = data.getExtras();
            final Bitmap imagem = (Bitmap) extra.get("data");

            //alertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(Objetos.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogText_Fotoview = inflater.inflate(R.layout.texto_foto, null);
            builder.setView(dialogText_Fotoview);
            builder.setCancelable(false);

            final ImageView imagemTirada = dialogText_Fotoview.findViewById(R.id.imgvw_fotoTirada);
            imagemTirada.setImageBitmap(imagem);
            final Button salvar = dialogText_Fotoview.findViewById(R.id.btnSalvar_TextoFalar);
            Button cancelar = dialogText_Fotoview.findViewById(R.id.btnCancelar_TextoFalar);

            salvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    salvar_foto.SALVAR_IMAGEM_DIRECTORIO(imagem, "AZ-" +
                            dataFormatada + ".JPG", splash_activity.meuDirObjetos.getAbsolutePath());
                    alerta.cancel();
                    CARREGAR_FOTOS_OBJETOS();
                }
            });
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alerta.cancel();

                }
            });
            alerta = builder.create();
            alerta.show();
        }

        if (resultCode == RESULT_OK && requestCode == 22) { //foto da galeria
            //Pegamos a URI da imagem...
            Uri uriSelecionada = data.getData();

            //pegar a imagem e converte  para um path compativel para inserir no ImageView
            String[] colunas = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uriSelecionada, colunas, null, null, null);
            cursor.moveToFirst();
            int indexColuna = cursor.getColumnIndex(colunas[0]);
            String pathImg = cursor.getString(indexColuna);
            cursor.close();

            final Bitmap imagem = BitmapFactory.decodeFile(pathImg);
            // criamos um File com o diretório selecionado!
            final File selecionada = new File(salvar_foto.getRealPathFromURI(uriSelecionada, getApplicationContext()));
            // Caso não exista o doretório, vamos criar!
            final File rootPath = new File(splash_activity.meuDirObjetos.getAbsolutePath());
            if (!rootPath.exists()) {
                rootPath.mkdirs();
            }

            // Criamos um file, com o no DIRETORIO, com o mesmo nome da anterior
            final File novaImagem = new File(rootPath, "AZ-" + dataFormatada + ".JPG");

            //alertDialog
            builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogText_Fotoview = inflater.inflate(R.layout.texto_foto, null);
            builder.setView(dialogText_Fotoview);
            builder.setCancelable(false);

            final ImageView imagemTirada = dialogText_Fotoview.findViewById(R.id.imgvw_fotoTirada);
            imagemTirada.setImageBitmap(imagem);
            final Button salvar = dialogText_Fotoview.findViewById(R.id.btnSalvar_TextoFalar);
            Button cancelar = dialogText_Fotoview.findViewById(R.id.btnCancelar_TextoFalar);

            salvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Movemos o arquivo!
                    try {
                        salvar_foto.COPIAR_ARQUIVO(selecionada, novaImagem, getApplicationContext());
                        Toast.makeText(Objetos.this, "Imagem copiada com sucesso!", Toast.LENGTH_SHORT).show();
                        alerta.cancel();
                        CARREGAR_FOTOS_OBJETOS();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alerta.cancel();

                }
            });
            alerta = builder.create();
            alerta.show();


        }

            /*String s = files.getAbsolutePath();
            String result = s.substring(s.lastIndexOf(System.getProperty("file.separator"))+1,s.length());
            System.out.println(result);
            Log.e("TESTESSSS",result);*///pegar apenas o nome do arquivo sem o nome do diretorio

           /* String caminhoCompleto = files.getAbsolutePath();
            int indiceBarra = caminhoCompleto.lastIndexOf("/") + 1;
            if (indiceBarra == 0) {
                indiceBarra = caminhoCompleto.lastIndexOf("/") + 1;
            }
            // Basta pegar o substring com o caminho da pasta.
            String caminhoPasta = caminhoCompleto.substring(0, indiceBarra);
            Log.e("TESTE",caminhoPasta);*/ //pegar apenas no nome do caminho sem o nome do arquivo
    }

}

