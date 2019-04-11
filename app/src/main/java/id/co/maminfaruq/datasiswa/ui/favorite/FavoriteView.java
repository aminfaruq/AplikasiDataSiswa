package id.co.maminfaruq.datasiswa.ui.favorite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.co.maminfaruq.datasiswa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteView extends Fragment {


    public FavoriteView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_view, container, false);
    }

}
