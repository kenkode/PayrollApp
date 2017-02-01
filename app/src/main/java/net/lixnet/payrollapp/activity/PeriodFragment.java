package net.lixnet.payrollapp.activity;

/**
 * Created by Wango on 1/24/2017.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import net.lixnet.payrollapp.R;

import java.util.Calendar;

/**
 * Created by Ratan on 7/29/2015.
 */
public class PeriodFragment extends Fragment implements View.OnClickListener {

    int year_x, month_x, day_x;
    static EditText  d;
    static Button btnpick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        View rootView = inflater.inflate(R.layout.payslip_period, container, false);

        d = (EditText)rootView.findViewById(R.id.date);
        d.setOnClickListener(this);
        btnpick = (Button)rootView.findViewById(R.id.select);
        btnpick.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View arg0) {
                                       FragmentTransaction fragmentTransaction = MainActivity.mFragmentManager.beginTransaction();
                                       fragmentTransaction.replace(R.id.containerView, new PayslipFragment()).commit();
                                   }
                               }
            );


        return rootView;
    }


    @Override
    public void onClick(View v) {
        DialogFragment picker = new DatePickerFragment();
        picker.show(getFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1);
        }
        public void populateSetDate(int year, int month) {
            d.setText(month+"-"+year);
        }
    }

}
