package br.com.amigoazul.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.amigoazul.R;

//VER ISSO...PODE AJUDAR
//TODO: https://github.com/nostra13/Android-Universal-Image-Loader

public class Sentimentos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.sentimentos);
        //TODO: tiras as imagens e colocar BOTOES e adicionar a FALA a eles



    }
}
