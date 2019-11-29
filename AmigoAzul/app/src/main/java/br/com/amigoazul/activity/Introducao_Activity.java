package br.com.amigoazul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import br.com.amigoazul.R;


public class Introducao_Activity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.introducao);
        /* Show/hide button */
        setButtonBackVisible(false);
        setButtonNextVisible(false);
        setFullscreen(true);

        addSlide(new FragmentSlide.Builder()
                .background(R.color.bg_intro)
                .fragment(R.layout.chamada_1)
                .canGoForward(true)
                .canGoBackward(false)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.bg_intro)
                .fragment(R.layout.chamada_2)
                .canGoForward(true)
                .canGoBackward(true)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.bg_intro)
                .fragment(R.layout.chamada_3)
                .canGoForward(true)
                .canGoBackward(true)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.bg_intro)
                .fragment(R.layout.chamada_4)
                .canGoForward(true)
                .canGoBackward(true)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.bg_cadastro)
                .fragment(R.layout.chamada_cadastro)
                .canGoForward(false)
                .canGoBackward(true)
                .build());
    }

    public void BtnChamarCadastro(View view) {
        startActivity(new Intent(Introducao_Activity.this, Cadastro_Activity.class));

        finish();
    }


}