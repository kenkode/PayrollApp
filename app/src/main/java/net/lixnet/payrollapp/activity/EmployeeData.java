package net.lixnet.payrollapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

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
public class EmployeeData extends AsyncTask<String, Integer, String> {

    Context c;
    String address;
    String username;
    TextView pfn;
    TextView fname;
    TextView lname;

    ProgressDialog pd;

    public EmployeeData(Context c, String address, String username, TextView pfn, TextView fname, TextView lname){
        this.c = c;
        this.address = address;
        this.username = username;
        this.pfn = pfn;
        this.fname = fname;
        this.lname=lname;
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
            Parser p = new Parser(c, s, username, pfn, fname, lname);
            p.execute();
        }else{
            Toast.makeText(c, "Data is not Available", Toast.LENGTH_SHORT).show();
        }
    }

    private String downloadData(){
        InputStream Is = null;
        String line = null;

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
        return null;
    }
}
