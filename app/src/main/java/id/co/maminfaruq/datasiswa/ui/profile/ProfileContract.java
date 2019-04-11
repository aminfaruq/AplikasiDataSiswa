package id.co.maminfaruq.datasiswa.ui.profile;

import android.content.Context;

import id.co.maminfaruq.datasiswa.model.login.LoginData;

public interface ProfileContract {
    interface View {
        void showProgress();

        void hideProgress();

        void showSuccessUpdateUser(String message);

        void showDataUser(LoginData loginData);
    }

    interface Presenter {
        void updateDataUser(Context context, LoginData loginData);

        void getDataUser(Context context);

        void logoutSession(Context context);
    }
}
