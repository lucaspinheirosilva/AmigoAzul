package br.com.amigoazul.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;
import java.util.Locale;

import br.com.amigoazul.R;
import br.com.amigoazul.model.ListaComunicacao;

/**
 * Criado por Lucas Pinheiro on 12/09/2019.
 */
public class ListarSentimentosAdapter extends RecyclerView.Adapter<ListarSentimentosAdapter.ListarComunicacaoAdapterViewHolder> {

    TextToSpeech textToSpeech;


    private List<ListaComunicacao> imageList;

    private Context context;



    public class ListarComunicacaoAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewSentimentos;

        public ListarComunicacaoAdapterViewHolder(View view) {
            super(view);
            imageViewSentimentos = view.findViewById(R.id.imgbtn_sentimentos_ID);
        }
    }

    public ListarSentimentosAdapter(Context c, List imageList) {
        this.context = c;
        this.imageList = imageList;
    }

    @Override
    public ListarComunicacaoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_sentimentos_adapter, parent, false);

        return new ListarComunicacaoAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListarComunicacaoAdapterViewHolder holder, int position) {
        //final File path = imageList.get(position);

        final ListaComunicacao listaComunicacao = imageList.get(position);
        final File pathFile = new File(listaComunicacao.getCaminhoFirebase());

        Picasso.get()
                .load(pathFile)
                //.resize(100,100)
                //  .centerCrop()
                .into(holder.imageViewSentimentos);


        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //int ttsLang = textToSpeech.setLanguage(Locale.US);
                    int ttsLang = textToSpeech.setLanguage(new Locale("pt","BR"));

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "a Linguagem nao é suportada!");
                    } else {
                        Log.i("TTS", "Lingua suportada.");
                    }
                    Log.i("TTS", "Inicializado com Sucesso.");
                } else {
                    Toast.makeText(context, "TTS Falha de Inicialização!", Toast.LENGTH_SHORT).show();
                }
            }

        });

        holder.imageViewSentimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,listaComunicacao.getTextoFalar(),Toast.LENGTH_LONG).show();


                String data = listaComunicacao.getTextoFalar();
                Log.e("TTS", "Clicou no Botao: " + data);
                int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);

                if (speechStatus == TextToSpeech.ERROR) {
                    Log.e("TTS", "Erro ao Converter Texto em Fala!");
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}
