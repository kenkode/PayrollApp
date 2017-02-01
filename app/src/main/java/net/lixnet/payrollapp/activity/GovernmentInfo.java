package net.lixnet.payrollapp.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.lixnet.payrollapp.R;

/**
 * Created by Wango on 1/23/2017.
 */
public class GovernmentInfo extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goverment_info, container, false);
        return rootView;
    }
}
