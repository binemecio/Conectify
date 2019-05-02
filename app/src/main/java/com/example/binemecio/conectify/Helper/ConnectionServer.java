package com.example.binemecio.conectify.Helper;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.binemecio.conectify.Interface.PassDataEnterprise;
import com.example.binemecio.conectify.Interface.PassDataListEnterprise;
import com.example.binemecio.conectify.Pojo.EnterPrise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.toolbox.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by binemecio on 1/5/2019.
 */

public class ConnectionServer {
    private String url = "http://192.168.137.1:80/conectify/api/consulta.php";
    private Activity activity;
    private RequestQueue queue;

    public ConnectionServer(Activity activity)
    {
        this.queue = Volley.newRequestQueue(activity);
        this.activity = activity;
    }

    public ConnectionServer(Activity activity, String url)
    {
        this.queue = Volley.newRequestQueue(activity);
        this.activity = activity;
        this.url = url;
    }
    public void getEnterpriseDataFromServer(PassDataListEnterprise callback)
    {
        JSONObject jsonParam = new JSONObject();
        try
        {
            jsonParam.put("consulta", "SELECT * FROM tbl_configuracion_empresas");
        }catch (JSONException e)
        {

        }

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from

//        StringRequest request = new StringRequest(Request.Method.POST,url, response -> {
//            System.out.println(response);
//        } , error -> {
//            System.out.println(error.getMessage());
//        }
//        );
//

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
//            JSONArray jsonArray = null;
//            try
//            {
//                jsonArray = new JSONArray(response.toString());
//            }
//            catch (Exception e){ }
//            List<EnterPrise> enterPriseList = jsonArray == null ? new ArrayList<>() : EnterPrise.getEnterPriseList(jsonArray);
//            callback.setDataListEnterprise(enterPriseList);

            List<EnterPrise> list = new ArrayList<>();
            list.add(new EnterPrise(response));
            callback.setDataListEnterprise(list);

        }, error -> {
            System.out.println("Error >>>>>>>>>>>>>>>>>>>>> : " + error.getMessage());
        }
        ){

        };
        // Adds the JSON object request "obreq" to the request queue
        queue.add(obreq);
    }

    public String getSSIDFromServer()
    {
        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                //loadIntoListView(s);
                System.out.print("");
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(ConnectionServer.this.url);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
        return getJSON.toString();
    }

}
