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
public class ListarObjetosAdapter extends RecyclerView.Adapter<ListarObjetosAdapter.ListarObjetosAdapterViewHolder> {

    private List<File> imageList;

    private Context c;

    public class ListarObjetosAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewObjetos;

        public ListarObjetosAdapterViewHolder(View view) {
            super(view);
            imageViewObjetos = view.findViewById(R.id.imgbtn_objetos_ID);
        }
    }

    public ListarObjetosAdapter(Context c, List imageList) {
        this.c = c;
        this.imageList = imageList;
    }

    @Override
    public ListarObjetosAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_objetos_adapter, parent, false);

        return new ListarObjetosAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListarObjetosAdapterViewHolder holder, int position) {
        final File path = imageList.get(position);



        Picasso.get()
                .load(path)
                .into(holder.imageViewObjetos);

        holder.imageViewObjetos.setOnClickListener(new View.OnClickListener() {
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
