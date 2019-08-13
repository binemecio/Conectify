package app.cloudnicaragua.bluewifi.nicaragua.Helper;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import app.cloudnicaragua.bluewifi.nicaragua.GlobalVar.GlobalVar;
import app.cloudnicaragua.bluewifi.nicaragua.Interface.PassDataEnterprise;
import app.cloudnicaragua.bluewifi.nicaragua.Interface.PassDataLong;
import app.cloudnicaragua.bluewifi.nicaragua.Interface.PassDataResult;
import app.cloudnicaragua.bluewifi.nicaragua.Pojo.ClientRecord;
import app.cloudnicaragua.bluewifi.nicaragua.Pojo.EnterPrise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.cloudnicaragua.bluewifi.nicaragua.Singletoon.StorageSingleton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by binemecio on 1/5/2019.
 */

public class ConnectionServer {
    private String url = GlobalVar.ssidUrl;
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
    public void getEnterpriseDataFromServer(String ssid1, PassDataEnterprise callback)
    {
        JSONObject jsonParam = new JSONObject();
        try
        {
            jsonParam.put("first_ssid", ssid1);
        }catch (JSONException e) {}


        StringRequest obreq = new StringRequest(Request.Method.POST, GlobalVar.ssidUrl, response -> {

            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length() == 0)
                {
                    callback.setDataEnterprise(null);
                }
                else
                {
                    callback.setDataEnterprise(EnterPrise.getEnterPriseList(jsonArray));
                }
            } catch (JSONException e) {
                callback.setDataEnterprise(null);
            }
        }, error -> {
            System.out.println("Error >>>>>>>>>>>>>>>>>>>>> : " + error.getMessage() + " URL : [ "+ GlobalVar.ssidUrl +" ]");
            callback.setDataEnterprise(null);
        }
        ){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("first_ssid", ssid1);
                return params2;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }



        };
        // Adds the JSON object request "obreq" to the request queue
        queue.add(obreq);
    }


    public void sendRecordDataToServer(ClientRecord record, PassDataResult result)
    {


        StringRequest obreq = new StringRequest(Request.Method.POST, GlobalVar.recordUrl, response -> {
            result.setData(true, response);

        }, error -> {
            System.out.println("Error >>>>>>>>>>>>>>>>>>>>> : " + error.getMessage() + " URL : [ "+ GlobalVar.recordUrl +" ]");

            result.setData(false, error.getMessage());
        }
        ){

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("id_configuracion_empresa", record.getId_configuracion_empresa());
                params2.put("nombres_cliente", record.getNombres_cliente());
                params2.put("apellidos_cliente", record.getApellidos_cliente());
                params2.put("numero_celular", record.getNumero_celular());
                params2.put("correo_electronico", record.getCorreo_electronico());
                params2.put("dispositivo_cliente", record.getDispositivo_cliente());
                params2.put("sistema_operativo_cliente", record.getSistema_operativo_cliente());
                return params2;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        // Adds the JSON object request "obreq" to the request queue
        queue.add(obreq);
    }

    public void getLoopTime(String id_ciclo, PassDataLong result)
    {
//        tiempo_ciclo
//        id_ciclo_lanzamiento_app
        JSONObject jsonParam = new JSONObject();
        try
        {
            jsonParam.put("id_ciclo_lanzamiento_app", id_ciclo);


        }catch (JSONException e) {}


        StringRequest obreq = new StringRequest(Request.Method.POST, GlobalVar.adLoopUrl, response -> {

//            List<EnterPrise> list = new ArrayList<>();
//            list.add(new EnterPrise(response));


            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject object = jsonArray.getJSONObject(0);
                Long value = this.convertToTime(object.getString("tiempo_ciclo"));
                StorageSingleton.getInstance().setLoopTime(value);
                result.setData(true, value);
            } catch (JSONException e) {
                result.setData(false, Long.valueOf(0));
            }



        }, error -> {
            System.out.println("Error >>>>>>>>>>>>>>>>>>>>> : " + error.getMessage() + " URL : [ "+ GlobalVar.adLoopUrl +" ]");

            result.setData(false, Long.valueOf(0));
        }
        ){

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("id_ciclo_lanzamiento_app", id_ciclo);
                return params2;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        // Adds the JSON object request "obreq" to the request queue
        queue.add(obreq);
    }

    private Long convertToTime(String value){
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        try {
            java.sql.Time time = new java.sql.Time(formatter.parse(value).getTime());
//            long valueTime =  time.getTime();
            long valueMiliseconds = time.getSeconds() * 1000;
            return valueMiliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
            return Long.valueOf(5000);
        }

    }

}
