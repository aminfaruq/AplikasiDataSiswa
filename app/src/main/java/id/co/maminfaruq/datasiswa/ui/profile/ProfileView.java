package id.co.maminfaruq.datasiswa.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import id.co.maminfaruq.datasiswa.MainActivity;
import id.co.maminfaruq.datasiswa.R;
import id.co.maminfaruq.datasiswa.model.login.LoginData;
import io.rmiri.buttonloading.ButtonLoading;

@SuppressLint("RestrictedApi")
public class ProfileView extends AppCompatActivity implements ProfileContract.View {

    @BindView(R.id.picture)
    CircleImageView picture;
    @BindView(R.id.fabChoosePic)
    FloatingActionButton fabChoosePic;
    @BindView(R.id.layoutPicture)
    RelativeLayout layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_no_telp)
    EditText edtNoTelp;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.layoutProfil)
    CardView layoutProfil;
    @BindView(R.id.btn_save)
    ButtonLoading btnSave;
    @BindView(R.id.layoutJenkel)
    CardView layoutJenkel;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.toolbar_profile)
    Toolbar toolbarProfile;

    private ProfilePresenter presenter = new ProfilePresenter(this);

    private String idUser, name, alamat, noTelp;
    private int gender;
    private String mGender;
    private Menu action;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        ButterKnife.bind(this);
        setTitleColor(android.R.color.white);


        toolbarProfile.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbarProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileView.this, MainActivity.class));
            }
        });

        presenter.getDataUser(this);
        setSupportActionBar(toolbarProfile);

        //set Spinner
        setupSpinner();
    }

    private void setupSpinner() {
        //membuat adapter
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinGender.setAdapter(genderSpinnerAdapter);

        //listener
        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mengambil posisi item yang dipilih
                String selection = (String) parent.getItemAtPosition(position);

                //cek posisi apakah ada isinya
                if (!TextUtils.isEmpty(selection)) {
                    //cek apakah 1 atau 2 yg dipilih user
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = "L";
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = "P";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving . . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showSuccessUpdateUser(String message) {
        Toasty.success(this, message, Toast.LENGTH_SHORT, true).show();

        // mengambil data ulang
        presenter.getDataUser(this);

    }

    @Override
    public void showDataUser(LoginData loginData) {

        readMode();

        idUser = loginData.getId_user();
        name = loginData.getNamaUser();
        alamat = loginData.getAlamat();
        noTelp = loginData.getNoTelp();
        if (loginData.getJenkel().equals("L")) {
            gender = 1;
        } else {
            gender = 2;
        }

        if (!TextUtils.isEmpty(idUser)) {
            Log.e("showDataUser: ", "Masuk");
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(name);
            }

            edtName.setText(name);
            edtAlamat.setText(alamat);
            edtNoTelp.setText(noTelp);

            switch (gender) {
                case GENDER_MALE:
                    spinGender.setSelection(0);
                    break;
                case GENDER_FEMALE:
                    spinGender.setSelection(1);
                    break;
            }

        } else {
            getSupportActionBar().setTitle("Profil");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:

                editMode();

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);


                return true;
            case R.id.menu_save:

                if (!TextUtils.isEmpty(idUser)) {
                    if (TextUtils.isEmpty(edtName.getText().toString()) ||
                            TextUtils.isEmpty(edtAlamat.getText().toString())
                            || TextUtils.isEmpty(edtNoTelp.getText().toString())){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }else {
                        //Apabila user mengisi semua field
                        LoginData loginData = new LoginData();
                        //mengisi inputan user ke model logindata
                        loginData.setId_user(idUser);
                        loginData.setNamaUser(edtName.getText().toString());
                        loginData.setAlamat(edtAlamat.getText().toString());
                        loginData.setNoTelp(edtNoTelp.getText().toString());
                        loginData.setJenkel(mGender);

                        // mengirim data ke presenter
                        presenter.updateDataUser(this, loginData);

                        readMode();
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                    }

                }else {
                    readMode();
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                }

                readMode();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(idUser)) {
            if (TextUtils.isEmpty(edtName.getText().toString()) ||
                    TextUtils.isEmpty(edtAlamat.getText().toString())
                    || TextUtils.isEmpty(edtNoTelp.getText().toString())) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("Please complete the field!");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            } else {
                //Apabila user mengisi semua field
                LoginData loginData = new LoginData();
                //mengisi inputan user ke model logindata
                loginData.setId_user(idUser);
                loginData.setNamaUser(edtName.getText().toString());
                loginData.setAlamat(edtAlamat.getText().toString());
                loginData.setNoTelp(edtNoTelp.getText().toString());
                loginData.setJenkel(mGender);

                // mengirim data ke presenter
                presenter.updateDataUser(this, loginData);

                readMode();
                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);
            }
        }
    }

    private void editMode() {

        edtName.setFocusableInTouchMode(true);
        edtAlamat.setFocusableInTouchMode(true);
        edtNoTelp.setFocusableInTouchMode(true);

        spinGender.setEnabled(true);
        fabChoosePic.setVisibility(View.VISIBLE);


    }

    private void readMode() {

        edtName.setFocusableInTouchMode(false);
        edtNoTelp.setFocusableInTouchMode(false);
        edtAlamat.setFocusableInTouchMode(false);
        edtName.setFocusable(false);
        edtAlamat.setFocusable(false);
        edtNoTelp.setFocusable(false);

        spinGender.setEnabled(false);
        fabChoosePic.setVisibility(View.INVISIBLE);

    }

}
