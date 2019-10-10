package br.com.amigoazul.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.model.ListaUsuario;

/**
 * Criado por Lucas Pinheiro on 12/09/2019.
 */
public class ListarComunicacaoAdapter extends RecyclerView.Adapter<ListarComunicacaoAdapter.MyViewHolder> {

    List <File> listaComunicacao;


    public ListarComunicacaoAdapter(List<File> listaComunicacao) {
        this.listaComunicacao = listaComunicacao;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemLista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lista_sentimentos_adapter,viewGroup,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        ListaUsuario listaUsuario = listaUsuarios.get(i);
        String nomeUsuarioListagem = listaUsuario.getNomeUsuario()+" -- "+listaUsuario.getDataNasc()+" -- "+listaUsuario.getGrauTEA();
        myViewHolder.txtvwListaUser.setText(nomeUsuarioListagem);

    }

    @Override
    public int getItemCount() {
        return this.listaUsuarios.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtvwListaUser;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtvwListaUser = itemView.findViewById(R.id.txtListaUser);
        }
    }

}