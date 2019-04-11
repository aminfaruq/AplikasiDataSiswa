package id.co.maminfaruq.datasiswa.network;

import id.co.maminfaruq.datasiswa.model.login.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    //create endpoin login
    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginResponse>loginUser(
            @Field("username")String username,
            @Field("password")String password
    );

    //create endpoind register
    @FormUrlEncoded
    @POST("registeruser.php")
    Call<LoginResponse> registerUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("nama_user") String namauser,
            @Field("alamat") String alamat,
            @Field("jenkel") String jenkel,
            @Field("no_telp") String notelp,
            @Field("level") String level
    );

    //update user
    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUser(
            @Field("id_user") int iduser,
            @Field("nama_user") String namauser,
            @Field("alamat") String alamat,
            @Field("jenkel") String jenkel,
            @Field("no_telp") String notelp
    );
}
