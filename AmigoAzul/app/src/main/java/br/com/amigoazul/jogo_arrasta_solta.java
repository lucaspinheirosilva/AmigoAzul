package br.com.amigoazul;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;


public class jogo_arrasta_solta extends AppCompatActivity {

    WebView jogoArrastaSolta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.jogo_arrasta_solta);

        jogoArrastaSolta = findViewById(R.id.wbvw_arrastaSolta);

        jogoArrastaSolta.getSettings().setJavaScriptEnabled(true);
        jogoArrastaSolta.getSettings().setAllowFileAccessFromFileURLs(true);

        jogoArrastaSolta.loadUrl("file:///android_asset/index.html");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
}
