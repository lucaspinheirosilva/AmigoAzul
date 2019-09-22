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

                addSlide(new SimpleSlide.Builder()
                .title("É para voce!")
                .description("Quem precisar pode usar sem nenhuma cobraça infortuna")
                .image(R.drawable.intro_1)
                .background(R.color.bg_intro1)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("A qualquer Hora")
                .description("Disponha de uma ferramenta a qualquer Hora do seu Dia")
                .image(R.drawable.intro_2)
                .background(R.color.bg_intro2)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Relatorio de acompanhamento")
                .description("Utilize o relatorio a qualquer" +
                        " hora para acompanhar o desenvolvimento do seu filho(a)")
                .image(R.drawable.intro_3)
                .background(R.color.bg_intro3)

                .build());

        addSlide(new SimpleSlide.Builder()
                .title("JUNTE-SE A NÓS")
                .description("Junte-se a Nós, é super rapido!!")
                .image(R.drawable.intro_4)
                .background(R.color.bg_intro4)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.bg_cadastro)
                .fragment(R.layout.chamada_cadastro)
                .canGoForward(false)
                .canGoBackward(true)
                .build());
    }

    public  void BtnChamarCadastro(View view){
        startActivity(new Intent(Introducao_Activity.this, Cadastro_Activity.class));
        finish();
    }


}