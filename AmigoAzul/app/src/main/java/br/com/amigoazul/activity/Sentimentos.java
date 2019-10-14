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
import android.view.View;
import android.widget.ImageButton;

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
import br.com.amigoazul.adapter.ListarSentimentosAdapter;
import br.com.amigoazul.helper.SALVAR_FOTO;

//VER ISSO...PODE AJUDAR
//TODO: https://github.com/nostra13/Android-Universal-Image-Loader

public class Sentimentos extends AppCompatActivity {

    FloatingActionButton FAB_camera_Sentimentos;

    //instanciar outras classes
    Splash_Activity splash_activity = new Splash_Activity();
    SALVAR_FOTO salvar_foto = new SALVAR_FOTO();

    AlertDialog alerta;

    List <File> listaArquivos = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.sentimentos);

        //setar variaveis com IDs
        FAB_camera_Sentimentos = findViewById(R.id.fab_cameraSentimentos);

        CARREGAR_FOTOS_SENTIMENTOS();




        FAB_camera_Sentimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TIRAR_FOTO_ou_GALERIA();

            }
        });


    }

    //https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
    //https://www.tutorialspoint.com/android/android_camera.html
    //https://pt.stackoverflow.com/questions/119792/carregar-imageview-usando-caminho-da-imagem
    //https://stackoverflow.com/questions/8646984/how-to-list-files-in-an-android-directory
    public void CARREGAR_FOTOS_SENTIMENTOS() {
        File diretorio = new File(splash_activity.meuDirSentimentos.getAbsolutePath());
        if (diretorio.exists()) {
            Log.e("SENTIMENTOS", "a pasta é " + diretorio.getAbsolutePath());
            File[] files = diretorio.listFiles();
            Log.e("SENTIMENTOS", "total de arquivos no diretorio: " + files.length);
            listaArquivos.clear();

            for (int i = 0; i < files.length; i++) {
                Log.e("SENTIMENTOS", "diretorio completo: " + diretorio + "/" + files[i].getName());
                listaArquivos.add(files[i]);
            }
            //https://acomputerengineer.wordpress.com/2018/04/15/display-image-grid-in-recyclerview-in-android/
            RecyclerView recyclerView = findViewById(R.id.rcrtvw_listarComunic);

            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(sglm);

            ListarSentimentosAdapter listarSentimentosAdapter = new ListarSentimentosAdapter(Sentimentos.this, listaArquivos);
            recyclerView.setAdapter(listarSentimentosAdapter);

        }

    }

    /**
     * TIRAR FOTO CAMERA CELULAR
     */
    //https://www.youtube.com/watch?v=1oyvdqc_QZg
    public void TIRAR_FOTO_ou_GALERIA() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Sentimentos.this);

       /*View mView = getLayoutInflater().inflate(R.layout.dialog_camera_galeria, null);

        ImageButton img_btn_camera = mView.findViewById(R.id.imgbtn_camera);
        ImageButton img_btn_galeria = mView.findViewById(R.id.imgbtn_galeria);

        img_btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(imageIntent, 1);
            }
        });
        img_btn_galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/ //arrumar depois

        builder.setTitle("ESCOLHA");
        builder.setMessage("selecione entre a CAMERA ou a GALERIA para escolher uma foto para adicionar ao AMIGO AZUL");
        builder.setPositiveButton("CAMERA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(imageIntent, 1);
            }
        });
        builder.setNegativeButton("GALERIA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentPegaFoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intentPegaFoto,11);
            }
        });

        alerta=builder.create();
        alerta.show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date diaData = new Date();
        String dataFormatada = formataData.format(diaData);

        if (requestCode == 1 && resultCode == RESULT_OK) {//tirar foto
            Bundle extra = data.getExtras();
            Bitmap imagem = (Bitmap) extra.get("data");
            salvar_foto.SALVAR_IMAGEM_DIRECTORIO(imagem, "AZ-" + dataFormatada + ".JPG", splash_activity.meuDirSentimentos.getAbsolutePath());

        }
        if (requestCode==11){//abrir galeria
            Uri extra = data.getData();
            Bitmap imagem = BitmapFactory.decodeFile(extra.getPath());
            File files = new File(extra.getPath());

            String s = files.getAbsolutePath();
            String result = s.substring(s.lastIndexOf(System.getProperty("file.separator"))+1,s.length());
            System.out.println(result);
            Log.e("TESTESSSS",result);


           /* String caminhoCompleto = files.getAbsolutePath();
            int indiceBarra = caminhoCompleto.lastIndexOf("/") + 1;
            if (indiceBarra == 0) {
                indiceBarra = caminhoCompleto.lastIndexOf("/") + 1;
            }
            // Basta pegar o substring com o caminho da pasta.
            String caminhoPasta = caminhoCompleto.substring(0, indiceBarra);
            Log.e("TESTE",caminhoPasta);*/ //pegar apenas no nome do caminho sem o nome do arquivo

            //salvar_foto.SALVAR_IMAGEM_DIRECTORIO(imagem, "AZ-" + dataFormatada + ".JPG", splash_activity.meuDirSentimentos.getAbsolutePath());

                salvar_foto.COPIAR_GALERIA(files.getAbsolutePath(),result,splash_activity.meuDirSentimentos.toString());

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    public void onResume() {
        CARREGAR_FOTOS_SENTIMENTOS();
        super.onResume();
    }

    @Override
    public void onRestart() {
        CARREGAR_FOTOS_SENTIMENTOS();
        super.onRestart();
    }
}
