package br.com.amigoazul.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vicmikhailau.maskededittext.MaskedFormatter;
import com.vicmikhailau.maskededittext.MaskedWatcher;

import br.com.amigoazul.R;
import br.com.amigoazul.helper.UsuarioDAO;
import br.com.amigoazul.model.ListaUsuario;

public class Cadastro_Activity extends AppCompatActivity {

    //VARIAVEIS
    TextInputEditText nome;
    TextInputEditText dataNasc;
    TextInputEditText email;
    TextInputEditText senha;
    RadioButton Nivel1;
    RadioButton Nivel2;
    RadioButton Nivel3;
    RadioGroup rdGp_GrauTEA;
    String NIVELTEA = "NAO DEFINIDO";
    FloatingActionButton gravar;
    FloatingActionButton deletar;
    ListaUsuario usuarioAtual;


    MaskedFormatter formatadorNascimento = new MaskedFormatter("##/##/####");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.cadastro_usuario);

        //Recuperar produto caso seja edição.
        usuarioAtual = (ListaUsuario) getIntent().getSerializableExtra("usuarioSelecionado");

        //***INSTANCIAR OUTRAS CLASSES*****
        final UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
        final ListaUsuario listaUsuario = new ListaUsuario();

        /**SETAR AS VARIAVEIS COM OS ID DO COMPONENTE DA TELA**/
        //CAMPOS DE TEXTOS
        nome = findViewById(R.id.edt_nome);
        dataNasc = findViewById(R.id.edt_nascimento);
        email = findViewById(R.id.edt_Email);
        senha = findViewById(R.id.edt_Senha);

        //RADIOS BUTTON
        Nivel1 = findViewById(R.id.rdbtn_nivel1);
        Nivel2 = findViewById(R.id.rdbtn_nivel2);
        Nivel3 = findViewById(R.id.rdbtn_nivel3);
        rdGp_GrauTEA = findViewById(R.id.rdGp_GrauTEA);

        //FABs
        gravar = findViewById(R.id.FAB_gravarCasUser);
        deletar = findViewById(R.id.FAB_DeletarCasUser);

        //marcara da data de aniversario
        dataNasc.addTextChangedListener(new MaskedWatcher(formatadorNascimento, dataNasc));

        //deixar FAB DELETAR invisivel ou visivel
        if (usuarioAtual!=null){
            deletar.show();
        }else {
            deletar.hide();
        }


        //Configurar produto na caixa de texto
        if (usuarioAtual != null) {
            nome.setText(usuarioAtual.getNomeUsuario());
            dataNasc.setText(usuarioAtual.getDataNasc());

            if (usuarioAtual.getGrauTEA().equals("nivel 1")) {
                Nivel1.setChecked(true);
            }
            if (usuarioAtual.getGrauTEA().equals("nivel 2")) {
                Nivel2.setChecked(true);
            }
            if (usuarioAtual.getGrauTEA().equals("nivel 3")) {
                Nivel3.setChecked(true);
            }
            email.setText(usuarioAtual.getEmail());
            senha.setText(usuarioAtual.getSenha());
        }
        //BOTAO GRAVAR USUARIO
        gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usuarioAtual == null) {//****SALVAR*****//
                    try {
                        if (nome.getText().length() < 3) {
                            nome.setFocusable(true);
                            nome.setError("Minimo 3 letras");
                        } else if (dataNasc.getText().length() < 10) {
                            dataNasc.setFocusable(true);
                            dataNasc.setError("data Invalida");
                        } else if ((Nivel1.isChecked()==false) && (Nivel2.isChecked()==false) && (Nivel3.isChecked()==false)) {
                            Toast.makeText(getApplicationContext(), "Selecione o NÍVEL do TEA", Toast.LENGTH_SHORT).show();
                            Nivel1.setTextColor(Color.RED);
                            Nivel2.setTextColor(Color.RED);
                            Nivel3.setTextColor(Color.RED);

                        }
                        else{

                            //salva as informaçoes digitadas nos GETTERS AND SETTERS

                            listaUsuario.setNomeUsuario(nome.getText().toString());
                            listaUsuario.setDataNasc(dataNasc.getText().toString());

                            if (Nivel1.isChecked()) {
                                NIVELTEA = "nivel 1";
                                listaUsuario.setGrauTEA(NIVELTEA);
                            }
                            if (Nivel2.isChecked()) {
                                NIVELTEA = "nivel 2";
                                listaUsuario.setGrauTEA(NIVELTEA);
                            }
                            if (Nivel3.isChecked()) {
                                NIVELTEA = "nivel 3";
                                listaUsuario.setGrauTEA(NIVELTEA);
                            }

                            listaUsuario.setEmail(email.getText().toString());
                            listaUsuario.setSenha(senha.getText().toString());
                            listaUsuario.setExcluido("n");

                            usuarioDAO.salvar(listaUsuario);

                            Toast.makeText(getApplicationContext(), "cadastro de usuário salvo com sucesso", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Cadastro_Activity.this, MainMenu.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception erro) {
                        Toast.makeText(getApplicationContext(), "erro ao salvar cadastro de usuário " + erro.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {//****ALTERAR****//

                    try {
                        if (nome.getText().length() < 3) {
                            nome.setFocusable(true);
                            nome.setError("Minimo 3 letras");
                        } else if (dataNasc.getText().length() < 10) {
                            dataNasc.setFocusable(true);
                            dataNasc.setError("data Invalida");
                        } else if ((Nivel1.isChecked()==false) && (Nivel2.isChecked()==false) && (Nivel3.isChecked()==false)) {
                            Toast.makeText(getApplicationContext(), "Selecione o NIVEL do TEA", Toast.LENGTH_SHORT).show();
                            Nivel1.setTextColor(Color.RED);
                            Nivel2.setTextColor(Color.RED);
                            Nivel3.setTextColor(Color.RED);

                        } else {

                            listaUsuario.setId(usuarioAtual.getId());
                            listaUsuario.setNomeUsuario(nome.getText().toString());
                            listaUsuario.setDataNasc(dataNasc.getText().toString());

                            if (Nivel1.isChecked()) {
                                NIVELTEA = "nivel 1";
                                listaUsuario.setGrauTEA(NIVELTEA);
                            }
                            if (Nivel2.isChecked()) {
                                NIVELTEA = "nivel 2";
                                listaUsuario.setGrauTEA(NIVELTEA);
                            }
                            if (Nivel3.isChecked()) {
                                NIVELTEA = "nivel 3";
                                listaUsuario.setGrauTEA(NIVELTEA);
                            }

                            listaUsuario.setEmail(email.getText().toString());
                            listaUsuario.setSenha(senha.getText().toString());
                            listaUsuario.setExcluido(usuarioAtual.getExcluido());

                            usuarioDAO.atualizar(listaUsuario);

                            Toast.makeText(getApplicationContext(), " cadastro de usuario atualizado com sucesso", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } catch (Exception erro) {
                        Toast.makeText(getApplicationContext(), "erro ao atualizar cadastro de usuario " + erro.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        //FAB deletar
        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro_Activity.this);
                builder.setTitle("Confirmar Exclusão");
                builder.setMessage("Deseja Excluir o usuario " + usuarioAtual.getNomeUsuario() +
                        " Nascido em " + usuarioAtual.getDataNasc() + " ?");
                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (usuarioAtual != null) {
                            listaUsuario.setId(usuarioAtual.getId());
                            listaUsuario.setNomeUsuario(usuarioAtual.getNomeUsuario());
                            listaUsuario.setDataNasc(usuarioAtual.getDataNasc());
                            listaUsuario.setEmail(usuarioAtual.getEmail());
                            listaUsuario.setSenha(usuarioAtual.getSenha());
                            listaUsuario.setGrauTEA(usuarioAtual.getGrauTEA());
                            listaUsuario.setExcluido("S");

                            usuarioDAO.atualizar(listaUsuario);
                            Toast.makeText(getApplicationContext(), "Excluido com Sucesso!!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "ERRO AO EXCLUIR!!", Toast.LENGTH_LONG).show();
                            Log.i("ERRO", "ERRO AO EXCLUIR USUARIO DO CADASTRO!!");

                        }
                    }
                });
                builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create();
                builder.show();

            }
        });
    }

}
