package com.example.binemecio.conectify.Pojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by binemecio on 1/5/2019.
 */

public class EnterPrise {

    private String id_configuracion_empresa, ssid1_empresa, ssid2_empresa, password, direccion_url, id_ciclo_display_anuncios  = "";
    private Long anuncio_lapso, tiempo_ciclo = Long.valueOf(0);
    private List<Ad> adList = new ArrayList<>();


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
            this.direccion_url = json.getString("direccion_url");
            this.anuncio_lapso = this.convertToTime(json.getString("anuncio_lapsos"));
            this.tiempo_ciclo = this.convertToTime( json.getString("tiempo_ciclo"));
            this.id_ciclo_display_anuncios = json.getString("id_ciclo_display_anuncios");

            //this.tiempo_ciclo = json.getString("tiempo_ciclo") + "";
        }
        catch (Exception e){
            System.out.print("Error parse jsonObject>>>>>>> >>>>>" + e.getMessage() );
        }
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
            return Long.valueOf(0);
        }

    }

    public static EnterPrise getEnterPriseList(JSONArray jsonArray)
    {
        List<EnterPrise> enterPrises = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++ )
        {
            try {
                enterPrises.add(new EnterPrise(jsonArray.getJSONObject(i)));
                System.out.print(jsonArray.get(i));
            }catch (Exception e) { System.out.print("Error to parse json array list to json object "+ e.getMessage()); }
        }



        return parseArrayListToObjet(enterPrises);
    }

    private static EnterPrise parseArrayListToObjet(List<EnterPrise> list)
    {
        if (list.isEmpty())
            return new EnterPrise();

        EnterPrise value = list.get(0);

        for (EnterPrise item : list)
        {
            value.adList.add(new Ad(item));
        }

        return value;
    }

    public List<Ad> getAdList() {
        return adList;
    }

    public void setAdList(List<Ad> adList) {
        this.adList = adList;
    }

    public String getId_configuracion_empresa() {
        return id_configuracion_empresa;
    }

    public void setId_configuracion_empresa(String id_configuracion_empresa) {
        this.id_configuracion_empresa = id_configuracion_empresa;
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

    public String getDireccion_url() {
        return direccion_url;
    }

    public void setDireccion_url(String direccion_url) {
        this.direccion_url = direccion_url;
    }

    public String getId_ciclo_display_anuncios() {
        return id_ciclo_display_anuncios;
    }

    public void setId_ciclo_display_anuncios(String id_ciclo_display_anuncios) {
        this.id_ciclo_display_anuncios = id_ciclo_display_anuncios;
    }

    public Long getAnuncio_lapso() {
        return anuncio_lapso;
    }

    public void setAnuncio_lapso(Long anuncio_lapso) {
        this.anuncio_lapso = anuncio_lapso;
    }

    public Long getTiempo_ciclo() {
        return tiempo_ciclo;
    }

    public void setTiempo_ciclo(Long tiempo_ciclo) {
        this.tiempo_ciclo = tiempo_ciclo;
    }
}
