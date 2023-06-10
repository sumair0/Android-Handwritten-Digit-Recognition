package com.sandeept.imageupload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {

    static Bitmap getBitmapFromUri(Uri uri, Context context){

        Bitmap bitmap;

        try{

            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            if(inputStream == null){
                return null;
            }

            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch(IOException fne){

            return null;
        }

        return bitmap;
    }

    static byte[] getBytesFromBitmap(Bitmap bitmap){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
