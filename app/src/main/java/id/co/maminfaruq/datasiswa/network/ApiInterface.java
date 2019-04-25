package id.co.maminfaruq.datasiswa.network;

import id.co.maminfaruq.datasiswa.model.login.LoginResponse;
import id.co.maminfaruq.datasiswa.model.login.kelas.KelasResponse;
import id.co.maminfaruq.datasiswa.model.login.upload.UploadKelasResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    //create endpoin login
    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password
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

    @GET("getkategori.php")
    Call<KelasResponse> getKategoriKelas();

    @GET("getdatakelas.php")
    Call<KelasResponse> getKelasBaru();

    @GET("getkelaspopuler.php")
    Call<KelasResponse> getKelasPopuler();

    @Multipart
    @POST("uploadkelas.php")
    Call<UploadKelasResponse> uploadKelas(
            @Part("id_user") int idUser,
            @Part("id_kategori") int idKategory,
            @Part("nama_kelas") RequestBody namaKelas,
            @Part("nama_jurusan") RequestBody namaJurusan,
            @Part("nama_wali") RequestBody namaWali,
            @Part("insert_time") RequestBody insertTime,
            @Part MultipartBody.Part image
            );


}
