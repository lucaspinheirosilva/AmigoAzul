package br.com.amigoazul.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import br.com.amigoazul.R;

public class MenuComunicacao extends AppCompatActivity {

    ImageView sentimentos;
    ImageView objetos;
    ImageView montarFrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.menu_comunicacao);

        sentimentos = findViewById(R.id.img_sentimentos);
        objetos = findViewById(R.id.img_objetos);
        montarFrase = findViewById(R.id.img_montarFrase);

        sentimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuComunicacao.this,Sentimentos.class);
                startActivity(intent);
            }
        });
        objetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuComunicacao.this,Objetos.class);
                startActivity(intent);
            }
        });
        montarFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuComunicacao.this,MontarFrase.class);
                startActivity(intent);
            }
        });
    }
}
