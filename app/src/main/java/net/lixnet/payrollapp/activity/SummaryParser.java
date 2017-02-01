package net.lixnet.payrollapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Wango on 12/11/2016.
 */
public class SummaryParser extends AsyncTask<Void, Integer, Integer> {

    Context c;
    String data;
    String username;
    TableLayout table;

    ProgressDialog pd;

    public SummaryParser(Context c, String data, String username, TableLayout table) {
        this.c = c;
        this.data = data;
        this.username = username;
        this.table = table;

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

        }else{
            Toast.makeText(c,"Data not available",Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }


    private int parse() {


        try
        {

            JSONArray jArray = new JSONArray(data);


            String re=jArray.getString(jArray.length()-1);


            TableLayout tv=table;
            tv.removeAllViewsInLayout();




            int flag=1;

            for(int i=-1;i<jArray.length()-1;i++)

            {




                TableRow tr=new TableRow(c);

                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));




                if(flag==1)
                {

                    TextView b6=new TextView(c);
                    b6.setText("Payroll No.");
                    b6.setTextColor(Color.BLUE);
                    b6.setTextSize(15);
                    tr.addView(b6);


                    TextView b19=new TextView(c);
                    b19.setPadding(10, 0, 0, 0);
                    b19.setTextSize(15);
                    b19.setText("Employee");
                    b19.setTextColor(Color.BLUE);
                    tr.addView(b19);

                    TextView b29=new TextView(c);
                    b29.setPadding(10, 0, 0, 0);
                    b29.setText("Basic Pay");
                    b29.setTextColor(Color.BLUE);
                    b29.setTextSize(15);
                    tr.addView(b29);


                    tv.addView(tr);

                    final View vline = new View(c);
                    vline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 2));
                    vline.setBackgroundColor(Color.BLACK);



                    tv.addView(vline);
                    flag=0;


                }

                else
                {



                    JSONObject json_data = jArray.getJSONObject(i);

                    Log.i("log_tag", "Payroll No: " + json_data.getInt("personal_file_number") +
                            ", Employee: " + json_data.getString("last_name") + " " + json_data.getString("first_name") +
                            ", Basic Pay: " + json_data.getInt("basic_pay"));




                    TextView b=new TextView(c);
                    String stime=String.valueOf(json_data.getInt("personal_file_number"));
                    b.setText(stime);
                    b.setTextColor(Color.RED);
                    b.setTextSize(15);
                    tr.addView(b);


                    TextView b1=new TextView(c);
                    b1.setPadding(10, 0, 0, 0);
                    b1.setTextSize(15);
                    String stime1=json_data.getString("last_name")+" "+json_data.getString("first_name");
                    b1.setText(stime1);
                    b1.setTextColor(Color.WHITE);
                    tr.addView(b1);

                    TextView b2=new TextView(c);
                    b2.setPadding(10, 0, 0, 0);
                    String stime2=String.valueOf(json_data.getInt("basic_pay"));
                    b2.setText(stime2);
                    b2.setTextColor(Color.RED);
                    b2.setTextSize(15);
                    tr.addView(b2);

                    tv.addView(tr);


                    final View vline1 = new View(c);
                    vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                    vline1.setBackgroundColor(Color.BLACK);
                    tv.addView(vline1);


                }

            }

            return 1;

        }
        catch(JSONException e)
        {
            Log.e("log_tag", "Error parsing data "+e.toString());
            Toast.makeText(c, "JsonArray fail", Toast.LENGTH_SHORT).show();
        }
        return 0;


    }

}