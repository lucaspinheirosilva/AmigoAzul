package br.com.amigoazul.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.vicmikhailau.maskededittext.MaskedFormatter;
import com.vicmikhailau.maskededittext.MaskedWatcher;

import java.text.SimpleDateFormat;

import br.com.amigoazul.R;
import br.com.amigoazul.helper.UsuarioDAO;
import br.com.amigoazul.model.ListaUsuario;

public class Cadastro_Activity extends AppCompatActivity {

    //VARIAVEIS
    EditText nome;
    EditText dataNasc;
    EditText email;
    EditText senha;
    RadioButton Nivel1;
    RadioButton Nivel2;
    RadioButton Nivel3;
    RadioGroup rdGp_GrauTEA;
    String NIVELTEA = "NAO DEFINIDO";
    ImageView gravar;
    ImageView deletar;
    ListaUsuario usuarioAtual;

    YoYo.YoYoString animacaoRequired;


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

        //AlertDialog
        AlertDialog alerta = null;
        AlertDialog.Builder builder;

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

        //BOTOES
        gravar = findViewById(R.id.imgbtn_SalvarCadastro);
        deletar = findViewById(R.id.imgbtn_deletarCadastro);

        //mascara da data de aniversario
        dataNasc.addTextChangedListener(new MaskedWatcher(formatadorNascimento, dataNasc));

        //deixar botao DELETAR invisivel ou visivel
        if (usuarioAtual != null) {
            deletar.setVisibility(View.VISIBLE);
        } else {
            deletar.setVisibility(View.INVISIBLE);
        }


