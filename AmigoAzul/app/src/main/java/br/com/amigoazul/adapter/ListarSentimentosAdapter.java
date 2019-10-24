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

import com.bumptech.glide.Glide;
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

        final ListaComunicacao listaComunicacao = imageList.get(position);
        final File pathFile = new File(listaComunicacao.getCaminhoFirebase());

        Glide
                .with(context)
                .load(pathFile)
                .centerCrop()
                //.placeholder(R.drawable.loading_spinner)
                .into(holder.imageViewSentimentos);

        /*Picasso.get()
                .load(pathFile)
                //.resize(100,100)
                //  .centerCrop()
                .into(holder.imageViewSentimentos);*/



       /* holder.imageViewSentimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,listaComunicacao.getTextoFalar(),Toast.LENGTH_LONG).show();



            }
        });*/
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}
