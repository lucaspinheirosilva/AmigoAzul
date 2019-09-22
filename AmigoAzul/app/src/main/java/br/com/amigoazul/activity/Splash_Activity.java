package br.com.amigoazul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.helper.UsuarioDAO;
import br.com.amigoazul.model.ListaUsuario;

//https://www.devmedia.com.br/como-criar-telas-de-abertura-no-android/33256
public class Splash_Activity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                List <ListaUsuario> SPLASH_listusers = new ArrayList <>();
                UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());

                SPLASH_listusers = usuarioDAO.listar();

                if (SPLASH_listusers.size() > 0) {
                    Toast.makeText(getApplicationContext(), "USUARIO ENCONTRADO", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(Splash_Activity.this, ListarUsuario.class);
                    String blockSplash = "bloqueadoSplash";
                    i.putExtra("BLOQUEIO_SPLASH", blockSplash);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "NADA ENCONTRADO", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Splash_Activity.this, Introducao_Activity.class);
                    startActivity(i);

                    // Fecha esta activity
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);
    }
}
