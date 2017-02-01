package net.lixnet.payrollapp.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import net.lixnet.payrollapp.R;
import net.lixnet.payrollapp.activity.MainActivity;

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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SummaryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SummaryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String url ="http://192.168.118.1/cloud/android/summary.php";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SummaryFragment newInstance(String param1, String param2) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_summary, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }

        InputStream Is = null;
        String line = null;
        String result = "";
        String loginurl = "http://192.168.118.1/cloud/android/summary.php";
        String username = MainActivity.userName();


        try{

            URL url = new URL(loginurl);
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

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line+"\n");
                }



            bufferedReader.close();
            inputStream.close();
            con.disconnect();



            result=sb.toString();

            //Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_LONG).show();

        }catch(MalformedURLException e){
            e.printStackTrace();
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }finally {
            if(Is != null){
                try {
                    Is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }

        //Toast.makeText(getContext(),result,Toast.LENGTH_LONG).show();

        try
        {


            JSONArray jArray = new JSONArray(result);


            String re=jArray.getString(jArray.length()-1);


            TableLayout tv=(TableLayout) rootView.findViewById(R.id.table);
            tv.removeAllViewsInLayout();




            int flag=1;

            for(int i=-1;i<jArray.length()-1;i++)

            {




                TableRow tr=new TableRow(getContext());

                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));




                if(flag==1)
                {

                    TextView b6=new TextView(getContext());
                    b6.setText("Payroll No.");
                    b6.setTextColor(Color.BLUE);
                    b6.setTextSize(15);
                    tr.addView(b6);


                    TextView b19=new TextView(getContext());
                    b19.setPadding(10, 0, 0, 0);
                    b19.setTextSize(15);
                    b19.setText("Employee");
                    b19.setTextColor(Color.BLUE);
                    tr.addView(b19);

                    TextView b29=new TextView(getContext());
                    b29.setPadding(10, 0, 0, 0);
                    b29.setText("Basic Pay");
                    b29.setTextColor(Color.BLUE);
                    b29.setTextSize(15);
                    tr.addView(b29);


                    tv.addView(tr);

                    final View vline = new View(getContext());
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




                    TextView b=new TextView(getContext());
                    String stime=String.valueOf(json_data.getInt("personal_file_number"));
                    b.setText(stime);
                    b.setTextColor(Color.RED);
                    b.setTextSize(15);
                    tr.addView(b);


                    TextView b1=new TextView(getContext());
                    b1.setPadding(10, 0, 0, 0);
                    b1.setTextSize(15);
                    String stime1=json_data.getString("last_name")+" "+json_data.getString("first_name");
                    b1.setText(stime1);
                    b1.setTextColor(Color.WHITE);
                    tr.addView(b1);

                    TextView b2=new TextView(getContext());
                    b2.setPadding(10, 0, 0, 0);
                    String stime2=String.valueOf(json_data.getInt("basic_pay"));
                    b2.setText(stime2);
                    b2.setTextColor(Color.RED);
                    b2.setTextSize(15);
                    tr.addView(b2);

                    tv.addView(tr);


                    final View vline1 = new View(getContext());
                    vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                    vline1.setBackgroundColor(Color.BLACK);
                    tv.addView(vline1);


                }

            }

        }
        catch(JSONException e)
        {
            Log.e("log_tag", "Error parsing data "+e.toString());
            Toast.makeText(getContext(), "JsonArray fail"+e.toString(), Toast.LENGTH_SHORT).show();
        }
        /*final SummaryData d = new SummaryData(rootView.getContext(), url, username, table);

        d.execute();*/

        //parse json data


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
