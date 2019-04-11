package id.co.maminfaruq.datasiswa.ui.register;

import id.co.maminfaruq.datasiswa.model.login.LoginData;

public interface RegisterContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showError(String message);
        void showRegisterSuccess(String message);
    }

    interface Presenter{
        void doRegisterUser(LoginData loginData);
    }
}
