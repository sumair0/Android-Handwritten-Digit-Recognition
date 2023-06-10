package com.sandeept.imageupload;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BitmapViewModel extends ViewModel {

    private MutableLiveData<Bitmap> bitmap = new MutableLiveData<>();

    public Bitmap getBitmap() {
        return bitmap.getValue();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap.setValue(bitmap);
    }
}
