package com.example.cloudnarydemo;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {

    private static Retrofit retrofit ;
    private static Retrofit getClient(String cloudName){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(
                            "https://api.cloudinary.com/v1_1/"+
                               cloudName +
                               "/"
                    )
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    ).build();
        }
        return retrofit;
    }
    public static CloudinaryApi getApi(String cloudName) {
        return getClient(cloudName)
                .create(CloudinaryApi.class);
    }

}
