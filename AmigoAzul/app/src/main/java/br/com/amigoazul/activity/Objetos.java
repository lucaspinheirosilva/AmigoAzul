package br.com.amigoazul.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.adapter.ListarObjetosAdapter;
import br.com.amigoazul.adapter.ListarSentimentosAdapter;
import br.com.amigoazul.helper.SALVAR_FOTO;

public class Objetos extends AppCompatActivity {
    FloatingActionButton FAB_cameraObjetos;

    //instanciar outras classes
    Splash_Activity splash_activity = new Splash_Activity();
    SALVAR_FOTO salvar_foto = new SALVAR_FOTO();

    List <File> listaArquivos = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.objetos);

        FAB_cameraObjetos = findViewById(R.id.fab_cameraObjetos);
        CARREGAR_FOTOS_OBJETOS();

        FAB_cameraObjetos.setOnClickListener(new View.OnClickListener() {
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
    public void CARREGAR_FOTOS_OBJETOS() {
        File diretorio = new File(splash_activity.meuDirObjetos.getAbsolutePath());
        if (diretorio.exists()) {
            Log.e("OBJETOS", "a pasta é " + diretorio.getAbsolutePath());
            File[] files = diretorio.listFiles();
            Log.e("OBJETOS", "total de arquivos no diretorio: " + files.length);
            listaArquivos.clear();
            for (int i = 0; i < files.length; i++) {
                Log.e("OBJETOS", "diretorio completo: " + diretorio + "/" + files[i].getName());
                listaArquivos.add(files[i]);
            }
            //https://acomputerengineer.wordpress.com/2018/04/15/display-image-grid-in-recyclerview-in-android/
            RecyclerView recyclerView = findViewById(R.id.rcrtvw_listarObjetos);

            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(sglm);

            ListarObjetosAdapter listarObjetosAdapter = new ListarObjetosAdapter(Objetos.this,listaArquivos);
            recyclerView.setAdapter(listarObjetosAdapter);

        }

    }
    /**
     * TIRAR FOTO CAMERA CELULAR
     */
    //https://www.youtube.com/watch?v=1oyvdqc_QZg
    public void TIRAR_FOTO_ou_GALERIA() {
        AlertDialog alerta;
        AlertDialog.Builder builder = new AlertDialog.Builder(Objetos.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_camera_galeria, null);

        ImageButton img_btn_camera = mView.findViewById(R.id.imgbtn_camera);
        ImageButton img_btn_galeria = mView.findViewById(R.id.imgbtn_galeria);

        img_btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(imageIntent, 2);
            }
        });
        img_btn_galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        alerta=builder.create();
        alerta.show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date diaData = new Date();
        String dataFormatada = formataData.format(diaData);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            Bitmap imagem = (Bitmap) extra.get("data");
            salvar_foto.SALVAR_IMAGEM_DIRECTORIO(imagem, "AZ-"+dataFormatada+".JPG",splash_activity.meuDirObjetos.getAbsolutePath());


        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void onResume() {
        CARREGAR_FOTOS_OBJETOS();
        super.onResume();
    }

    @Override
    public void onRestart() {
        CARREGAR_FOTOS_OBJETOS();
        super.onRestart();
    }
}

