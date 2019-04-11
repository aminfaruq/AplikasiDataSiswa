package id.co.maminfaruq.datasiswa.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import id.co.maminfaruq.datasiswa.model.login.LoginData;
import id.co.maminfaruq.datasiswa.ui.login.LoginView;

public class SessionManager {

    //membuat variable global untuk sharedpreff
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private final Context context;

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;

        //object
        pref = context.getSharedPreferences(Constants.pref_name,0);

        //create edit mode
        editor = pref.edit();
    }

    public void createSession(LoginData loginData){
        //memasukkan data user yang sdh login ke dalam shared pref
        editor.putBoolean(Constants.KEY_IS_LOGIN,true);
        editor.putString(Constants.KEY_USER_ID, loginData.getId_user());
        editor.putString(Constants.KEY_USER_NAMA, loginData.getNamaUser());
        editor.putString(Constants.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constants.KEY_USER_JENKEL, loginData.getJenkel());
        editor.putString(Constants.KEY_USER_NOTELP, loginData.getNoTelp());
        editor.putString(Constants.KEY_USER_USERNAME, loginData.getUsername());
        editor.putString(Constants.KEY_USER_LEVEL, loginData.getLevel());
        // Mengeksekusi penyimpanan
        editor.commit();
        /*editor.apply();*/
    }

    //Function untuk cek apakah user sudah pernah login
    public boolean isLogin(){
        //mengembalikan nilai bolean dan mengambil data dari shared pref
        return pref.getBoolean(Constants.KEY_IS_LOGIN,false);
    }

    //Function untuk melakukan logout atau menghapus isi dalam shared pref
    public void logout(){
        //mengambil method clear untuk menghapus data shred pref
        editor.clear();
        //mengeksekusi perintah clear
        editor.commit();

        //membuat intent untuk berpindah halaman
        Intent intent = new Intent(context, LoginView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
