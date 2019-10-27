package br.com.amigoazul.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.model.ListaComunicacao;

/**
 * Criado por Lucas Pinheiro on 12/09/2019.
 */
public class ListarMontarFreseAdapter extends RecyclerView.Adapter<ListarMontarFreseAdapter.ListarComunicacaoAdapterViewHolder> {

    private List<ListaComunicacao> imageList;

    private Context context;



    public class ListarComunicacaoAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMontarFrase;


        public ListarComunicacaoAdapterViewHolder(View view) {
            super(view);
            imageViewMontarFrase = view.findViewById(R.id.imgbtn_montarFrase_ID);

        }
    }

    public ListarMontarFreseAdapter(Context c, List imageList) {
        this.context = c;
        this.imageList = imageList;
    }

    @Override
    public ListarComunicacaoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_motar_frase_adapter, parent, false);

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
                .into(holder.imageViewMontarFrase);
        }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}