        //verificação de texto digitado
        nome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (nome.getText().length() > 49) {
                    nome.setError("LIMITE DE CARACTERES ATINGIDO");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        senha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (senha.getText().length() > 14) {
                    senha.setError("LIMITE DE CARACTERES ATINGIDO");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //Configurar usuario na caixa de texto
        if (usuarioAtual != null) {
            nome.setText(usuarioAtual.getNomeUsuario());
            dataNasc.setText(usuarioAtual.getDataNasc());

            if (usuarioAtual.getGrauTEA().equals("NIVEL 1")) {
                Nivel1.setChecked(true);
            }
            if (usuarioAtual.getGrauTEA().equals("NIVEL 2")) {
                Nivel2.setChecked(true);
            }
            if (usuarioAtual.getGrauTEA().equals("NIVEL 3")) {
                Nivel3.setChecked(true);
            }
            email.setText(usuarioAtual.getEmail());
            senha.setText(usuarioAtual.getSenha());
        }
        //BOTAO GRAVAR USUARIO
        gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataNasc.getText().length() == 10) {

                    //pega a data do sistema
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String dateString = sdf.format(date);


                    String[] arrayDataSistema = dateString.split("/");//separa a frase em palavras e guarda em array
                    String[] arrayDataInformada = dataNasc.getText().toString().split("/");//separa a frase em palavras e guarda em array
                    int[] arrayDataDigitadaConvertida = new int[3];//cria um array de inteiro de 3 posiçoes vazio
                    int[] arrayDataSistemaConvertida = new int[3];//cria um array de inteiro de 3 posiçoes vazio

                    for (int i = 0; i < arrayDataInformada.length; i++) {
                        arrayDataInformada[i] = arrayDataInformada[i].trim();//percorre o array e tira os espaços em branco
                        arrayDataSistema[i] = arrayDataSistema[i].trim();//percorre o array e tira os espaços em branco
                        arrayDataDigitadaConvertida[i] = Integer.parseInt(arrayDataInformada[i]);//pega os dados do array de String e passa para o array de inteiro
                        arrayDataSistemaConvertida[i] = Integer.parseInt(arrayDataSistema[i]);//pega os dados do array de String e passa para o array de inteiro

                    }

                    for (int i = 0; i < arrayDataInformada.length; i++) { //percorre todos os campos do array
                        if ((arrayDataDigitadaConvertida[0] <= 0 || arrayDataDigitadaConvertida[0] > 31)) {//DIA
                            dataNasc.setFocusable(true);
                            dataNasc.setError("DIA INVÁLIDO");
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptDataNasc));
                            return;

                        } else if ((arrayDataDigitadaConvertida[1] <= 0 || arrayDataDigitadaConvertida[1] > 12)) {//MES
                            dataNasc.setFocusable(true);
                            dataNasc.setError("MÊS INVÁLIDO");
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptDataNasc));
                            return;

                        } else if ((arrayDataDigitadaConvertida[2] < 1920) ||
                                (arrayDataDigitadaConvertida[2] > arrayDataSistemaConvertida[2])) {//ANO

                            dataNasc.setFocusable(true);
                            dataNasc.setError("ANO INVÁLIDO(MIN 1920 MAX ANO ATUAL)");
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptDataNasc));
                            return;
                        } else if (arrayDataDigitadaConvertida[2] == arrayDataSistemaConvertida[2]) {//se o ANO digitado for igual ao ANO do sistema
                            if ((arrayDataDigitadaConvertida[1] > arrayDataSistemaConvertida[1])) {//se o MES digitado foi maior que o mes do sistema
                                dataNasc.setFocusable(true);
                                dataNasc.setError("MÊS INVÁLIDO");
                                animacaoRequired = YoYo.with(Techniques.Tada)
                                        .duration(900)
                                        .repeat(0)
                                        .playOn(findViewById(R.id.txtImptDataNasc));
                                return;
                            }
                            else if(arrayDataDigitadaConvertida[1]==arrayDataSistemaConvertida[1]){
                                if (arrayDataDigitadaConvertida[0]>arrayDataSistemaConvertida[0]){
                                    dataNasc.setFocusable(true);
                                    dataNasc.setError("DIA INVÁLIDO");
                                    animacaoRequired = YoYo.with(Techniques.Tada)
                                            .duration(900)
                                            .repeat(0)
                                            .playOn(findViewById(R.id.txtImptDataNasc));
                                    return;
                                }

                            }

                        }

                    }
                } else {
                    //nada
                }

                if (usuarioAtual == null) {//****SALVAR*****//
                    try {
                        if (nome.getText().length() < 3) {
                            nome.setFocusable(true);
                            nome.setError("MINÍMO 3 LETRAS");
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptNome));
                        } else if (dataNasc.getText().length() < 10) {
                            dataNasc.setFocusable(true);
                            dataNasc.setError("DATA INVÁLIDA");
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptDataNasc));
                        } else if ((Nivel1.isChecked() == false) && (Nivel2.isChecked() == false) && (Nivel3.isChecked() == false)) {
                            Toast.makeText(getApplicationContext(), "SELECIONE O NÍVEL DO TEA", Toast.LENGTH_SHORT).show();
                            Nivel1.setTextColor(Color.RED);
                            Nivel2.setTextColor(Color.RED);
                            Nivel3.setTextColor(Color.RED);

                            animacaoRequired = YoYo.with(Techniques.Shake)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.rdbtn_nivel1));
                            animacaoRequired = YoYo.with(Techniques.Shake)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.rdbtn_nivel2));
                            animacaoRequired = YoYo.with(Techniques.Shake)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.rdbtn_nivel3));

                        }
                        //VALIDAÇÃO DE EMAIL
                        else if ((email.getText().length() != 0) &&
                                (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())) {

                            email.setError("E-MAIL INVÁLIDO");
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptEmail));

                        } else if ((email.getText().length() != 0) && (senha.getText().length() == 0)) {
                            senha.setError("SENHA REQUERIDA");
                            Toast.makeText(getApplicationContext(), "VOCE INFORMOU UM EMAIL, É OBRIGATÓRIO " +
                                    "INFORMAR A SENHA PARA SEGURANÇA DOS DADOS", Toast.LENGTH_LONG).show();
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptSenha));
                        } else if ((email.getText().length() != 0) && (senha.getText().length() > 1) && (senha.getText().length() <= 5)) {
                            senha.setError("MINÍMO 6 CARACTERES");
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptSenha));

                        } else {
                            //salva as informaçoes digitadas nos GETTERS AND SETTERS

                            listaUsuario.setNomeUsuario(nome.getText().toString().toUpperCase());
                            listaUsuario.setDataNasc(dataNasc.getText().toString().toUpperCase());

                            if (Nivel1.isChecked()) {
                                NIVELTEA = "NIVEL 1";
                                listaUsuario.setGrauTEA(NIVELTEA);
                            }
                            if (Nivel2.isChecked()) {
                                NIVELTEA = "NIVEL 2";
                                listaUsuario.setGrauTEA(NIVELTEA);
                            }
                            if (Nivel3.isChecked()) {
                                NIVELTEA = "NIVEL 3";
                                listaUsuario.setGrauTEA(NIVELTEA);

                            }

                            listaUsuario.setEmail(email.getText().toString().toUpperCase());
                            listaUsuario.setSenha(senha.getText().toString().toUpperCase());
                            listaUsuario.setExcluido("N");

                            usuarioDAO.salvar(listaUsuario);

                            Toast.makeText(getApplicationContext(), "CADASTROS DE USUÁRIO SALVO COM SUCESSO", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Cadastro_Activity.this, MainMenu.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception erro) {
                        Toast.makeText(getApplicationContext(), "ERRO AO SALVAR CADASTRO DE USUÁRIO " + erro.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {//****ALTERAR****//

                    try {
                        if (nome.getText().length() < 3) {
                            nome.setFocusable(true);
                            nome.setError("MINÍMO 3 LETRAS");
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptNome));
                        } else if (dataNasc.getText().length() < 10) {
                            dataNasc.setFocusable(true);
                            dataNasc.setError("DATA INVÁLIDA");
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptDataNasc));
                        } else if ((Nivel1.isChecked() == false) && (Nivel2.isChecked() == false) && (Nivel3.isChecked() == false)) {
                            Toast.makeText(getApplicationContext(), "SELECIONE O NÍVEL DO TEA", Toast.LENGTH_SHORT).show();
                            Nivel1.setTextColor(Color.RED);
                            Nivel2.setTextColor(Color.RED);
                            Nivel3.setTextColor(Color.RED);
                        }

                        //VALIDAÇÃO DE EMAIL
                        else if ((email.getText().length() != 0) &&
                                (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())) {

                            email.setError("E-MAIL INVÁLIDO");
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptEmail));

                        } else if ((email.getText().length() != 0) && (senha.getText().length() == 0)) {
                            senha.setError("SENHA REQUERIDA");
                            Toast.makeText(getApplicationContext(), "VOCE INFORMOU UM EMAIL, É OBRIGATÓRIO " +
                                    "INFORMAR A SENHA PARA SEGURANÇA DOS DADOS", Toast.LENGTH_LONG).show();
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptSenha));
                        } else if ((email.getText().length() != 0) && (senha.getText().length() > 1) && (senha.getText().length() <= 5)) {
                            senha.setError("MÍNIMO 6 CARACTERES");
                            animacaoRequired = YoYo.with(Techniques.Tada)
                                    .duration(900)
                                    .repeat(0)
                                    .playOn(findViewById(R.id.txtImptSenha));

                        } else {

                            listaUsuario.setId(usuarioAtual.getId());
                            listaUsuario.setNomeUsuario(nome.getText().toString().toUpperCase());
                            listaUsuario.setDataNasc(dataNasc.getText().toString().toUpperCase());

                            if (Nivel1.isChecked()) {
                                NIVELTEA = "NIVEL 1";
                                listaUsuario.setGrauTEA(NIVELTEA);
                            }
                            if (Nivel2.isChecked()) {
                                NIVELTEA = "NIVEL 2";
                                listaUsuario.setGrauTEA(NIVELTEA);
                            }
                            if (Nivel3.isChecked()) {
                                NIVELTEA = "NIVEL 3";
                                listaUsuario.setGrauTEA(NIVELTEA);
                            }

                            listaUsuario.setEmail(email.getText().toString().toUpperCase());
                            listaUsuario.setSenha(senha.getText().toString().toUpperCase());
                            listaUsuario.setExcluido(usuarioAtual.getExcluido());

                            usuarioDAO.atualizar(listaUsuario);

                            Toast.makeText(getApplicationContext(), " CADASTRO DE USUÁRIO ATUALIZADO COM SUCESSO", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } catch (Exception erro) {
                        Toast.makeText(getApplicationContext(), "ERRO AO ATUALIZAR CADASTRO DE USUÁRIO " + erro.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        //FAB deletar
        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro_Activity.this);
                builder.setTitle("CONFIRMA EXCLUSÃO");
                builder.setMessage("DESEJA EXCLUIR O USUÁRIO " + usuarioAtual.getNomeUsuario().toUpperCase() +
                        " NASCIDO EM " + usuarioAtual.getDataNasc() + " ?");
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
                            Toast.makeText(getApplicationContext(), "EXCLUIDO COM SUCESSO!!", Toast.LENGTH_LONG).show();
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


