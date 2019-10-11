package br.com.amigoazul.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.adapter.ListarComunicacaoAdapter;

//VER ISSO...PODE AJUDAR
//TODO: https://github.com/nostra13/Android-Universal-Image-Loader

public class Sentimentos extends AppCompatActivity {

    //instanciar outras classes
    Splash_Activity splash_activity = new Splash_Activity();

    List <File> listaArquivos = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.sentimentos);
        DOWNLOAD_TESTE();


    }

    //https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
    //https://www.tutorialspoint.com/android/android_camera.html
    //https://pt.stackoverflow.com/questions/119792/carregar-imageview-usando-caminho-da-imagem
    //https://stackoverflow.com/questions/8646984/how-to-list-files-in-an-android-directory
    public void DOWNLOAD_TESTE() {
        File diretorio = new File(splash_activity.meuDirSentimentos.getAbsolutePath());
        if (diretorio.exists()) {
            Log.e("TESTE", "a pasta é " + diretorio.getAbsolutePath());
            File[] files = diretorio.listFiles();
            Log.e("TESTE", "total de arquivos no diretorio: " + files.length);
            File imagemFile;
            listaArquivos.clear();

            for (int i = 0; i < files.length; i++) {
                Log.e("TESTE", "diretorio completo: " + diretorio + "/" + files[i].getName());
                imagemFile = new File(diretorio.getAbsolutePath() + "/", files[i].getName());
                listaArquivos.add(files[i]);
            }
            //https://acomputerengineer.wordpress.com/2018/04/15/display-image-grid-in-recyclerview-in-android/
            RecyclerView recyclerView = findViewById(R.id.rcrtvw_listarComunic);

            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(sglm);

            ListarComunicacaoAdapter listarComunicacaoAdapter = new ListarComunicacaoAdapter(Sentimentos.this,listaArquivos);
            recyclerView.setAdapter(listarComunicacaoAdapter);



            /*Picasso.with(context).load(new File(YOUR_FILE_PATH)).into(imageView);*/


        }

    }
}
