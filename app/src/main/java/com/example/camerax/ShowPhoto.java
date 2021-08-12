package com.example.camerax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

public class ShowPhoto extends AppCompatActivity {
    ImageView imageview;
    ImageButton share,crop;
    String path = "";
    public static String CAMERA_POSITION_FRONT;
    public static String CAMERA_POSITION_BACK;
    public static String MAX_ASPECT_RATIO;

    //widgets

    //vars
    private boolean mPermissions;
    public String mCameraOrientation = "none";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        share = (ImageButton) findViewById(R.id.share);




        imageview = (ImageView) findViewById(R.id.imageview);
        path = getIntent().getExtras().getString("path");
        File imgfile = new File(path);

        if (imgfile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
            imageview.setImageBitmap(RotateBitmap(myBitmap, 90));
        }



        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image();
            }
        });
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public void captureVideo(View view){


    }

    public void image() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        BitmapDrawable drawable = (BitmapDrawable) imageview.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        File f = new File(getExternalCacheDir() + "/" + getResources().getString(R.string.app_name) + ".jpeg");
        Intent shareint;



        try {
            FileOutputStream outputStream=new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            shareint = new Intent(Intent.ACTION_SEND);
            shareint.setType("image/");
            shareint.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            shareint.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        startActivity(Intent.createChooser(shareint,"share image"));
    }


}