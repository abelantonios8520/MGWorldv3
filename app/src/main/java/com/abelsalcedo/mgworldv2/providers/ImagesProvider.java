package com.abelsalcedo.mgworldv2.providers;

import android.content.Context;

import com.abelsalcedo.mgworldv2.utils.CompressorBitmapImage;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class ImagesProvider {

    private StorageReference mStorage;

    public ImagesProvider(String ref){
        mStorage = FirebaseStorage.getInstance().getReference().child(ref);
    }

    public UploadTask saveImage(Context context, File image, String idUser){
        byte[] imageByte = CompressorBitmapImage.getImage(context, image.getPath(), 500, 500);
        StorageReference storage = mStorage.child(idUser + ".jpg");
        mStorage = storage;
        UploadTask uploadTask = storage.putBytes(imageByte);
        return uploadTask;
    }

    public StorageReference getStorage(){
        return mStorage;
    }
}
