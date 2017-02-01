package net.lixnet.payrollapp.activity;

/**
 * Created by Wango on 1/24/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.lixnet.payrollapp.R;

/**
 * Created by Ratan on 7/29/2015.
 */
public class AdvanceFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sent_layout,null);
    }
}
