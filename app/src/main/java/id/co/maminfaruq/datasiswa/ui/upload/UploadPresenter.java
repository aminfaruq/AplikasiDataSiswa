package id.co.maminfaruq.datasiswa.ui.upload;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.co.maminfaruq.datasiswa.model.login.kelas.KelasResponse;
import id.co.maminfaruq.datasiswa.model.login.upload.UploadKelasResponse;
import id.co.maminfaruq.datasiswa.network.ApiClient;
import id.co.maminfaruq.datasiswa.network.ApiInterface;
import id.co.maminfaruq.datasiswa.utils.Constants;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPresenter implements UploadContract.Presenter {

    private final UploadContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public UploadPresenter(UploadContract.View view) {
        this.view = view;
    }

    @Override
    public void getCategory() {
        view.showProgress();

        Call<KelasResponse> call = apiInterface.getKategoriKelas();

        call.enqueue(new Callback<KelasResponse>() {
            @Override
            public void onResponse(Call<KelasResponse> call, Response<KelasResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.shoSpinnerCategory(response.body().getData());
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    view.showMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<KelasResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());

            }
        });

    }

    @Override
    public void uploadMakanan(Context context, Uri filePath, String namaKelas, String namaJurusan, String namaWali, String idCategory) {
        view.showProgress();

        if (namaKelas.isEmpty()) {
            view.showMessage(" nama kelas tidak boleh kosong");
            view.hideProgress();
            return;
        }

        if (namaJurusan.isEmpty()) {
            view.showMessage(" nama jursan tidak boleh kosong");
            view.hideProgress();
            return;
        }

        if (namaWali.isEmpty()) {
            view.showMessage(" nama wali tidak boleh kosong ");
            view.hideProgress();
            return;
        }

        if (filePath == null) {
            view.showMessage("Silahkan memilih gambar");
            view.hideProgress();
            return;
        }

        Log.e("isi filepath", "filepath: " + String.valueOf(filePath));

        File myFile = new File(filePath.getPath());
        Log.i("isi myFile", "myFile: " + String.valueOf(myFile));

        Uri selectedImage = getImageContentUri(context, myFile, filePath);
        Log.i("isi selectedimage", "selectedimg: " + String.valueOf(selectedImage));

        File imageFile = null;
        if (selectedImage != null) {
            String partImage = getPath(context, selectedImage);
            Log.i("isi partImage", "partImage: " + imageFile);

            imageFile = new File(partImage);
            Log.i("isi imageFile if", "imgfile: " + String.valueOf(imageFile));
        } else {
            String partImage = getPath(context, filePath);
            imageFile = new File(partImage);
            Log.i("isi imageFile else", "imgfile: " + String.valueOf(imageFile));
        }

        // Mengambil id user di dalam shared pref
        SharedPreferences pref = context.getSharedPreferences(Constants.pref_name, 0);
        String idUser = pref.getString(Constants.KEY_USER_ID, "");

        // Mengambil date sekarang untuk waktu upload makanan
        String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Memasukkan data yang diperlukan ke dalam request body dengan tipe form-data
        // Memasukkan imagefile ke dalam requestbody.part
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

        RequestBody mNamaKelas   = RequestBody.create(MediaType.parse("multipart/form-data"), namaKelas);
        RequestBody mNamaJurusan = RequestBody.create(MediaType.parse("multipart/form-data"), namaJurusan);
        RequestBody mNamaWali    = RequestBody.create(MediaType.parse("multipart/form-data"), namaWali);
        RequestBody mInsertTime = RequestBody.create(MediaType.parse("multipart/form-data"), dateNow);

        Call<UploadKelasResponse> call = apiInterface.uploadKelas(
                Integer.valueOf(idUser),
                Integer.valueOf(idCategory),
                mNamaKelas,
                mNamaJurusan,
                mNamaWali,
                mInsertTime,
                mPartImage
        );

        call.enqueue(new Callback<UploadKelasResponse>() {
            @Override
            public void onResponse(Call<UploadKelasResponse> call, Response<UploadKelasResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showMessage(response.body().getMessage());
                        view.successUpload();
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage(" Data kosong");
                }
            }

            @Override
            public void onFailure(Call<UploadKelasResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
                Log.i("Cek failure", "onFailure: " + t.getMessage());

            }
        });

    }

    private String getPath(Context context, Uri filepath) {
        Cursor cursor = context.getContentResolver().query(filepath, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private Uri getImageContentUri(Context context, File imageFile, Uri filePath) {
        String fileAbsolutePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{fileAbsolutePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Apabila file gambar sudah pernah diapakai namun ada kondisi lain yang belum diketahui
            // Apabila file gambar sudah pernah dipakai pengambilan bukan di galery

            Log.i("Isi Selected if", "Masuk cursor ada");
            return filePath;

        } else {
            Log.i("Isi Selected else", "cursor tidak ada");
            if (imageFile.exists()) {
                // Apabila file gambar baru belum pernah di pakai
                Log.i("Isi Selected else", "imagefile exists");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, fileAbsolutePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                // Apabila file gambar sudah pernah dipakai
                // Apabila file gambar sudah pernah dipakai di galery
                Log.i("Isi Selected else", "imagefile tidak exists");
                return null;
            }
        }
    }

}
