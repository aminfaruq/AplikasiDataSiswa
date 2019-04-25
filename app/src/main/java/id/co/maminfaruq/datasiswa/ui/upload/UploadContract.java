package id.co.maminfaruq.datasiswa.ui.upload;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import id.co.maminfaruq.datasiswa.model.login.kelas.KelasData;

public interface UploadContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showMessage(String msg);
        void successUpload();
        void shoSpinnerCategory(List<KelasData> categoryKelasDataList);

    }

    interface Presenter{

        void getCategory();
        void uploadMakanan(Context context, Uri filePath, String namaKelas, String namaJurusan, String namaWali , String idCategory);

    }
}
