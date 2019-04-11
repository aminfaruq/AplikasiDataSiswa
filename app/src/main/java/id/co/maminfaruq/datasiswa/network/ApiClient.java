package id.co.maminfaruq.datasiswa.network;

import id.co.maminfaruq.datasiswa.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit getClient(){

        //membuat object logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        // set log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // membuat object httpClient
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // menambahkan logging interceptor ke dalam httpClient
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

}
