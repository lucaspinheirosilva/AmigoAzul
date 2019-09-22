package br.com.amigoazul.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import br.com.amigoazul.R;

public class MontarFrase extends AppCompatActivity {

    ImageView euQuero;
    ImageView brincar;
    ImageView cachorro;
    ImageView bola;
    EditText fraseMontada;
    ImageButton PlayAudio;

    String juntarPlavras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.montar_frase);

        euQuero = findViewById(R.id.img_MF_euQuero);
        brincar = findViewById(R.id.img_MF_brincar);
        cachorro = findViewById(R.id.img_MF_cachorro);
        bola = findViewById(R.id.img_MF_bola);
        fraseMontada = findViewById(R.id.edttxt_MF_fraseMontada);
        PlayAudio = findViewById(R.id.imgbtn_MF_playAudio);

        //TODO: pensar em alguma forma de implementar essa tela




    }
}
