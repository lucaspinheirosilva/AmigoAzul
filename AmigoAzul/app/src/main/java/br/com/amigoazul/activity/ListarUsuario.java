package br.com.amigoazul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.amigoazul.R;
import br.com.amigoazul.adapter.ListarUsuarioAdapter;
import br.com.amigoazul.helper.RecyclerItemClickListiner;
import br.com.amigoazul.helper.UsuarioDAO;
import br.com.amigoazul.model.ListaUsuario;

public class ListarUsuario extends AppCompatActivity {

    ListarUsuarioAdapter listarUsuarioAdapter;
    RecyclerView rcrvwListagemUsuario;
    Button BtnNovoUsuario;
    ListaUsuario usuarioselecionado;
    UsuarioDAO usuarioDAO;
    List <ListaUsuario> listUsuario = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.listar_usuario);

        //Instanciar outras classes
        usuarioDAO = new UsuarioDAO(getApplicationContext());

        //setar IDs com variaveis
        rcrvwListagemUsuario = findViewById(R.id.rcvw_listarUsuario);
        BtnNovoUsuario = findViewById(R.id.btn_novo_cadastro);

        //Recuperar usuario caso seja edição.
        final String bloqueioSplash = (String) getIntent().getSerializableExtra("BLOQUEIO_SPLASH");

        CarregarListaUsuarios();

        BtnNovoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListarUsuario.this, Introducao_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        rcrvwListagemUsuario.addOnItemTouchListener(
                new RecyclerItemClickListiner(getApplicationContext(),
                        rcrvwListagemUsuario, new RecyclerItemClickListiner.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                    }

                    @Override
                    public void onItemClick(View view, int position) {

                        if (bloqueioSplash == null) {//seleciona usuario e vai para tela de edição de cadastro
                            //Recuper produto para edição
                            usuarioselecionado = listUsuario.get(position);

                            //Enviar produto para a tela de adicionar usuario
                            Intent intent = new Intent(ListarUsuario.this, Cadastro_Activity.class);
                            intent.putExtra("usuarioSelecionado", usuarioselecionado);
                            startActivity(intent);
                        } else {//senão ele seleciona usuario e vai para tela de menu principal
                             Toast.makeText(getApplicationContext(), "usuario SELECIONADO", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ListarUsuario.this,MainMenu.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }


    public void CarregarListaUsuarios() {/**metodos para fazer a listagem dos usuarios**/

        //Listar usuario
        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
        listUsuario = new ArrayList <>();
        listUsuario = usuarioDAO.listar();

        //Configurar ADAPTER
        listarUsuarioAdapter = new ListarUsuarioAdapter(listUsuario);

        //Configurar RECYCLERVIEW
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        /**RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);*/ //faz aparecer em 2 colunas

        rcrvwListagemUsuario.setLayoutManager(layoutManager);
        rcrvwListagemUsuario.setHasFixedSize(true);

        /**https://www.udemy.com/curso-de-desenvolvimento-android-oreo/learn/lecture/9124084#questions/6267586**/
        //se ja tiver a linha de decoração ele remove e coloca novamnete para nao ficar criando
        // linha amais a cada refresh da activity
        while (rcrvwListagemUsuario.getItemDecorationCount() > 0) {
            rcrvwListagemUsuario.removeItemDecorationAt(0);
        }
        rcrvwListagemUsuario.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        rcrvwListagemUsuario.setAdapter(listarUsuarioAdapter);
    }

    public void onResume() {//recarrega a listagem ao fazer alguma ação
        super.onResume();
        CarregarListaUsuarios();
    }

}