package com.sandeept.imageupload;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> requestPermissionLauncher;

    private ActivityResultLauncher<Uri> takePictureLauncher;
    private Uri photoUri;
    private BitmapViewModel bitmapViewModel;

    /*
     * Reference - https://developer.android.com/reference/androidx/activity/result/contract/ActivityResultContracts
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bitmapViewModel = new ViewModelProvider(this).get(BitmapViewModel.class);

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted->{

            if(isGranted){
                captureImage();
            }
            else{
                Toast.makeText(this, "Camera persmission required!", Toast.LENGTH_SHORT).show();
            }
        });

        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {

            if(result){

                Intent uploadIntent = new Intent(getApplicationContext(), UploadActivity.class);

                uploadIntent.putExtra("Uri", photoUri.toString());
                startActivity(uploadIntent);
            }
        });
    }

    /*
    * Reference - https://developer.android.com/training/permissions/requesting
    * */

    public void onTakePhoto(View view){

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){

            Toast.makeText(this, "No Camera detected!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            captureImage();
        }

        else{
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void captureImage(){

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        if(photoUri == null){

            Toast.makeText(this, "Error capturing photo", Toast.LENGTH_SHORT).show();
            return;
        }

        takePictureLauncher.launch(photoUri);
    }
}