package com.example.cloudnarydemo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CloudinaryApi {

    @Multipart
    @POST("image/upload")
    Call<CloudinaryResponse> uploadImage (
            @Part MultipartBody.Part image,
            @Part("upload_preset") RequestBody uploadPreset
    );

}
