package id.co.maminfaruq.datasiswa.ui.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.co.maminfaruq.datasiswa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardView extends Fragment {


    public DashboardView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_view, container, false);
    }

}
