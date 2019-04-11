package id.co.maminfaruq.datasiswa.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import id.co.maminfaruq.datasiswa.MainActivity;
import id.co.maminfaruq.datasiswa.R;
import id.co.maminfaruq.datasiswa.model.login.LoginData;
import id.co.maminfaruq.datasiswa.ui.register.RegisterView;
import id.co.maminfaruq.datasiswa.utils.Constants;
import io.rmiri.buttonloading.ButtonLoading;

public class LoginView extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.welcome_)
    TextView welcome;
    @BindView(R.id.sign_in_to_)
    TextView signInTo;
    @BindView(R.id.edtusername)
    EditText edtusername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.button)
    ButtonLoading button;
    @BindView(R.id.tv_create)
    TextView tvCreate;

    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter = new LoginPresenter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        ButterKnife.bind(this);
        loginPresenter.checkLogin(this);

        buttonLoading();
    }

    private void buttonLoading() {
        button.setOnButtonLoadingListener(new ButtonLoading.OnButtonLoadingListener() {
            @Override
            public void onClick() {
                loginPresenter.doLogin(edtusername.getText().toString(),edtPassword.getText().toString());
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading . . .");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.ic_play_button);
        progressDialog.show();

    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void loginSuccess(String message, LoginData loginData) {
        Toasty.success(this, message, Toast.LENGTH_SHORT, true).show();

        // Menyimpan data user ke dalam SharedPreference
        loginPresenter.saveDataUser(this, loginData);

        LoginData mLoginData = new LoginData();
        mLoginData.setId_user(loginData.getId_user());
        mLoginData.setNamaUser(loginData.getNamaUser());
        mLoginData.setAlamat(loginData.getAlamat());
        mLoginData.setJenkel(loginData.getJenkel());
        mLoginData.setNoTelp(loginData.getNoTelp());
        mLoginData.setUsername(loginData.getUsername());
        mLoginData.setPassword(loginData.getPassword());
        mLoginData.setLevel(loginData.getLevel());

        startActivity(new Intent(this, MainActivity.class).putExtra(Constants.KEY_LOGIN, mLoginData));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public void loginFailure(String message) {
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void usernameError(String message) {
        edtusername.setError(message);
        edtusername.setFocusable(true);
    }

    @Override
    public void passwordError(String message) {
        edtPassword.setError(message);
        edtPassword.setFocusable(true);
    }

    @Override
    public void isLogin() {
        // Berpindah halaman apabila user sudah login
        startActivity(new Intent(this, MainActivity.class));
        // Menutup loginActivity
        finish();
    }

    @OnClick(R.id.tv_create)
    public void onViewClicked() {
        startActivity(new Intent(this, RegisterView.class));
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

}
