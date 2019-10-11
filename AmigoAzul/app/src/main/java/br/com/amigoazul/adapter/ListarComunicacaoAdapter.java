package br.com.amigoazul.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.model.ListaUsuario;

/**
 * Criado por Lucas Pinheiro on 12/09/2019.
 */
public class ListarComunicacaoAdapter extends RecyclerView.Adapter<ListarComunicacaoAdapter.ListarComunicacaoAdapterViewHolder> {

    private List<File> imageList;

    private Context c;

    public class ListarComunicacaoAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView siv;

        public ListarComunicacaoAdapterViewHolder(View view) {
            super(view);
            siv = view.findViewById(R.id.imgbtn_sentim_ID);
        }
    }

    public ListarComunicacaoAdapter(Context c, List imageList) {
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
                .into(holder.siv);

        holder.siv.setOnClickListener(new View.OnClickListener() {
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
