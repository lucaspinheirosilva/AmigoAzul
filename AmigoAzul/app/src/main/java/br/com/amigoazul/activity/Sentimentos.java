package br.com.amigoazul.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.amigoazul.R;
import br.com.amigoazul.adapter.ListarSentimentosAdapter;
import br.com.amigoazul.helper.ComunicacaoDAO;
import br.com.amigoazul.helper.RecyclerItemClickListiner;
import br.com.amigoazul.helper.SALVAR_FOTO;
import br.com.amigoazul.model.ListaComunicacao;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class Sentimentos extends AppCompatActivity {


    //instanciar outras classes
    Splash_Activity splash_activity = new Splash_Activity();
    SALVAR_FOTO salvar_foto = new SALVAR_FOTO();
    ComunicacaoDAO comunicacaoDAO;

    //AlertDialog
    AlertDialog alerta;
    AlertDialog.Builder builder;

    //Texto em fala
    TextToSpeech textToSpeech;

    ListaComunicacao listaComunicacaoSentimentos = new ListaComunicacao();//instaciar objeto Lista de comunicação para obter os GETTER e SETTER


    //Constantes
    private final int CAMERA = 1;
    private final int GALERIA = 2;
    private final int ALTERA_CAMERA = 3;
    private final int ALTERA_GALERIA = 4;


    String[] palavroes = {"porra", "caralho", "buceta", "merda", "cu", "puta", "fuder", "cacete", "foder", "xereca",
            "cu", "cú", "cuzinho", "bosta", "boquete", "cu?", "boquete?", "foda", "fudido","idiota"};

    //tremer a tela
    YoYo.YoYoString animacaoRequired;


    //metodos para pegar a data e hora do disposivo para montar o nome das fotos ao salvar
    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
    Date diaData = new Date();
    String dataFormatada = formataData.format(diaData);

    TextView tituloSentimentos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.sentimentos);

        final String sentimentosAtual = (String) getIntent().getSerializableExtra("LIBERA_EXCLUSAO");

        //setar variaveis com IDs
        FabSpeedDial FAB_camera_Sentimentos = findViewById(R.id.fab_speed_dial_sentimentos);
        tituloSentimentos = findViewById(R.id.txtvw_LabelSentimentos);


        CARREGAR_FOTOS_SENTIMENTOS();


        //pega a "mensagem mandada pelo botao EXCLUIR" liberando ou nao a exclusao
        //muda o titulo da tela
        if (sentimentosAtual != null) {
            tituloSentimentos.setText("Alterar / Excluir Sentimentos");
            tituloSentimentos.setTextColor(Color.parseColor("#F80909"));
        } else {
            tituloSentimentos.setText("Sentimentos");
            tituloSentimentos.setTextColor(Color.parseColor("#052C88"));
        }

        //configuração do idioma e gramatica da fala do dispositivo
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int ttsLang = textToSpeech.setLanguage(new Locale("pt", "BR"));

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "a Linguagem nao é suportada!");
                    } else {
                        Log.i("TTS", "Lingua suportada.");
                    }
                    Log.i("TTS", "Inicializado com Sucesso.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Falha de Inicialização!", Toast.LENGTH_SHORT).show();
                }
            }

        });


        //FloatActionButton para menu de SENTIMENTOS
        FAB_camera_Sentimentos.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.FAB_acao_addImagem:
                        TIRAR_FOTO_ou_GALERIA(false);
                        break;
                    case R.id.FAB_acao_excluirImagem:
                        tituloSentimentos.setText("Alterar / Excluir");
                        Intent intent = new Intent(Sentimentos.this, Sentimentos.class);
                        String blockSentimentos = "LiberadoParaExclusao";
                        intent.putExtra("LIBERA_EXCLUSAO", blockSentimentos);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;
            }
        });
    }

    public void CARREGAR_FOTOS_SENTIMENTOS() {
        //pega a "mensagem mandada pelo botao EXCLUIR" liberando ou nao a exclusao
        final String sentimentosAtual = (String) getIntent().getSerializableExtra("LIBERA_EXCLUSAO");

        //refencia o ID do recyclerView com a variavel
        RecyclerView RCRVW_listarSentimentos = findViewById(R.id.rcrtvw_listarComunicSentimentos);


        File diretorio = new File(splash_activity.meuDirSentimentos.getAbsolutePath());
        //verifica se a pasta das imagens realmente existe
        if (diretorio.exists()) {
            Log.e("SENTIMENTOS", "a pasta é " + diretorio.getAbsolutePath());


            List <ListaComunicacao> list = new ArrayList();
            comunicacaoDAO = new ComunicacaoDAO(getApplicationContext());
            list = comunicacaoDAO.listar_sentimentos();

            //"ADAPTER" que monta as imagens no RecyclerView com 3 colunas
            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            RCRVW_listarSentimentos.setLayoutManager(sglm);
            ListarSentimentosAdapter listarSentimentosAdapter = new ListarSentimentosAdapter(Sentimentos.this, list);
            RCRVW_listarSentimentos.setAdapter(listarSentimentosAdapter);


            final List <ListaComunicacao> finalList = list;//Array de lista de sentimentos que recebe o metodo LISTAR do banco

            RCRVW_listarSentimentos.addOnItemTouchListener(new RecyclerItemClickListiner(getApplicationContext(),
                    RCRVW_listarSentimentos, new RecyclerItemClickListiner.OnItemClickListener() {
                @Override
                public void onItemClick(View view, final int position) {

                    final ListaComunicacao listaComunicacaoSentimentoRCRVW = finalList.get(position);// pega a posição do item clicado

                    if (sentimentosAtual == null) {//se for nulo, nao pode alterar,só vai falar

                        //metodo que pega o que esta no banco (listaComunicacaoSentimentoRCRVW.getTextoFalar()) e reproduz
                        String data = listaComunicacaoSentimentoRCRVW.getTextoFalar();
                        Log.e("TTS", "Clicou no Botao: " + data);
                        int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);

                        if (speechStatus == TextToSpeech.ERROR) {
                            Log.e("TTS", "Erro ao Converter Texto em Fala!");
                        }
                    } else {//se for identificado que foi clicado no botao ALTERAR ele ira apresente um
                        // DIALOG com as informações da imagem para fazer a alteração


                        //alertDialog para ALTERAR
                        builder = new AlertDialog.Builder(Sentimentos.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogText_Fotoview = inflater.inflate(R.layout.texto_foto, null);
                        builder.setView(dialogText_Fotoview);
                        builder.setCancelable(false);

                        final ImageView imagemTirada = dialogText_Fotoview.findViewById(R.id.imgvw_fotoTirada);
                        final TextInputEditText textoFalar = dialogText_Fotoview.findViewById(R.id.edttxt_textoFalar);
                        final TextView textInformativo = dialogText_Fotoview.findViewById(R.id.txtvw_informAlterarFoto);
                        final Button salvar = dialogText_Fotoview.findViewById(R.id.btnSalvar_TextoFalar);
                        Button cancelar = dialogText_Fotoview.findViewById(R.id.btnCancelar_TextoFalar);

                        //seta o texto informativo para alterar a foto e deixa ele VISIVEL
                        textInformativo.setVisibility(View.INVISIBLE);
                        //preenche os campos com os dados
                        textoFalar.setText(listaComunicacaoSentimentoRCRVW.getTextoFalar());
                        //converte o caminho da imagem em um BITMAP
                        final Bitmap bitmap = BitmapFactory.decodeFile(listaComunicacaoSentimentoRCRVW.getCaminhoFirebase());
                        imagemTirada.setImageBitmap(bitmap);

                        salvar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //VALIDAÇÂO DE PALAVROES DIGITADOS
                                if (textoFalar.getText().length() >= 3) {
                                    String[] arrayTextoFalar = textoFalar.getText().toString().split(" ");//separa a frase em palavras e guarda em array

                                    for (int x = 0; x < arrayTextoFalar.length; x++) {
                                        arrayTextoFalar[x] = arrayTextoFalar[x].trim();//percorre o array e tira os espaços em branco
                                    }

                                    for (int i = 0; i < palavroes.length; i++) {
                                        for (int x = 0; x < arrayTextoFalar.length; x++) {
                                            if (arrayTextoFalar[x].equals(palavroes[i])) {
                                                Toast.makeText(getApplicationContext(), "Palavra Inapropriada encontrada..Mude a" +
                                                        " palavra/frase por favor", Toast.LENGTH_LONG).show();
                                                textoFalar.setFocusable(true);
                                                textoFalar.setError("palavra inapropriada");

                                                animacaoRequired = YoYo.with(Techniques.Tada)
                                                        .duration(900)
                                                        .repeat(0)
                                                        .playOn(textoFalar);
                                                return;
                                            }
                                        }
                                    }
                                }


                                if (textoFalar.getText().length() <= 3) {
                                    textoFalar.setError("Informe o Texto por favor com no minimo 3 letras");
                                    textoFalar.setFocusable(true);

                                } else {

                                    //atualiza dados da imagem no BD
                                    if (bitmap != null) {
                                        listaComunicacaoSentimentos = new ListaComunicacao();
                                        listaComunicacaoSentimentos.setId(listaComunicacaoSentimentoRCRVW.getId());
                                        listaComunicacaoSentimentos.setTextoFalar_MontarFrase(null);
                                        listaComunicacaoSentimentos.setExcluido("n");
                                        listaComunicacaoSentimentos.setTipoComunic(listaComunicacaoSentimentoRCRVW.getTipoComunic());
                                        listaComunicacaoSentimentos.setTextoFalar(textoFalar.getText().toString());
                                        listaComunicacaoSentimentos.setCaminhoFirebase(listaComunicacaoSentimentoRCRVW.getCaminhoFirebase());

                                        comunicacaoDAO.atualizar(listaComunicacaoSentimentos);
                                        alerta.cancel();
                                        Intent intent = new Intent(Sentimentos.this, Sentimentos.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(), "SALVO COM SUCESSO", Toast.LENGTH_LONG).show();
                                    }
                                }


                            }
                        });
                        cancelar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alerta.cancel();
                            }
                        });
                        alerta = builder.create();
                        alerta.show();
                        //***fim do ALERTDIALOG ALTERAR
                    }
                }

                @Override
                public void onLongItemClick(View view, int position) {

                    if (sentimentosAtual == null) {

                    } else {
                        // caso o usuario pressione e segura a imagem, o app ira perguntar se ele
                        //quer excluir a imagem mesmo, perante ao click no FAB deletar

                        final ListaComunicacao listaComunicacaoRCRVW = finalList.get(position);// pega a posição do item clicado

                        //alertDialog para EXCLUIR O SENTIMENTO
                        builder = new AlertDialog.Builder(Sentimentos.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogText_Fotoview = inflater.inflate(R.layout.foto_excluir, null);
                        builder.setView(dialogText_Fotoview);
                        builder.setCancelable(false);

                        final ImageView imagemTirada_excluir = dialogText_Fotoview.findViewById(R.id.imgvw_DELETAR_fotoTirada);
                        final EditText id = dialogText_Fotoview.findViewById(R.id.edttxt_Deletar_ID);
                        final EditText tipoComunicacao = dialogText_Fotoview.findViewById(R.id.edttxt_Deletar_TipoComunic);
                        final EditText textoFalar_excluir = dialogText_Fotoview.findViewById(R.id.edttxt_Deletar_textReproduzir);


                        final Button deletar_excluir = dialogText_Fotoview.findViewById(R.id.btnDeletar_FotoDeletar);
                        Button cancelar_excluir = dialogText_Fotoview.findViewById(R.id.btnCancelar_FotoDeletar);

                        //preenche os campos com os dados
                        id.setText(listaComunicacaoRCRVW.getId().toString());
                        tipoComunicacao.setText(listaComunicacaoRCRVW.getTipoComunic());
                        textoFalar_excluir.setText(listaComunicacaoRCRVW.getTextoFalar());

                        //converte o caminho da imagem em um BITMAP
                        final Bitmap bitmap = BitmapFactory.decodeFile(listaComunicacaoRCRVW.getCaminhoFirebase());
                        imagemTirada_excluir.setImageBitmap(bitmap);

                        //deixa os campos inativos para a alteração dos textos
                        id.setEnabled(false);
                        tipoComunicacao.setEnabled(false);
                        textoFalar_excluir.setEnabled(false);

                        deletar_excluir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //DELETAR dados da imagem no BD
                                listaComunicacaoSentimentos = new ListaComunicacao();
                                listaComunicacaoSentimentos.setId(listaComunicacaoRCRVW.getId());
                                listaComunicacaoSentimentos.setTextoFalar_MontarFrase(null);
                                listaComunicacaoSentimentos.setExcluido("s");
                                listaComunicacaoSentimentos.setTipoComunic(listaComunicacaoRCRVW.getTipoComunic());
                                listaComunicacaoSentimentos.setTextoFalar(listaComunicacaoRCRVW.getTextoFalar());
                                listaComunicacaoSentimentos.setCaminhoFirebase(listaComunicacaoRCRVW.getCaminhoFirebase());

                                comunicacaoDAO.atualizar(listaComunicacaoSentimentos);
                                alerta.cancel();
                                Intent intent = new Intent(Sentimentos.this, Sentimentos.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "EXCLUIDO COM SUCESSO", Toast.LENGTH_LONG).show();

                            }
                        });
                        cancelar_excluir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alerta.dismiss();
                            }
                        });
                        alerta = builder.create();
                        alerta.show();
                        //***fim do ALERTDIALOG
                    }
                }

                @Override
                public void onItemClick(AdapterView <?> parent, View view, int position, long id) {

                }
            }));


        } else {
            splash_activity.meuDirSentimentos.mkdirs();
            CARREGAR_FOTOS_SENTIMENTOS();//recursividade
        }

    }


    public void TIRAR_FOTO_ou_GALERIA(final Boolean alteracao) {
        //alertDialog para escoher daonde é a foto
        AlertDialog.Builder builder = new AlertDialog.Builder(Sentimentos.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogCameraGaleriaview = inflater.inflate(R.layout.dialog_camera_galeria, null);
        builder.setView(dialogCameraGaleriaview);

        ImageButton camera = dialogCameraGaleriaview.findViewById(R.id.imgbtn_alerDialcamera);
        ImageButton galeria = dialogCameraGaleriaview.findViewById(R.id.imgbtn_alerDialgaleria);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (alteracao == false) {
                    startActivityForResult(imageIntent, CAMERA);
                } else {
                    startActivityForResult(imageIntent, ALTERA_CAMERA);
                }
                alerta.cancel();

            }
        });
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPegaFoto = new Intent(Intent.ACTION_PICK);
                intentPegaFoto.setType("image/*");
                if (alteracao == false) {
                    startActivityForResult(intentPegaFoto, GALERIA);
                } else {
                    startActivityForResult(intentPegaFoto, ALTERA_GALERIA);
                }
                alerta.cancel();


            }
        });
        alerta = builder.create();
        alerta.show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        comunicacaoDAO = new ComunicacaoDAO(getApplicationContext());

        if (requestCode == CAMERA && resultCode == RESULT_OK) {//tirar foto(SALVAR)
            Bundle extra = data.getExtras();
            final Bitmap imagem = (Bitmap) extra.get("data");

            //alertDialog para salvar a foto da CAMERA
            builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogText_Fotoview = inflater.inflate(R.layout.texto_foto, null);
            builder.setView(dialogText_Fotoview);
            builder.setCancelable(false);

            final ImageView imagemTirada = dialogText_Fotoview.findViewById(R.id.imgvw_fotoTirada);
            final EditText textoFalar = dialogText_Fotoview.findViewById(R.id.edttxt_textoFalar);
            final Button salvar = dialogText_Fotoview.findViewById(R.id.btnSalvar_TextoFalar);
            Button cancelar = dialogText_Fotoview.findViewById(R.id.btnCancelar_TextoFalar);

            imagemTirada.setImageBitmap(imagem);

            salvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //VALIDAÇÂO DE PALAVROES DIGITADOS
                    if (textoFalar.getText().length() >= 3) {
                        String[] arrayTextoFalar = textoFalar.getText().toString().split(" ");//separa a frase em palavras e guarda em array

                        for (int x = 0; x < arrayTextoFalar.length; x++) {
                            arrayTextoFalar[x] = arrayTextoFalar[x].trim();//percorre o array e tira os espaços em branco
                        }

                        for (int i = 0; i < palavroes.length; i++) {
                            for (int x = 0; x < arrayTextoFalar.length; x++) {
                                if (arrayTextoFalar[x].equals(palavroes[i])) {
                                    Toast.makeText(getApplicationContext(), "Palavra Inapropriada encontrada..Mude a" +
                                            " palavra/frase por favor", Toast.LENGTH_LONG).show();
                                    textoFalar.setFocusable(true);
                                    textoFalar.setError("palavra inapropriada");

                                    animacaoRequired = YoYo.with(Techniques.Tada)
                                            .duration(900)
                                            .repeat(0)
                                            .playOn(textoFalar);
                                    return;
                                }
                            }
                        }
                    } else {
                        Log.e("DICIONARIO DE PALAVRAO", "nada de palavrao encontrado");
                    }


                    if (textoFalar.getText().length() <= 2) {
                        textoFalar.setError("minimo 3 letras");
                        textoFalar.setFocusable(true);
                        animacaoRequired = YoYo.with(Techniques.Tada)
                                .duration(900)
                                .repeat(0)
                                .playOn(textoFalar);

                    } else {

                        //Savar dados da imagem no BD
                        if (imagem != null) {
                            String nomeDoArquivo = "AZ-" + dataFormatada + ".JPG";
                            listaComunicacaoSentimentos.setTextoFalar(textoFalar.getText().toString());
                            listaComunicacaoSentimentos.setTextoFalar_MontarFrase(null);
                            listaComunicacaoSentimentos.setCaminhoFirebase(splash_activity.meuDirSentimentos + "/" + nomeDoArquivo);
                            listaComunicacaoSentimentos.setTipoComunic("sentimentos");
                            listaComunicacaoSentimentos.setExcluido("n");

                            comunicacaoDAO.salvar(listaComunicacaoSentimentos);

                            salvar_foto.SALVAR_IMAGEM_DIRECTORIO(imagem, nomeDoArquivo, splash_activity.meuDirSentimentos.getAbsolutePath());
                            alerta.cancel();
                            Intent intent = new Intent(Sentimentos.this, Sentimentos.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Sentimentos.this, "Erro ao salvar imagem, refaça a operação por favor!", Toast.LENGTH_SHORT).show();
                            alerta.cancel();
                        }
                    }
                }
            });
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alerta.cancel();

                }
            });
            alerta = builder.create();
            alerta.show();
        }

