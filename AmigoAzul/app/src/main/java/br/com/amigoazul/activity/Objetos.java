package br.com.amigoazul.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.amigoazul.R;

public class Objetos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.objetos);
        //TODO: tiras as imagens e colocar BOTOES e adicionar a FALA a eles
    }
}
