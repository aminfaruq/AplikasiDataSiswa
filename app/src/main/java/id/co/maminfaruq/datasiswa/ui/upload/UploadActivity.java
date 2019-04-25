package id.co.maminfaruq.datasiswa.ui.upload;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.maminfaruq.datasiswa.R;
import id.co.maminfaruq.datasiswa.model.login.kelas.KelasData;
import id.co.maminfaruq.datasiswa.utils.Constants;

public class UploadActivity extends AppCompatActivity implements UploadContract.View {

    @BindView(R.id.img_picture)
    ImageView imgPicture;
    @BindView(R.id.fab_choose_picture)
    FloatingActionButton fabChoosePicture;
    @BindView(R.id.layoutPicture)
    CardView layoutPicture;
    @BindView(R.id.edt_name_kelas)
    EditText edtNameKelas;
    @BindView(R.id.edt_jurusan)
    EditText edtJurusan;
    @BindView(R.id.edt_nama_wali)
    EditText edtNamaWali;
    @BindView(R.id.spin_category)
    Spinner spinCategory;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.layoutUploadMakanan)
    CardView layoutUploadMakanan;

    private ProgressDialog progressDialog;

    private UploadPresenter mUploadPresenter = new UploadPresenter(this);

    private String mIdCategory;
    // Untuk menampung gambar
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        mUploadPresenter.getCategory();

        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                hideProgress();
            }
        });


        PermissionGalery();
    }

    private void PermissionGalery() {
        // Mencek apakah user sudah memberikan permission untuk mengakses external storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                showMessage("Permission granted now you can read the storage");
                Log.i("Permission on", "onRequestPermissionsResult: " + String.valueOf(grantResults));
            } else {
                //Displaying another toast if permission is not granted
                showMessage("Oops you just denied the permission");
                Log.i("Permission off", "onRequestPermissionsResult: " + String.valueOf(grantResults));
            }
        }
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void successUpload() {
        finish();

    }

    @Override
    public void shoSpinnerCategory(final List<KelasData> categoryKelasDataList) {
        List<String> listSpinner = new ArrayList<>();

        for (int i = 0; i < categoryKelasDataList.size(); i++) {
            listSpinner.add(categoryKelasDataList.get(i).getNamaKategori());
        }

        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpinner);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinCategory.setAdapter(categorySpinnerAdapter);

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mIdCategory = categoryKelasDataList.get(position).getIdKategori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @OnClick({R.id.fab_choose_picture, R.id.btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                showFileChooser();
                break;
            case R.id.btn_upload:
                mUploadPresenter.uploadMakanan(this,
                        filePath,
                        edtNameKelas.getText().toString(),
                        edtJurusan.getText().toString(),
                        edtNamaWali.getText().toString(),
                        mIdCategory);
                break;
        }
    }

    private void showFileChooser() {
        // Membuka media external storage
        Intent intentGalery = new Intent(Intent.ACTION_PICK);
        intentGalery.setType("image/*");
        intentGalery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGalery, "Select pictures"), Constants.REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Mencek apakah request code dan data ada
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            // Mengambil data image yang sudah dipilih user
            filePath = data.getData();

            try{
                // Mengubah file image pilihan user menjadi bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // Menmapilkan gambar preview yang di pilih
                imgPicture.setImageBitmap(bitmap);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
