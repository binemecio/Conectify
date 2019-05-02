package com.example.binemecio.conectify.Pojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binemecio on 1/5/2019.
 */

public class EnterPrise {
    private String id_empresa, acronimo_empresa, nombre_empresa = "";
    private String id_configuracion_empresa, ip_empresa, ssid1_empresa, ssid2_empresa, password, tiempo_ciclo = "";

    public EnterPrise() {

    }

    public EnterPrise(JSONObject json)
    {
        try
        {
            this.id_configuracion_empresa = json.getInt("id_configuracion_empresa") + "";
            this.ssid1_empresa = json.getString("ssid1_empresa") + "";
            this.ssid2_empresa = json.getString("ssid2_empresa") + "";
            this.password = json.getString("password") + "";
            //this.tiempo_ciclo = json.getString("tiempo_ciclo") + "";
        }
        catch (Exception e){
            System.out.print("Error parse jsonObject");
        }
    }



    public static List<EnterPrise> getEnterPriseList(JSONArray jsonArray)
    {
        List<EnterPrise> enterPrises = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++ )
        {
            try {
                enterPrises.add(new EnterPrise(jsonArray.getJSONObject(i)));
                System.out.print(jsonArray.get(i));
            }catch (Exception e) { System.out.print("Error to parse json array list to json object "+ e.getMessage()); }
        }

        return enterPrises;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getAcronimo_empresa() {
        return acronimo_empresa;
    }

    public void setAcronimo_empresa(String acronimo_empresa) {
        this.acronimo_empresa = acronimo_empresa;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getId_configuracion_empresa() {
        return id_configuracion_empresa;
    }

    public void setId_configuracion_empresa(String id_configuracion_empresa) {
        this.id_configuracion_empresa = id_configuracion_empresa;
    }

    public String getIp_empresa() {
        return ip_empresa;
    }

    public void setIp_empresa(String ip_empresa) {
        this.ip_empresa = ip_empresa;
    }

    public String getSsid1_empresa() {
        return ssid1_empresa;
    }

    public void setSsid1_empresa(String ssid1_empresa) {
        this.ssid1_empresa = ssid1_empresa;
    }

    public String getSsid2_empresa() {
        return ssid2_empresa;
    }

    public void setSsid2_empresa(String ssid2_empresa) {
        this.ssid2_empresa = ssid2_empresa;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTiempo_ciclo() {
        return tiempo_ciclo;
    }

    public void setTiempo_ciclo(String tiempo_ciclo) {
        this.tiempo_ciclo = tiempo_ciclo;
    }
}
