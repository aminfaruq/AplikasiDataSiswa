package id.co.maminfaruq.datasiswa.ui.login;

import android.content.Context;
import android.util.Log;

import id.co.maminfaruq.datasiswa.model.login.LoginData;
import id.co.maminfaruq.datasiswa.model.login.LoginResponse;
import id.co.maminfaruq.datasiswa.network.ApiClient;
import id.co.maminfaruq.datasiswa.network.ApiInterface;
import id.co.maminfaruq.datasiswa.utils.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private SessionManager mSessionManager;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void doLogin(String username, String password) {
        //cek username & password
        if (username.isEmpty()) {
            view.usernameError("Username is empty");
            return;
        }

        if (password.isEmpty()) {
            view.passwordError("Password is empty");
            return;
        }

        view.showProgress();

        Call<LoginResponse> call = apiInterface.loginUser(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getResult() == 1) {
                        if (response.body().getData() != null && response.isSuccessful()) {
                            LoginData loginData = response.body().getData();
                            String message = response.body().getMessage();
                            view.loginSuccess(message, loginData);
                        } else {
                            view.loginFailure("Data is empty");
                        }
                    } else {
                        view.loginFailure(response.body().getMessage());
                    }
                } else {
                    view.loginFailure("Data is empty");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.loginFailure(t.getMessage());
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void saveDataUser(Context context, LoginData loginData) {
        // Membuat object SessionManager
        mSessionManager = new SessionManager(context);
        // Mensave data ke SharedPreference dengan menggunakan method dari class SessionManager
        mSessionManager.createSession(loginData);
    }

    @Override
    public void checkLogin(Context context) {
        // Membuat object SessionManager
        mSessionManager = new SessionManager(context);
        // Mengambil data KEY_IS_LOGIN lalu memasukkan ke dalam variable isLogin
        Boolean isLogin = mSessionManager.isLogin();
        // Mengecek apakah KEY_IS_LOGIN bernilai true
        if (isLogin){
            // Menyuruh view untuk melakukan perpindahan ke MainAcivity
            view.isLogin();
        }
    }
}
