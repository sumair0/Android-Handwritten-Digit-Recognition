package com.sandeept.imageupload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    BitmapViewModel bitmapViewModel;

    Button uploadButton;
    TextView uploadStatusText;

    EditText ipField, portField;

    /*
     * Reference - https://developer.android.com/develop/ui/views/components/spinner
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadButton = findViewById(R.id.upload_button);
        uploadStatusText = findViewById(R.id.upload_status_text);
        ipField = findViewById(R.id.ip_field);
        portField = findViewById(R.id.port_field);

        //Spinner categorySpinner = findViewById(R.id.category_spinner);

//        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
//                R.array.category_array, android.R.layout.simple_spinner_item);
//
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        categorySpinner.setAdapter(arrayAdapter);
//        categorySpinner.setOnItemSelectedListener(this);

        Initialize();
    }

    /*
    * Reference - https://developer.android.com/topic/libraries/architecture/viewmodel
    * */
    private void Initialize(){

        bitmapViewModel = new ViewModelProvider(this).get(BitmapViewModel.class);

        ImageView imageView = findViewById(R.id.image_view);

        if(bitmapViewModel.getBitmap() == null){

            Uri uri = Uri.parse(getIntent().getStringExtra("Uri"));
            Bitmap bitmap = BitmapUtils.getBitmapFromUri(uri, this);

            bitmapViewModel.setBitmap(bitmap);
            imageView.setImageBitmap(bitmap);
        }
        else{

            imageView.setImageBitmap(bitmapViewModel.getBitmap());
        }
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//        category = parent.getItemAtPosition(position).toString();
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }

    private String getUploadURL(){

        return ipField.getText().toString();
    }

    public void onUpload(View view){

        uploadButton.setEnabled(false);
        uploadStatusText.setText(getResources().getString(R.string.uploading));

        new Thread(() -> {

            try {

                int responseCode = NetworkUtils.uploadBitmap("http", getUploadURL(), Integer.parseInt(portField.getText().toString()),
                        "upload", BitmapUtils.getBytesFromBitmap(bitmapViewModel.getBitmap()));

                if(responseCode == 204){

                    runOnUiThread(() -> {
                        uploadButton.setEnabled(true);
                        uploadStatusText.setText(getResources().getString(R.string.upload_complete));
                    });
                }
                else{
                    runOnUiThread(() -> {
                        uploadButton.setEnabled(true);
                        uploadStatusText.setText(getResources().getString(R.string.upload_error));
                    });
                }

            } catch (IOException e) {

                runOnUiThread(() -> {
                    uploadButton.setEnabled(true);
                    uploadStatusText.setText(getResources().getString(R.string.upload_error));
                });
            }
        }).start();
    }
}