package id.co.maminfaruq.datasiswa.ui.home;

import java.util.List;

import id.co.maminfaruq.datasiswa.model.login.kelas.KelasData;

public interface HomeContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showKelasNewsList(List<KelasData> kelasNewsList);

        void showkelasPopulerList(List<KelasData> kelasPopulerList);

        void showKelasKategoriList(List<KelasData> kelasKategoriList);

        void showFailureMessage(String msg);
    }

    interface Presenter {
        void getListKelasnews();

        void getListKelasPolular();

        void getListKelasKategori();

    }
}
