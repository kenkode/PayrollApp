package net.lixnet.payrollapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.lixnet.payrollapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Wango on 1/23/2017.
 */

public class PersonalInfo extends Fragment {

    String url = "http://192.168.118.1/cloud/employeedetails.php";
    String username = MainActivity.userName();
    TextView pfn,fname,lname,gender,idno,passport,status,citizenship,dob,education,bank,bbranch,accno,swift,eft,contact,email,zip,address;
    private ProgressDialog loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_employee_info, container, false);
        pfn = (TextView) rootView.findViewById(R.id.pfn_txt);
        fname = (TextView) rootView.findViewById(R.id.fname_txt);
        lname = (TextView) rootView.findViewById(R.id.lname_txt);
        gender = (TextView) rootView.findViewById(R.id.gender);
        dob = (TextView) rootView.findViewById(R.id.dob);
        idno = (TextView) rootView.findViewById(R.id.idno);
        citizenship = (TextView) rootView.findViewById(R.id.citizenship);
        status = (TextView) rootView.findViewById(R.id.marital_status);
        passport = (TextView) rootView.findViewById(R.id.passport);
        education = (TextView) rootView.findViewById(R.id.education);
        bank = (TextView) rootView.findViewById(R.id.bank);
        bbranch = (TextView) rootView.findViewById(R.id.bbranch);
        accno = (TextView) rootView.findViewById(R.id.bacc);
        swift = (TextView) rootView.findViewById(R.id.swift);
        eft = (TextView) rootView.findViewById(R.id.eft);
        contact = (TextView) rootView.findViewById(R.id.phone);
        email = (TextView) rootView.findViewById(R.id.email);
        zip = (TextView) rootView.findViewById(R.id.zip);
        address = (TextView) rootView.findViewById(R.id.address);

        // pfn.setText(MainActivity.userName());

        getData();

        /*
        final EmployeeData d = new EmployeeData(rootView.getContext(), url, username, pfn, fname, lname);

        d.execute();
        */
        return rootView;

    }

    private void getData() {

        loading = ProgressDialog.show(this.getContext(),"Please wait...","Fetching...",false,false);

        String url = Config.DATA_URL+MainActivity.userName().toString().trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String pfntxt="";
        String firstname="";
        String lastname = "";
        String idtxt="";
        String passporttxt="";
        String gendertxt = "";
        String citizenshiptxt="";
        String statustxt="";
        String dobtxt = "";
        String educationtxt="";
        String banktxt="";
        String bbranchtxt = "";
        String acctxt="";
        String swifttxt="";
        String efttxt = "";
        String phonetxt="";
        String emailtxt="";
        String ziptxt = "";
        String addresstxt="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            pfntxt = collegeData.getString(Config.KEY_PFN);
            firstname = collegeData.getString(Config.KEY_FNAME);
            lastname = collegeData.getString(Config.KEY_LNAME);
            idtxt = collegeData.getString(Config.KEY_IDNO);
            passporttxt = collegeData.getString(Config.KEY_PASSPORT);
            gendertxt = collegeData.getString(Config.KEY_GENDER);
            dobtxt = collegeData.getString(Config.KEY_DOB);
            banktxt = collegeData.getString(Config.KEY_BANK);
            bbranchtxt = collegeData.getString(Config.KEY_BANK_BRANCH);
            acctxt = collegeData.getString(Config.KEY_ACC);
            statustxt = collegeData.getString(Config.KEY_STATUS);
            swifttxt = collegeData.getString(Config.KEY_SWIFT);
            efttxt = collegeData.getString(Config.KEY_EFT);
            educationtxt = collegeData.getString(Config.KEY_EDUCATION);
            citizenshiptxt = collegeData.getString(Config.KEY_CITIZENSHIP);
            phonetxt = collegeData.getString(Config.KEY_CONTACT);
            emailtxt = collegeData.getString(Config.KEY_EMAIL);
            addresstxt = collegeData.getString(Config.KEY_ADDRESS);
            ziptxt = collegeData.getString(Config.KEY_ZIP);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        pfn.setText(pfntxt);
        fname.setText(firstname);
        lname.setText(lastname);
        idno.setText(idtxt);
        passport.setText(passporttxt);
        gender.setText(gendertxt);
        education.setText(educationtxt);
        citizenship.setText(citizenshiptxt);
        status.setText(statustxt);
        bank.setText(banktxt);
        bbranch.setText(bbranchtxt);
        accno.setText(acctxt);
        swift.setText(swifttxt);
        eft.setText(efttxt);
        dob.setText(dobtxt);
        email.setText(emailtxt);
        contact.setText(phonetxt);
        address.setText(addresstxt);
        zip.setText(ziptxt);
    }

}