//*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

        //foto da galeria (SALVAR)
        if (resultCode == RESULT_OK && requestCode == GALERIA) {
            //Pegamos a URI da imagem...
            Uri uriSelecionada = data.getData();

            //pegar a imagem e converte  para um path compativel para inserir no ImageView
            String[] colunas = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uriSelecionada, colunas, null, null, null);
            cursor.moveToFirst();
            int indexColuna = cursor.getColumnIndex(colunas[0]);
            String pathImg = cursor.getString(indexColuna);
            cursor.close();

            final Bitmap imagem = BitmapFactory.decodeFile(pathImg);
            // criamos um File com o diretório selecionado!
            final File selecionada = new File(salvar_foto.getRealPathFromURI(uriSelecionada, getApplicationContext()));
            // Caso não exista o doretório, vamos criar!
            final File rootPath = new File(splash_activity.meuDirSentimentos.getAbsolutePath());
            if (!rootPath.exists()) {
                rootPath.mkdirs();
            }

            // Criamos um file, com o DIRETORIO, com o mesmo nome novo
            final File novaImagem = new File(rootPath, "AZ-" + dataFormatada + ".JPG");

            //alertDialog para salvar foto que foi pego da GALERIA
            builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogText_Fotoview = inflater.inflate(R.layout.texto_foto, null);
            builder.setView(dialogText_Fotoview);
            builder.setCancelable(false);

            final ImageView imagemTirada = dialogText_Fotoview.findViewById(R.id.imgvw_fotoTirada);
            imagemTirada.setImageBitmap(imagem);
            final Button salvar = dialogText_Fotoview.findViewById(R.id.btnSalvar_TextoFalar);
            final EditText textoFalar = dialogText_Fotoview.findViewById(R.id.edttxt_textoFalar);
            Button cancelar = dialogText_Fotoview.findViewById(R.id.btnCancelar_TextoFalar);

            salvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //VALIDAÇÂO DE PALAVROES DIGITADOS
                    if (textoFalar.getText().length() >= 3) {
                        String[] arrayTextoFalar = textoFalar.getText().toString().split(" ");//separa a frase em palavras e guarda em array

                        for (int x = 0; x < arrayTextoFalar.length; x++) {
                            arrayTextoFalar[x] = arrayTextoFalar[x].trim();//percorre o array e tira os espaços em branco
                        }

                        for (int i = 0; i < palavroes.length; i++) {
                            for (int x = 0; x < arrayTextoFalar.length; x++) {
                                if (arrayTextoFalar[x].equals(palavroes[i])) {
                                    Toast.makeText(getApplicationContext(), "Palavra Inapropriada encontrada..Mude a" +
                                            " palavra/frase por favor", Toast.LENGTH_LONG).show();
                                    textoFalar.setFocusable(true);
                                    textoFalar.setError("palavra inapropriada");

                                    animacaoRequired = YoYo.with(Techniques.Tada)
                                            .duration(900)
                                            .repeat(0)
                                            .playOn(textoFalar);
                                    return;
                                }
                            }
                        }
                    } else {
                        Log.e("DICIONARIO DE PALAVRAO", "nada de palavrao encontrado");
                    }

                    if (textoFalar.getText().length() < 2) {
                        textoFalar.setError("Informe o Texto por favor com no minimo 3 letras");
                        textoFalar.setFocusable(true);

                    } else {
                        //Savar dados da imagem no BD
                        if (imagem != null) {
                            String nomeDoArquivo = "AZ-" + dataFormatada + ".JPG";
                            listaComunicacaoSentimentos.setTextoFalar(textoFalar.getText().toString());
                            listaComunicacaoSentimentos.setTextoFalar_MontarFrase(null);
                            listaComunicacaoSentimentos.setCaminhoFirebase(splash_activity.meuDirSentimentos + "/" + nomeDoArquivo);
                            listaComunicacaoSentimentos.setTipoComunic("sentimentos");
                            listaComunicacaoSentimentos.setExcluido("n");

                            comunicacaoDAO.salvar(listaComunicacaoSentimentos);

                            Intent intent = new Intent(Sentimentos.this, Sentimentos.class);
                            startActivity(intent);
                            finish();

                            try {//Movemos o arquivo!
                                salvar_foto.COPIAR_ARQUIVO(selecionada, novaImagem, getApplicationContext());
                                Toast.makeText(Sentimentos.this, "Imagem Salva com sucesso!", Toast.LENGTH_SHORT).show();
                                alerta.cancel();

                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alerta.cancel();

                }
            });
            alerta = builder.create();
            alerta.show();
        }
    }

    public void onRestart() {
        super.onRestart();
        tituloSentimentos.setText("Sentimentos");
        tituloSentimentos.setTextColor(Color.parseColor("#052C88"));

    }

}
