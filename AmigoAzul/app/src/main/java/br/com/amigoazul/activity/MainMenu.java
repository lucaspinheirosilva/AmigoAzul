package br.com.amigoazul.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.amigoazul.R;


public class MainMenu extends AppCompatActivity {

    Button comunicar;
    Button atividades;
    FloatingActionButton FAB_alterarUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.menu_principal);

        comunicar = findViewById(R.id.btn_comunicar);
        atividades = findViewById(R.id.btn_atividade);
        FAB_alterarUser = findViewById(R.id.fab_alterarUsuario);


        //BOTAO MENU COMUNICAR
        comunicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainMenu.this, MenuComunicacao.class);
                startActivity(intent);
            }
        });

        //BOTAO MENU ATIVIDADES
        atividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainMenu.this,MenuAtividades.class);
                startActivity(intent);

            }
        });

        //BOTAO FAB ALTERAR USUARIO
        FAB_alterarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,ListarUsuario.class);
                startActivity(intent);
            }
        });

    }
}
