package br.com.amigoazul.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.amigoazul.R;

//VER ISSO...PODE AJUDAR
//TODO: https://github.com/nostra13/Android-Universal-Image-Loader

public class Sentimentos extends AppCompatActivity {

    //instanciar outras classes
    Splash_Activity splash_activity = new Splash_Activity();

    List<File> listaArquivos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.sentimentos);
        //TODO: tiras as imagens e colocar BOTOES e adicionar a FALA a eles




    }
    //https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
    //https://www.tutorialspoint.com/android/android_camera.htm]
    //https://pt.stackoverflow.com/questions/119792/carregar-imageview-usando-caminho-da-imagem
    //https://stackoverflow.com/questions/8646984/how-to-list-files-in-an-android-directory
    public void DOWNLOAD_TESTE() {
        File imgFile = new File(splash_activity.meuDirSentimentos.getAbsolutePath());
        if (imgFile.exists()) {
            Log.e("TESTE", "a pasta é " + imgFile.getAbsolutePath());
            File[] files = imgFile.listFiles();
            Log.e("TESTE", "o tamanho é " + files.length);
            listaArquivos.clear();

            for (int i = 0; i < files.length; i++) {
                Log.e("TESTE", "Nome do ARQUIVO " + files[i].getName());
                listaArquivos.add(files[i]);
            }

        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), lis, R.layout.lista_sentimentos_adapter, from, to);
        ListView androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);
    }
}
