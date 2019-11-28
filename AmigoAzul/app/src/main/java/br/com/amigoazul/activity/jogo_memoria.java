package br.com.amigoazul.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.amigoazul.R;


public class jogo_memoria extends AppCompatActivity {

    WebView jogoMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.memoria);

        jogoMemoria = findViewById(R.id.wbvw_memoria);

        jogoMemoria.getSettings().setJavaScriptEnabled(true);
        jogoMemoria.getSettings().setAllowFileAccessFromFileURLs(true);

        jogoMemoria.loadUrl("file:///android_asset/memoria/index.html");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
}
