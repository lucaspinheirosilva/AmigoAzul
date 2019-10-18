package br.com.amigoazul.helper;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import br.com.amigoazul.activity.Splash_Activity;

/**
 * Criado por Lucas Pinheiro on 13/10/2019.
 */

public class SALVAR_FOTO {
    //*****SALVAR IMAGEM NO DIRETORIO
    //https://stackoverflow.com/questions/54005582/camera-bitmap-imagebitmap-bitmap-extras-getdata-gives-nullpointer-err
    public void SALVAR_IMAGEM_DIRECTORIO(Bitmap bitmap, String nomeArquivo,String diretorio) {
        File direct = new File(diretorio);

        if (!direct.exists()) {
            File profilePic = new File(diretorio);
            profilePic.mkdirs();
        }

        File file = new File(String.valueOf(new File(diretorio, nomeArquivo)));
        if (file.exists()) {
          //  file.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //*****copiar da galeria para Amigo azul
    //Copia a imagem e remove o destino
    /*https://pt.stackoverflow.com/questions/187457/pegar-uma-imagem-da-galeria-e-mover-para-uma-pasta*/
    public void COPIAR_ARQUIVO(File sourceFile, File destFile,Context context) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
        /*//Alertamos, caso não consiga remover
        if (!sourceFile.delete()) {
            Toast.makeText(context, "Não foi possível remover a imagem!", Toast.LENGTH_SHORT).show();
        }*/
    }
    // Transforma o Uri em um diretório válido, para carregarmos em um arquivo
    public String getRealPathFromURI(Uri contentUri,Context context) {
        final Dialog dialog = new Dialog(context);
        String[] proj = {MediaStore.Video.Media.DATA};
        Cursor cursor = dialog.getContext().getContentResolver().query(contentUri, proj, null, null, null);
        assert cursor != null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    }


