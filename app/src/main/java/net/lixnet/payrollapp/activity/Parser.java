package net.lixnet.payrollapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Wango on 12/11/2016.
 */
public class Parser  extends AsyncTask<Void, Integer, Integer> {

    Context c;
    String data;
    String username;
    TextView pfn;
    TextView fname;
    TextView lname;
    String pfn_txt = "";
    String firstname = "";
    String lastname = "";


    ProgressDialog pd;

    public Parser(Context c, String data, String username, TextView pfn, TextView fname, TextView lname) {
        this.c = c;
        this.data = data;
        this.username = username;
        this.pfn = pfn;
        this.fname = fname;
        this.lname = lname;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Parser");
        pd.setMessage("Parsing...Please wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        //Toast.makeText(c,integer,Toast.LENGTH_SHORT).show();

        if(integer == 1){
            pfn.setText(pfn_txt);
            fname.setText(firstname);
            lname.setText(lastname);
            Toast.makeText(c, firstname + " " + lastname, Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(c,"Data not available",Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }


    private int parse() {
        try {

            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            //des.clear();

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                pfn_txt = jo.getString("personal_file_number");
                firstname = jo.getString("first_name");
                lastname = jo.getString("last_name");

            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}