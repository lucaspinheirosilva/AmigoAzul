package br.com.amigoazul.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class MontarFrase extends AppCompatActivity {

    EditText fraseMontada;
    ImageButton playAudio;
    ImageButton stopAudio;
    ImageButton limparAudio;
    String juntarPalavras = "";

    //instanciar outras classes
    Splash_Activity splash_activity = new Splash_Activity();
    SALVAR_FOTO salvar_foto = new SALVAR_FOTO();
    ComunicacaoDAO comunicacaoDAO;

    //AlertDialog
    AlertDialog alerta;
    AlertDialog.Builder builder;

    //Texto em fala
    TextToSpeech textToSpeech;

    ListaComunicacao listaComunicacaoMontarFrase = new ListaComunicacao();//instaciar objeto Lista de comunicação para obter os GETTER e SETTER


    //Constantes
    private final int CAMERA = 1;
    private final int GALERIA = 2;

    //metodos para pegar a data e hora do disposivo para montar o nome das fotos ao salvar
    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
    Date diaData = new Date();
    String dataFormatada = formataData.format(diaData);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//esconder a actionBar
        setContentView(R.layout.montar_frase);

        //setar variaveis com IDs
        FabSpeedDial FAB_camera_Montar_Frase = findViewById(R.id.fab_speed_dial_MontarFrase);
        fraseMontada = findViewById(R.id.edttxt_MF_fraseMontada);
        playAudio = findViewById(R.id.imgbtn_MF_playAudio);
        stopAudio = findViewById(R.id.imgbtn_MF_StopAudio);
        limparAudio = findViewById(R.id.imgbtn_MF_LimparAudio);

        CARREGAR_FOTOS_SENTIMENTOS();

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
        //Ação quando clicado no botao de reproduzir audio
        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chama o metodo REPREPRODUZIR_SOM e passa para ele o texto contido no campo fraseMontada convertido para String
                REPRODUZIR_SOM(fraseMontada.getText().toString());
            }
        });

        //Ação quando clicado no botao de para audio
        stopAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PARAR_REPRODUZIR_SOM();
            }
        });
        //Ação quando clicado no botao de limpar  texto audio
        limparAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fraseMontada.setText("");
                juntarPalavras="";
            }
        });



        //FloatActionButton para menu de SENTIMENTOS
        FAB_camera_Montar_Frase.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.FAB_acao_addImagem:
                        TIRAR_FOTO_ou_GALERIA();
                        break;
                    case R.id.FAB_acao_excluirImagem:
                        Intent intent = new Intent(MontarFrase.this, MontarFrase.class);
                        String blockSentimentos = "LiberadoParaExclusao";
                        intent.putExtra("LIBERA_EXCLUSAO", blockSentimentos);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    public void CARREGAR_FOTOS_SENTIMENTOS() {
        //pega a "mensagem mandada pelo botao EXCLUIR" liberando ou nao a exclusao
        final String MontarFraseAtual = (String) getIntent().getSerializableExtra("LIBERA_EXCLUSAO");

        //refencia o ID do recyclerView com a variavel
        RecyclerView RCRVW_listarMontarFrase = findViewById(R.id.rcrtvw_listarMontarFrese);


        File diretorio = new File(splash_activity.meuDirMontarFreses.getAbsolutePath());
        //verifica se a pasta das imagens realmente existe
        if (diretorio.exists()) {
            Log.e("MONTAR FRASE", "a pasta é " + diretorio.getAbsolutePath());


            List <ListaComunicacao> list = new ArrayList();
            comunicacaoDAO = new ComunicacaoDAO(getApplicationContext());
            list = comunicacaoDAO.listar_montarFrases();

            //"ADAPTER" que monta as imagens no RecyclerView com 3 colunas
            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            RCRVW_listarMontarFrase.setLayoutManager(sglm);
            ListarSentimentosAdapter listarSentimentosAdapter = new ListarSentimentosAdapter(MontarFrase.this, list);
            RCRVW_listarMontarFrase.setAdapter(listarSentimentosAdapter);


            final List <ListaComunicacao> finalList = list;//Array de lista de sentimentos que recebe o metodo LISTAR do banco

            RCRVW_listarMontarFrase.addOnItemTouchListener(new RecyclerItemClickListiner(getApplicationContext(),
                    RCRVW_listarMontarFrase, new RecyclerItemClickListiner.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    final ListaComunicacao listaComunicacaoMontarFraseRCRVW = finalList.get(position);// pega a posição do item clicado

                    if (MontarFraseAtual == null) {//se for nulo, nao pode alterar,só vai falar

                        //metodo que pega o que esta no banco (listaComunicacaoSentimentoRCRVW.getTextoFalar_MontarFrase()) e reproduz

                        juntarPalavras = juntarPalavras + " " + listaComunicacaoMontarFraseRCRVW.getTextoFalar_MontarFrase();
                        fraseMontada.setText(juntarPalavras);


                    } else {
                        //se for identificado que foi clicado no botao EXCLUIR ele ira apresente um
                        // DIALOG com as informações da imagem para fazer a alteração
                        //alertDialog para ALTERAR
                        builder = new AlertDialog.Builder(MontarFrase.this);
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
                        textInformativo.setVisibility(View.VISIBLE);
                        //preenche os campos com os dados
                        textoFalar.setText(listaComunicacaoMontarFraseRCRVW.getTextoFalar());
                        //converte o caminho da imagem em um BITMAP
                        final Bitmap bitmap = BitmapFactory.decodeFile(listaComunicacaoMontarFraseRCRVW.getCaminhoFirebase());
                        imagemTirada.setImageBitmap(bitmap);

                        imagemTirada.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Implementar essa Função", Toast.LENGTH_LONG).show();
                            }

                        });
                        salvar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (textoFalar.getText().length() < 2) {
                                    textoFalar.setError("Informe o Texto por favor com no minimo 2 letras");
                                    textoFalar.setFocusable(true);

                                } else {

                                    //atualiza dados da imagem no BD
                                    if (bitmap != null) {
                                        listaComunicacaoMontarFrase = new ListaComunicacao();
                                        listaComunicacaoMontarFrase.setId(listaComunicacaoMontarFraseRCRVW.getId());
                                        listaComunicacaoMontarFrase.setTextoFalar_MontarFrase(textoFalar.getText().toString());
                                        listaComunicacaoMontarFrase.setExcluido("n");
                                        listaComunicacaoMontarFrase.setTipoComunic(listaComunicacaoMontarFraseRCRVW.getTipoComunic());
                                        listaComunicacaoMontarFrase.setTextoFalar(null);
                                        listaComunicacaoMontarFrase.setCaminhoFirebase(listaComunicacaoMontarFraseRCRVW.getCaminhoFirebase());

                                        comunicacaoDAO.atualizar(listaComunicacaoMontarFrase);
                                        alerta.cancel();
                                        Intent intent = new Intent(MontarFrase.this, MontarFrase.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "SALVO COM SUCESSO", Toast.LENGTH_LONG).show();
                                    }
                                }


                            }
                        });
                        cancelar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alerta.dismiss();
                            }
                        });
                        alerta = builder.create();
                        alerta.show();
                        //***fim do ALERTDIALOG ALTERAR
                    }
                }

                @Override
                public void onLongItemClick(View view, int position) {

                    if (MontarFraseAtual == null) {

                    } else {
                        // caso o usuario pressione e segura a imagem, o app ira perguntar se ele
                        //quer excluir a imagem mesmo, perante ao click no FAB deletar

                        final ListaComunicacao listaComunicacaoRCRVW = finalList.get(position);// pega a posição do item clicado

                        //alertDialog para EXCLUIR
                        builder = new AlertDialog.Builder(MontarFrase.this);
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
                        textoFalar_excluir.setText(listaComunicacaoRCRVW.getTextoFalar_MontarFrase());

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
                                listaComunicacaoMontarFrase = new ListaComunicacao();
                                listaComunicacaoMontarFrase.setId(listaComunicacaoRCRVW.getId());
                                listaComunicacaoMontarFrase.setTextoFalar_MontarFrase(listaComunicacaoRCRVW.getTextoFalar_MontarFrase());
                                listaComunicacaoMontarFrase.setExcluido("s");
                                listaComunicacaoMontarFrase.setTipoComunic(listaComunicacaoRCRVW.getTipoComunic());
                                listaComunicacaoMontarFrase.setTextoFalar(null);
                                listaComunicacaoMontarFrase.setCaminhoFirebase(listaComunicacaoRCRVW.getCaminhoFirebase());

                                comunicacaoDAO.atualizar(listaComunicacaoMontarFrase);
                                alerta.cancel();
                                Intent intent = new Intent(MontarFrase.this, MontarFrase.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "REGISTRO EXCLUIDO COM SUCESSO", Toast.LENGTH_LONG).show();

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

    public void TIRAR_FOTO_ou_GALERIA() {
        //alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MontarFrase.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogCameraGaleriaview = inflater.inflate(R.layout.dialog_camera_galeria, null);
        builder.setView(dialogCameraGaleriaview);

        ImageButton camera = dialogCameraGaleriaview.findViewById(R.id.imgbtn_alerDialcamera);
        ImageButton galeria = dialogCameraGaleriaview.findViewById(R.id.imgbtn_alerDialgaleria);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(imageIntent, CAMERA);
                alerta.cancel();
            }

        });
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPegaFoto = new Intent(Intent.ACTION_PICK);
                intentPegaFoto.setType("image/*");
                startActivityForResult(intentPegaFoto, GALERIA);
                alerta.cancel();
            }
        });
        alerta = builder.create();
        alerta.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        comunicacaoDAO = new ComunicacaoDAO(getApplicationContext());

        if (requestCode == CAMERA && resultCode == RESULT_OK) {//tirar foto
            Bundle extra = data.getExtras();
            final Bitmap imagem = (Bitmap) extra.get("data");

            //alertDialog
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

                    if (textoFalar.getText().length() < 2) {
                        textoFalar.setError("Informe o Texto por favor com no minimo 2 letras");
                        textoFalar.setFocusable(true);

                    } else {

                        //Savar dados da imagem no BD
                        if (imagem != null) {
                            String nomeDoArquivo = "AZ-" + dataFormatada + ".JPG";
                            listaComunicacaoMontarFrase.setTextoFalar_MontarFrase(textoFalar.getText().toString());
                            listaComunicacaoMontarFrase.setTextoFalar(null);
                            listaComunicacaoMontarFrase.setCaminhoFirebase(splash_activity.meuDirSentimentos + "/" + nomeDoArquivo);
                            listaComunicacaoMontarFrase.setTipoComunic("montar frase");
                            listaComunicacaoMontarFrase.setExcluido("n");

                            comunicacaoDAO.salvar(listaComunicacaoMontarFrase);

                            salvar_foto.SALVAR_IMAGEM_DIRECTORIO(imagem, nomeDoArquivo, splash_activity.meuDirMontarFreses.getAbsolutePath());
                            alerta.cancel();
                            CARREGAR_FOTOS_SENTIMENTOS();
                        } else {
                            Toast.makeText(MontarFrase.this, "Erro ao salvar imagem, refaça a operação por favor!", Toast.LENGTH_SHORT).show();
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


        if (resultCode == RESULT_OK && requestCode == GALERIA) { //foto da galeria
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
            final File rootPath = new File(splash_activity.meuDirMontarFreses.getAbsolutePath());
            if (!rootPath.exists()) {
                rootPath.mkdirs();
            }

            // Criamos um file, com o DIRETORIO, com o mesmo nome novo
            final File novaImagem = new File(rootPath, "AZ-" + dataFormatada + ".JPG");

            //alertDialog
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

                    if (textoFalar.getText().length() < 2) {
                        textoFalar.setError("Informe o Texto por favor com no minimo 2 letras");
                        textoFalar.setFocusable(true);

                    } else {
                        //Savar dados da imagem no BD
                        if (imagem != null) {
                            String nomeDoArquivo = "AZ-" + dataFormatada + ".JPG";
                            listaComunicacaoMontarFrase.setTextoFalar_MontarFrase(textoFalar.getText().toString());
                            listaComunicacaoMontarFrase.setTextoFalar(null);
                            listaComunicacaoMontarFrase.setCaminhoFirebase(splash_activity.meuDirMontarFreses + "/" + nomeDoArquivo);
                            listaComunicacaoMontarFrase.setTipoComunic("montar frase");
                            listaComunicacaoMontarFrase.setExcluido("n");

                            comunicacaoDAO.salvar(listaComunicacaoMontarFrase);

                            try {//Movemos o arquivo!
                                salvar_foto.COPIAR_ARQUIVO(selecionada, novaImagem, getApplicationContext());
                                Toast.makeText(MontarFrase.this, "Imagem movida com sucesso!", Toast.LENGTH_SHORT).show();
                                alerta.cancel();
                                CARREGAR_FOTOS_SENTIMENTOS();
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

    public void REPRODUZIR_SOM(String testoFalar) {
        Log.e("TTS", "Clicou no Botao: " + testoFalar);
        int speechStatus = textToSpeech.speak(testoFalar, TextToSpeech.QUEUE_FLUSH, null);

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Erro ao Converter Texto em Fala!");
        }
    }

    //metodo para parar a reprodução do audio
    public void PARAR_REPRODUZIR_SOM() {
        if (textToSpeech != null) {
            if (textToSpeech.isSpeaking()) {
                textToSpeech.stop();
            }
        }
    }


}

