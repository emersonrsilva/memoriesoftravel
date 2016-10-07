package com.cedro.memoriesoftravel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.cedro.memoriesoftravel.model.ImageModel;
import com.cedro.memoriesoftravel.preseter.ImagePreseter;
import com.memoriesoftravel.R;


public class ImageLoader {
    
    public MemoryCache memoryCache=new MemoryCache();

    FileCache fileCache;

    private Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    ExecutorService executorService;

    final int logo_se_nao_existir_outra = R.drawable.ic_launcher;

    public ImageLoader(Context context){
        fileCache=new FileCache(context);
        executorService=Executors.newFixedThreadPool(5);
    }
    

    public void exibirImagem(String url, ImageView imageView)
    {
        imageViews.put(imageView, url);

        //primeiro busca do cache na memoria
        Bitmap bitmap=memoryCache.get(url);

        if(bitmap!=null)
            imageView.setImageBitmap(bitmap);

        //se nao existir o cache em memoria, ele cria uma fila para carregar
        else
        {
           enfileirarImagem(url, imageView);
            //define uma imagem temporaria(logo do app)
           imageView.setImageResource(logo_se_nao_existir_outra);
        }
    }
        
    private void enfileirarImagem(String url, ImageView imageView)
    {
        ImageModel p=new ImageModel(url, imageView);
        executorService.submit(new ImagePreseter(this,p));
    }
    
    public Bitmap getBitmap(String url)
    {
        File f=fileCache.getFile(url);
        
        //do cache
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;
        
        //pela web se não exibir cache
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex){
           ex.printStackTrace();
           return null;
        }
    }

    //decodifica imagem para reduzir a escala
    private Bitmap decodeFile(File f){
        try {

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
            
            final int TAMANHO_DESEJADO=150;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int escala=1;
            while(true){
                if(width_tmp/2<TAMANHO_DESEJADO || height_tmp/2<TAMANHO_DESEJADO)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                escala*=2;
            }
            
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=escala;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
    

    public boolean imageViewReusado(ImageModel imgParaCarregar){
        String tag=imageViews.get(imgParaCarregar.getImageView());
        if(tag==null || !tag.equals(imgParaCarregar.getUrl()))
            return true;
        return false;
    }
    

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

}
