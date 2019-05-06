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
import com.example.binemecio.conectify.Interface.PassDataResult;
import com.example.binemecio.conectify.Pojo.ClientRecord;
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
    public void getEnterpriseDataFromServer(String ssid1, PassDataListEnterprise callback)
    {
        JSONObject jsonParam = new JSONObject();
        try
        {
            jsonParam.put("first_ssid", ssid1);
        }catch (JSONException e) {}


        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.POST, url, null, response -> {

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


    public void sendRecordDataToServer(ClientRecord record, PassDataResult result)
    {
        JSONObject jsonParam = new JSONObject();
        try
        {
            jsonParam.put("id_configuracion_empresa", record.getId_configuracion_empresa());
            jsonParam.put("nombres_cliente", record.getNombres_cliente());
            jsonParam.put("apellidos_cliente", record.getApellidos_cliente());
            jsonParam.put("numero_celular", record.getNumero_celular());
            jsonParam.put("correo_electronico", record.getCorreo_electronico());
            jsonParam.put("dispositivo_cliente", record.getDispositivo_cliente());
            jsonParam.put("sistema_operativo_cliente", record.getSistema_operativo_cliente());


        }catch (JSONException e) {}


        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.POST, url, null, response -> {

//            List<EnterPrise> list = new ArrayList<>();
//            list.add(new EnterPrise(response));
            result.setData(true, response.toString());

        }, error -> {
            //System.out.println("Error >>>>>>>>>>>>>>>>>>>>> : " + error.getMessage());
            result.setData(false, error.getMessage());
        }
        ){

        };
        // Adds the JSON object request "obreq" to the request queue
        queue.add(obreq);
    }



}
