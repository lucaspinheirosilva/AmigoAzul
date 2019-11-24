package br.com.amigoazul.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.amigoazul.R;

public class MenuAtividades extends AppCompatActivity {


    /**https://github.com/yavski/fab-speed-dial*/

    ImageView arrastaSolta;
    ImageView memoria;
    Button testarJogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.menu_atividades);

        testarJogo = findViewById(R.id.btn_testarJogoConstruct);

        testarJogo.setVisibility(View.INVISIBLE);
        testarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuAtividades.this, jogo_arrasta_solta.class);
                startActivity(intent);
            }
        });

        arrastaSolta = findViewById(R.id.img_arrastaSolta);
        memoria = findViewById(R.id.img_memoria);

        arrastaSolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuAtividades.this, jogo_arrasta_solta.class);
                startActivity(intent);
            }
        });
        memoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Não foi IMPLEMENTADO ESTA TELA!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
