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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Wango on 12/11/2016.
 */
public class SummaryData extends AsyncTask<String, Integer, String> {

    Context c;
    String address;
    String username;
    TableLayout table;

    ProgressDialog pd;

    public SummaryData(Context c, String address, String username, TableLayout table){
        this.c = c;
        this.address = address;
        this.username = username;
        this.table = table;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Fetch Data");
        pd.setMessage("Fetching Data...Please wait");
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String data = downloadData();
        return data;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pd.dismiss();

        //Toast.makeText(c, s+" bla", Toast.LENGTH_LONG).show();
        if(s != null){
            SummaryParser p = new SummaryParser(c, s, username, table);
            p.execute();
        }else{
            Toast.makeText(c, "Data is not Available", Toast.LENGTH_SHORT).show();
        }
    }

    private String downloadData(){
        InputStream Is = null;
        String line = null;
        String result = null;

        try{

            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            OutputStream outputStream = con.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            //String result = "";

            /*while((line = bufferedReader.readLine()) != null){
                result += line;
            }*/


            //BufferedReader br = new BufferedReader(new InputStreamReader(Is));

            StringBuffer sb = new StringBuffer();

            if(bufferedWriter != null){
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line+"\n");
                }
                result=sb.toString();
            }else{
                return null;
            }

            bufferedReader.close();
            inputStream.close();
            con.disconnect();



            return sb.toString();

        }catch(MalformedURLException e){
            e.printStackTrace();
            Toast.makeText(c, e.toString(), Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(c, e.toString(), Toast.LENGTH_LONG).show();
        }finally {
            if(Is != null){
                try {
                    Is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(c, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }

        try
        {

            JSONArray jArray = new JSONArray(result);


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

        }
        catch(JSONException e)
        {
            Log.e("log_tag", "Error parsing data "+e.toString());
            Toast.makeText(c, "JsonArray fail", Toast.LENGTH_SHORT).show();
        }


        return null;
    }
}
