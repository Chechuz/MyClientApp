package com.example.myclientapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.myclientapp.camara.Imagenes;

import java.util.List;

public class AdapterGaleria extends BaseAdapter {
    private Context context;
    List<Imagenes> imageList;

    public AdapterGaleria(Context context, List<Imagenes> imageList){
        this.context=context;
        this.imageList=imageList;
    }
    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageURI(Uri.parse(imageList.get(position).getImageUri())); // como el getImgURI es un string lo parseo a URI con URI.parse
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(340, 350));
        return imageView;
    }
}
