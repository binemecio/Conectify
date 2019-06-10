package com.example.binemecio.conectify.Pojo;

/**
 * Created by Nemesio on 5/6/19.
 */

public class Ad {

    private String direccion_url, id_ciclo_display_anuncios = "";
    private Long anuncio_lapso, tiempo_ciclo = Long.valueOf(0);

    public Ad(){}

    public Ad(EnterPrise obj)
    {
        this.anuncio_lapso = obj.getTiempo_ciclo_lanzamiento();
        this.tiempo_ciclo = obj.getTiempo_ciclo_display_anuncio();
        this.direccion_url = obj.getDireccion_url();
        this.id_ciclo_display_anuncios = obj.getId_ciclo_display_anuncios();
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
