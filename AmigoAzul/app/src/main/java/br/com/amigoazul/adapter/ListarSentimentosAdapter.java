package br.com.amigoazul.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import br.com.amigoazul.R;

/**
 * Criado por Lucas Pinheiro on 12/09/2019.
 */
public class ListarSentimentosAdapter extends RecyclerView.Adapter<ListarSentimentosAdapter.ListarComunicacaoAdapterViewHolder> {

    private List<File> imageList;

    private Context c;

    public class ListarComunicacaoAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewSentimentos;

        public ListarComunicacaoAdapterViewHolder(View view) {
            super(view);
            imageViewSentimentos = view.findViewById(R.id.imgbtn_sentim_ID);
        }
    }

    public ListarSentimentosAdapter(Context c, List imageList) {
        this.c = c;
        this.imageList = imageList;
    }

    @Override
    public ListarComunicacaoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_sentimentos_adapter, parent, false);

        return new ListarComunicacaoAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListarComunicacaoAdapterViewHolder holder, int position) {
        final File path = imageList.get(position);



        Picasso.get()
                .load(path)
                //.resize(100,100)
              //  .centerCrop()
                .into(holder.imageViewSentimentos);

        holder.imageViewSentimentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle click event on image
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}
