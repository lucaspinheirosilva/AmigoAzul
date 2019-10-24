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





    }
}
