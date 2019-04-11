package id.co.maminfaruq.datasiswa.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;

import id.co.maminfaruq.datasiswa.model.login.LoginData;
import id.co.maminfaruq.datasiswa.model.login.LoginResponse;
import id.co.maminfaruq.datasiswa.network.ApiClient;
import id.co.maminfaruq.datasiswa.network.ApiInterface;
import id.co.maminfaruq.datasiswa.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View view;
    private SharedPreferences pref;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void updateDataUser(final Context context, final LoginData loginData) {
        view.showProgress();

        //Membuat object call
        Call<LoginResponse> call = apiInterface.updateUser(Integer.valueOf(loginData.getId_user()),
                loginData.getNamaUser(),
                loginData.getAlamat(),
                loginData.getJenkel(),
                loginData.getNoTelp()
        );

        //request server
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.isSuccessful()){
                    if (response.body().getResult() == 1){
                        //membuat object shared prefecence yang sdh ada
                        pref = context.getSharedPreferences(Constants.pref_name, 0);
                        //mengubah mode menjadi edit
                        SharedPreferences.Editor editor = pref.edit();
                        //memasukkan data ke dalam sharedpref
                        editor.putString(Constants.KEY_USER_NAMA, loginData.getNamaUser());
                        editor.putString(Constants.KEY_USER_ALAMAT, loginData.getAlamat());
                        editor.putString(Constants.KEY_USER_NOTELP, loginData.getNoTelp());
                        editor.putString(Constants.KEY_USER_JENKEL, loginData.getJenkel());
                        //Apply perubahan
                        editor.apply();
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }else {
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.showSuccessUpdateUser(t.getMessage());
            }
        });
    }

    @Override
    public void getDataUser(Context context) {
        //pengambilan data
        pref = context.getSharedPreferences(Constants.pref_name, 0);

        //membuat object model login data
        LoginData loginData = new LoginData();

        loginData.setId_user(pref.getString(Constants.KEY_USER_ID, ""));
        loginData.setNamaUser(pref.getString(Constants.KEY_USER_NAMA, ""));
        loginData.setAlamat(pref.getString(Constants.KEY_USER_ALAMAT, ""));
        loginData.setNoTelp(pref.getString(Constants.KEY_USER_NOTELP, ""));
        loginData.setJenkel(pref.getString(Constants.KEY_USER_JENKEL, ""));

        view.showDataUser(loginData);

    }

    @Override
    public void logoutSession(Context context) {

    }
}
