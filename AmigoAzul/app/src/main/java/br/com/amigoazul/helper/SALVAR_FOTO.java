package br.com.amigoazul.helper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;

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
    /**
     * SALVAR IMAGEM NO DIRETORIO
     */
    //https://stackoverflow.com/questions/54005582/camera-bitmap-imagebitmap-bitmap-extras-getdata-gives-nullpointer-err
    public void SALVAR_IMAGEM_DIRECTORIO(Bitmap bitmap, String nomeArquivo,String diretorio) {
        File direct = new File(diretorio);

        if (!direct.exists()) {
            File profilePic = new File(diretorio);
            profilePic.mkdirs();
        }

        File file = new File(String.valueOf(new File(diretorio, nomeArquivo)));
        if (file.exists()) {
            file.delete();
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

    //copiar da galeria para Amigo azul
    public void COPIAR_GALERIA(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }
    }


