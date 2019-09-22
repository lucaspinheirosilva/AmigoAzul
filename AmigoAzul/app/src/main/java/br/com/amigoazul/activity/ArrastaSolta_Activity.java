package br.com.amigoazul.activity;
import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import br.com.amigoazul.R;

/**
 * Criado por Lucas Pinheiro on 02/09/2019.
 */

/***********************************************************************************************/
/***/ //https://medium.com/@nglauber/drag-and-drop-no-android-f5a4c3f3d2dc

/***********************************************************************************************/

 public class ArrastaSolta_Activity  extends Activity
        implements View.OnTouchListener, View.OnDragListener {

    Drawable enterShape;
    Drawable normalShape;

    ImageView bola;
    ImageView cachorro;
    ImageView carro;
    ImageView arvore;

    ImageView acertoBola;
    ImageView acertoCarro;
    ImageView acertoArvore;
    ImageView acertoCachorro;

    LinearLayout LLBola;
    LinearLayout LLArvore;
    LinearLayout LLCarro;
    LinearLayout LLCachorro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrasta_solta);

        enterShape = getResources().getDrawable(R.drawable.bg_over_arrasta_solta);
        normalShape = getResources().getDrawable(R.drawable.bg_arrasta_solta);

        bola = findViewById(R.id.bola);
        cachorro = findViewById(R.id.cachorro);
        carro = findViewById(R.id.carro);
        arvore = findViewById(R.id.arvore);

        LLBola = findViewById(R.id.bottomright);
        LLCachorro = findViewById(R.id.topleft);
        LLCarro = findViewById(R.id.topright);
        LLArvore = findViewById(R.id.bottomleft);

        acertoArvore = findViewById(R.id.acertoArvore);
        acertoBola = findViewById(R.id.acertoBola);
        acertoCachorro = findViewById(R.id.acertoCachorro);
        acertoCarro = findViewById(R.id.acertoCarro);

        findViewById(R.id.bola).setOnTouchListener(this);
        findViewById(R.id.arvore).setOnTouchListener(this);
        findViewById(R.id.cachorro).setOnTouchListener(this);
        findViewById(R.id.carro).setOnTouchListener(this);

        findViewById(R.id.topleft).setOnDragListener(this);
        findViewById(R.id.topright).setOnDragListener(this);
        findViewById(R.id.bottomleft).setOnDragListener(this);
        findViewById(R.id.bottomright).setOnDragListener(this);

        acertoCachorro.setVisibility(View.GONE);
        acertoCarro.setVisibility(View.GONE);
        acertoArvore.setVisibility(View.GONE);
        acertoBola.setVisibility(View.GONE);
    }

    public boolean onTouch(View view, MotionEvent me) {
        int action = me.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder =
                    new View.DragShadowBuilder(view);

            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_ENTERED:
                // Ao entrar na área que pode fazer o drop
                v.setBackgroundDrawable(enterShape);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                // Ao sair da área que pode fazer o drop
                v.setBackgroundDrawable(normalShape);
                break;
            case DragEvent.ACTION_DROP:
                // Ao fazer o drop
                View view = (View) event.getLocalState();
                if ((v == LLArvore) && (view.getId() == arvore.getId())) {
                    acertoArvore.setVisibility(View.VISIBLE);
                    acertoArvore.setImageResource(R.drawable.certo);

                }
                if ((v == LLBola) && (view.getId() == bola.getId())) {
                    acertoBola.setVisibility(View.VISIBLE);
                    acertoBola.setImageResource(R.drawable.certo);

                }
                if ((v == LLCachorro) && (view.getId() == cachorro.getId())) {
                    acertoCachorro.setVisibility(View.VISIBLE);
                    acertoCachorro.setImageResource(R.drawable.certo);

                }
                if ((v == LLCarro) && (view.getId() == carro.getId())) {
                    acertoCarro.setVisibility(View.VISIBLE);
                    acertoCarro.setImageResource(R.drawable.certo);

                }
                //**************************************
                if ((v == LLArvore) && (view.getId() != arvore.getId())) {
                    acertoArvore.setVisibility(View.VISIBLE);
                    acertoArvore.setImageResource(R.drawable.errado);
                }
                if ((v == LLBola) && (view.getId() != bola.getId())) {
                    acertoBola.setVisibility(View.VISIBLE);
                    acertoBola.setImageResource(R.drawable.errado);
                }
                if ((v == LLCachorro) && (view.getId() != cachorro.getId())) {
                    acertoCachorro.setVisibility(View.VISIBLE);
                    acertoCachorro.setImageResource(R.drawable.errado);
                }
                if ((v == LLCarro) && (view.getId() != carro.getId())) {
                    acertoCarro.setVisibility(View.VISIBLE);
                    acertoCarro.setImageResource(R.drawable.errado);
                }
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);
                LinearLayout container = (LinearLayout) v;
                container.addView(view);
                view.setVisibility(View.VISIBLE);

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                // Ao terminar de arrastar
                v.setBackgroundDrawable(normalShape);
                View view2 = (View) event.getLocalState();
                view2.setVisibility(View.VISIBLE);

            default:
                break;
        }

        return true;
    }

}

