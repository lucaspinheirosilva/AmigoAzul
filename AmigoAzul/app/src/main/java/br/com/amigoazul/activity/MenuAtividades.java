package br.com.amigoazul.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.amigoazul.R;

public class MenuAtividades extends AppCompatActivity {


    /**https://github.com/yavski/fab-speed-dial*/

    ImageView arrastaSolta;
    ImageView memoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.menu_atividades);

        arrastaSolta = findViewById(R.id.img_arrastaSolta);
        memoria = findViewById(R.id.img_memoria);

        arrastaSolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuAtividades.this, ArrastaSolta_Activity.class);
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
