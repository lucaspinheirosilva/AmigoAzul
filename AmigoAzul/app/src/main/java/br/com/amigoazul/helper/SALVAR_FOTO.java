package br.com.amigoazul.helper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;

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
}

