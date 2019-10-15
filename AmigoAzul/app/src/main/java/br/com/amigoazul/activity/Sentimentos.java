package br.com.amigoazul.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.adapter.ListarSentimentosAdapter;
import br.com.amigoazul.helper.SALVAR_FOTO;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class Sentimentos extends AppCompatActivity {


    //instanciar outras classes
    Splash_Activity splash_activity = new Splash_Activity();
    SALVAR_FOTO salvar_foto = new SALVAR_FOTO();


    AlertDialog alerta;

    List <File> listaArquivos = new ArrayList <>();

    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
    Date diaData = new Date();
    String dataFormatada = formataData.format(diaData);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.sentimentos);


        //setar variaveis com IDs
        FabSpeedDial FAB_camera_Sentimentos = (FabSpeedDial) findViewById(R.id.fab_speed_dial_sentimentos);

        CARREGAR_FOTOS_SENTIMENTOS();

        //FloatActionButton para menu de SENTIMENTOS
        FAB_camera_Sentimentos.setMenuListener(new SimpleMenuListenerAdapter() {
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


    public void CARREGAR_FOTOS_SENTIMENTOS() {
        File diretorio = new File(splash_activity.meuDirSentimentos.getAbsolutePath());
        if (diretorio.exists()) {
            Log.e("SENTIMENTOS", "a pasta é " + diretorio.getAbsolutePath());
            File[] files = diretorio.listFiles();
            Log.e("SENTIMENTOS", "total de arquivos no diretorio: " + files.length);
            listaArquivos.clear();

            for (int i = 0; i < files.length; i++) {
                Log.e("SENTIMENTOS *****", (i + 1) + "**** ====> diretorio completo: " + diretorio + "/" + files[i].getName());
                listaArquivos.add(files[i]);
            }

            RecyclerView recyclerView = findViewById(R.id.rcrtvw_listarComunic);

            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(sglm);

            ListarSentimentosAdapter listarSentimentosAdapter = new ListarSentimentosAdapter(Sentimentos.this, listaArquivos);
            recyclerView.setAdapter(listarSentimentosAdapter);
        }
    }


    public void TIRAR_FOTO_ou_GALERIA() {
        //alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(Sentimentos.this);
        builder.setTitle("SENTIMENTOS");
        builder.setMessage("selecione a CAMERA ou a GALERIA para escolher uma foto para adicionar ao AMIGO AZUL");
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
                Intent intentPegaFoto = new Intent(Intent.ACTION_PICK);
                intentPegaFoto.setType("image/*");
                startActivityForResult(intentPegaFoto, 11);
            }
        });

        alerta = builder.create();
        alerta.show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {//tirar foto
            Bundle extra = data.getExtras();
            Bitmap imagem = (Bitmap) extra.get("data");
            salvar_foto.SALVAR_IMAGEM_DIRECTORIO(imagem, "AZ-" + dataFormatada + ".JPG", splash_activity.meuDirSentimentos.getAbsolutePath());
            CARREGAR_FOTOS_SENTIMENTOS();
        }


        if (resultCode == RESULT_OK && requestCode == 11) { //foto da galeria
            //Pegamos a URI da imagem...
            Uri uriSelecionada = data.getData();
            // criamos um File com o diretório selecionado!
            final File selecionada = new File(salvar_foto.getRealPathFromURI(uriSelecionada, getApplicationContext()));
            // Caso não exista o doretório, vamos criar!
            final File rootPath = new File(splash_activity.meuDirSentimentos.getAbsolutePath());
            if (!rootPath.exists()) {
                rootPath.mkdirs();
            }

            // Criamos um file, com o no DIRETORIO, com o mesmo nome da anterior
            final File novaImagem = new File(rootPath, "AZ-" + dataFormatada + ".JPG");


            try {//Movemos o arquivo!
                salvar_foto.COPIAR_ARQUIVO(selecionada, novaImagem, getApplicationContext());
                Toast.makeText(getApplicationContext(), "Imagem movida com sucesso!", Toast.LENGTH_SHORT).show();
                CARREGAR_FOTOS_SENTIMENTOS();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
