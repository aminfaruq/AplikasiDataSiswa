package id.co.maminfaruq.datasiswa.ui.home;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.co.maminfaruq.datasiswa.R;
import id.co.maminfaruq.datasiswa.adapter.KelasAdapter;
import id.co.maminfaruq.datasiswa.model.login.kelas.KelasData;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeView extends Fragment implements HomeContract.View {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    Unbinder unbinder;
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.rv_new)
    RecyclerView rvNew;
    @BindView(R.id.rv_popular)
    RecyclerView rvPopular;

    private ProgressDialog progressDialog;
    private HomePresenter homePresenter = new HomePresenter(this);


    public HomeView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_view, container, false);
        unbinder = ButterKnife.bind(this, view);


        homePresenter.getListKelasnews();
        homePresenter.getListKelasPolular();
        homePresenter.getListKelasKategori();

        return view;
    }


    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();

    }

    @Override
    public void showKelasNewsList(List<KelasData> kelasNewsList) {
        rvNew.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvNew.setAdapter(new KelasAdapter(KelasAdapter.TYPE_1, getContext(), kelasNewsList));

    }

    @Override
    public void showkelasPopulerList(List<KelasData> kelasPopulerList) {
        rvPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvPopular.setAdapter(new KelasAdapter(KelasAdapter.TYPE_2, getContext(), kelasPopulerList));

    }

    @Override
    public void showKelasKategoriList(List<KelasData> kelasKategoriList) {
        rvCategory.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvCategory.setAdapter(new KelasAdapter(KelasAdapter.TYPE_3, getContext(), kelasKategoriList));

    }

    @Override
    public void showFailureMessage(String msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
