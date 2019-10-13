package br.com.amigoazul.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
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

    List <File> listaArquivos = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.sentimentos);
        CARREGAR_FOTOS_SENTIMENTOS();

        FAB_camera_Sentimentos = findViewById(R.id.fab_cameraSentimentos);


        FAB_camera_Sentimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TIRAR_FOTO();

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
    public void TIRAR_FOTO() {
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(imageIntent, 1);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date diaData = new Date();
        String dataFormatada = formataData.format(diaData);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            Bitmap imagem = (Bitmap) extra.get("data");
            salvar_foto.SALVAR_IMAGEM_DIRECTORIO(imagem, "AZ-"+dataFormatada+".JPG",splash_activity.meuDirSentimentos.getAbsolutePath());


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
