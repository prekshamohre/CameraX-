package com.example.camerax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView img_cam;

    
    private int REQUEST_CODE_PERMISSION = 101;
    private final String[] REQUIRED_PERMISSION = new String[]{
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        img_cam = (ImageView) findViewById(R.id.img_cam);

        if(allPermissionGranted())
        {

        }
        else
        {
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSION,REQUEST_CODE_PERMISSION);
        }

        img_cam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });
    }




    private boolean allPermissionGranted()
    {
        for(String permission: REQUIRED_PERMISSION)
        {
            if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
        }
        return true;

     }
}
