package com.cedro.memoriesoftravel.presenter;

import android.app.Activity;
import android.graphics.Bitmap;

import com.cedro.memoriesoftravel.model.ImageModel;
import com.cedro.memoriesoftravel.util.ImageLoader;

/**
 * Created by emerson on 06/10/16.
 */

public class ImagePreseter implements Runnable {
    ImageModel imageModel;
    ImageLoader view;

    public ImagePreseter(ImageLoader view, ImageModel imgParaCarregar) {
        this.view = view;
        this.imageModel = imgParaCarregar;
    }

    public void run() {
        // se o imageView ja estiver usado retorna sem fazer nada
        if(view.imageViewReusado(imageModel))
            return;

        //carrega o bitmap do cache ~ api
        Bitmap bmp=view.getBitmap(imageModel.getUrl());

        //salva em cache
        view.memoryCache.put(imageModel.getUrl(), bmp);

        // se o imageView ja estiver usado retorna sem fazer nada
        if(view.imageViewReusado(imageModel))
            return;

        //cria thread para exibir a imagem
        BitmapDisplayer bd=new BitmapDisplayer(bmp, imageModel);
        Activity a=(Activity) imageModel.getImageView().getContext();
        a.runOnUiThread(bd);
    }

    public class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        ImageModel imageModel;

        public BitmapDisplayer(Bitmap b, ImageModel p){
            bitmap=b;
            imageModel =p;
        }

        public void run()
        {
            if(view.imageViewReusado(imageModel))
                return;
            if(bitmap!=null)
            {
                imageModel.getImageView().setImageBitmap(bitmap);
            }
        }
    }
}
