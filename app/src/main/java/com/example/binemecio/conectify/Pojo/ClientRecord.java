package com.example.binemecio.conectify.Pojo;

import com.android.volley.ClientError;

/**
 * Created by binemecio on 3/5/2019.
 */

public class ClientRecord {
    private String id_configuracion_empresa,nombres_cliente, apellidos_cliente, numero_celular, correo_electronico, dispositivo_cliente,
            sistema_operativo_cliente, fecha_hora_acceso_cliente;


    public ClientRecord(){}

    public ClientRecord(String id_configuracion_empresa, String nombres_cliente, String apellidos_cliente, String numero_celular, String correo_electronico, String dispositivo_cliente, String sistema_operativo_cliente, String fecha_hora_acceso_cliente) {
        this.id_configuracion_empresa = id_configuracion_empresa;
        this.nombres_cliente = nombres_cliente;
        this.apellidos_cliente = apellidos_cliente;
        this.numero_celular = numero_celular;
        this.correo_electronico = correo_electronico;
        this.dispositivo_cliente = dispositivo_cliente;
        this.sistema_operativo_cliente = sistema_operativo_cliente;
        this.fecha_hora_acceso_cliente = fecha_hora_acceso_cliente;
    }

    public String getId_configuracion_empresa() {
        return id_configuracion_empresa;
    }

    public void setId_configuracion_empresa(String id_configuracion_empresa) {
        this.id_configuracion_empresa = id_configuracion_empresa;
    }

    public String getNombres_cliente() {
        return nombres_cliente;
    }

    public void setNombres_cliente(String nombres_cliente) {
        this.nombres_cliente = nombres_cliente;
    }

    public String getApellidos_cliente() {
        return apellidos_cliente;
    }

    public void setApellidos_cliente(String apellidos_cliente) {
        this.apellidos_cliente = apellidos_cliente;
    }

    public String getNumero_celular() {
        return numero_celular;
    }

    public void setNumero_celular(String numero_celular) {
        this.numero_celular = numero_celular;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getDispositivo_cliente() {
        return dispositivo_cliente;
    }

    public void setDispositivo_cliente(String dispositivo_cliente) {
        this.dispositivo_cliente = dispositivo_cliente;
    }

    public String getSistema_operativo_cliente() {
        return sistema_operativo_cliente;
    }

    public void setSistema_operativo_cliente(String sistema_operativo_cliente) {
        this.sistema_operativo_cliente = sistema_operativo_cliente;
    }

    public String getFecha_hora_acceso_cliente() {
        return fecha_hora_acceso_cliente;
    }

    public void setFecha_hora_acceso_cliente(String fecha_hora_acceso_cliente) {
        this.fecha_hora_acceso_cliente = fecha_hora_acceso_cliente;
    }
}
