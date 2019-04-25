package id.co.maminfaruq.datasiswa.ui.home;

import android.util.Log;

import id.co.maminfaruq.datasiswa.model.login.kelas.KelasResponse;
import id.co.maminfaruq.datasiswa.network.ApiClient;
import id.co.maminfaruq.datasiswa.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);


    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void getListKelasnews() {
        view.showProgress();
        Call<KelasResponse> call = mApiInterface.getKelasBaru();
        call.enqueue(new Callback<KelasResponse>() {
            @Override
            public void onResponse(Call<KelasResponse> call, Response<KelasResponse> response) {
                view.hideProgress();
                Log.e("cek response", "ini masuk");
                if (response.body().getResult() == 1) {
                    view.showKelasNewsList(response.body().getData());
                } else {
                    view.showFailureMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<KelasResponse> call, Throwable t) {
                view.hideProgress();

            }
        });

    }

    @Override
    public void getListKelasPolular() {
        Call<KelasResponse> call = mApiInterface.getKelasPopuler();



        call.enqueue(new Callback<KelasResponse>() {
            @Override
            public void onResponse(Call<KelasResponse> call, Response<KelasResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    view.showkelasPopulerList(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<KelasResponse> call, Throwable t) {
                view.hideProgress();

            }
        });
    }

    @Override
    public void getListKelasKategori() {
        Call<KelasResponse> call = mApiInterface.getKategoriKelas();


        call.enqueue(new Callback<KelasResponse>() {
            @Override
            public void onResponse(Call<KelasResponse> call, Response<KelasResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    view.showKelasKategoriList(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<KelasResponse> call, Throwable t) {
                view.hideProgress();

            }
        });
    }
}
