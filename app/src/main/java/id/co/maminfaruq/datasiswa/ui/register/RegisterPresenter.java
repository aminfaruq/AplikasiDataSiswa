package id.co.maminfaruq.datasiswa.ui.register;


import android.util.Log;

import id.co.maminfaruq.datasiswa.model.login.LoginData;
import id.co.maminfaruq.datasiswa.model.login.LoginResponse;
import id.co.maminfaruq.datasiswa.network.ApiClient;
import id.co.maminfaruq.datasiswa.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void doRegisterUser(LoginData loginData) {
        if (loginData != null) {
            if (!loginData.getUsername().isEmpty() &&
                    !loginData.getAlamat().isEmpty() &&
                    !loginData.getJenkel().isEmpty() &&
                    !loginData.getLevel().isEmpty() &&
                    !loginData.getNoTelp().isEmpty() &&
                    !loginData.getPassword().isEmpty() &&
                    !loginData.getNamaUser().isEmpty()
                    ){

                view.showProgress();
                Call<LoginResponse> call = apiInterface.registerUser(
                        loginData.getUsername(),
                        loginData.getPassword(),
                        loginData.getNamaUser(),
                        loginData.getAlamat(),
                        loginData.getJenkel(),
                        loginData.getNoTelp(),
                        loginData.getLevel()
                );

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        view.hideProgress();
                        if (response.body() != null && response.isSuccessful()){
                            if (response.body().getResult() == 1){
                                view.showRegisterSuccess(response.body().getMessage());
                            }else {
                                view.showError(response.body().getMessage());
                                Log.e("gagal",response.body().getMessage());
                            }
                        }else {
                            view.showError("Data isEmpty");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        view.hideProgress();
                        view.showError(t.getMessage());
                        Log.e("failure",t.getMessage());
                    }
                });
            }
        }else {
            view.showError("There must be nothing empty");
        }
    }
}
