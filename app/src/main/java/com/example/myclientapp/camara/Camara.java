package com.example.myclientapp.camara;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class Camara extends AppCompatActivity {
    protected String path, name;
    ActivityResultLauncher<Intent> cameraLauncher;
    ImageView imageView;
    File photo;

    // Constructor vacio
    public Camara(){

    }

    //metodo para capturar la fotografia

    public void takePicture(){
        String ROOT_FOLDER = "myClientTrack/";
        String IMAGE_ROOT = ROOT_FOLDER + "images";
        File fileImg = new File(Environment.getExternalStorageDirectory(), IMAGE_ROOT);
        boolean isTaken = fileImg.exists();

        if(!isTaken){
            isTaken=fileImg.mkdirs();
        }
        if(isTaken){
            name= (System.currentTimeMillis()/1000)+".jpg";
        }
        path=Environment.getExternalStorageDirectory()+ File.separator+ IMAGE_ROOT +File.separator+name;

        photo = new File(path);

        // hago la llamada a la CAMARA

        /**

        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(takePicIntent, 1);    //********* DEPRECATE********

         **/

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>(){
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()==RESULT_OK){
                    assert result.getData() != null;
                    Bundle extras = result.getData().getExtras();
                    assert extras != null;
                    Bitmap imgBitmap = (Bitmap) extras.get("data");
                    // imgView.setImageBitmap(imgBitmap);
                    Uri uriPath = (Uri) extras.get("uriData");
                    imageView.setImageURI(uriPath);
                }

            }
        });
    }

    public void setPicture(){
        cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        Intent intent= new Intent();
        intent.putExtra("uriData", Uri.fromFile(photo));
    }

    /**
   public void setPic(){
        @Override
       public void onActivityResult(int requestCode, int resultCode, Intent data){
           super.onActivityResult(requestCode, resultCode, data);
           if(requestCode==RESULT_OK){
               Uri path=data.getData();
               imageView.setImageURI(path);
           }
       }
   }
*/


}
